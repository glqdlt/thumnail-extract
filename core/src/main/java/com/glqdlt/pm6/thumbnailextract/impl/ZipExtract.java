package com.glqdlt.pm6.thumbnailextract.impl;

import com.glqdlt.pm6.thumbnailextract.api.AsyncExtractError;
import com.glqdlt.pm6.thumbnailextract.api.ExtractUtil;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Date 2019-11-10
 * <p>
 * 압축 파일의 내부 엔트리 중에 가장 첫번째 이미지를 추출하는 개념.
 *
 * @author glqdlt
 */
public class ZipExtract implements ExtractUtil {
    private final org.slf4j.Logger logger;

    public ZipExtract(Logger logger) {
        this.logger = logger;
    }

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    private void work(List<Future<String>> futures, File targetSource, File outputDir) {
        final String id = targetSource.getAbsolutePath();
        Future<String> future = executorService.submit(() -> {
            getLogger().debug(String.format("work to start ==> %s", id));
            if (targetSource.isDirectory()) {
                throw new AsyncExtractError(String.format("%s is Not File", id), id);
            }
            if (!targetSource.getName().endsWith(".zip")) {
                throw new AsyncExtractError(String.format("%s is not zip", id), id);
            }

            ZipFile zipFile = new ZipFile(targetSource);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            ZipEntry firstItem = entries.nextElement();
            String s;
            try (InputStream stream = zipFile.getInputStream(firstItem)) {
                final String d;
                if (outputDir == null) {
                    d = targetSource.getPath();
                } else {
                    d = outputDir.getPath();
                }
                final Path
                        out = new File(d + File.separator + targetSource.getName() + "_" + firstItem.getName()).toPath();
                Files.copy(stream, out, StandardCopyOption.REPLACE_EXISTING);
                s = out.toString();
            }
            getLogger().debug(String.format("finish ==> %s", id));
            return s;
        });
        futures.add(future);
    }

    public String[] extract(File input) throws IOException {
        return extract(input, input.getAbsoluteFile());
    }

    @Override
    public String[] extract(File inputSource, File outputDir) throws IOException {
        final List<Future<String>> futures = new LinkedList<>();
        final Integer i;
        if (inputSource.isDirectory()) {
            File[] ee = inputSource.listFiles();
            i = ee.length;
            int j = i;
            for (File e : ee) {
                j--;
                getLogger().debug("[{}/{}]job start", i, j);
                work(futures, e, outputDir);
            }
        } else {
            i = 1;
            getLogger().debug("[{}/{}]job start", i, i);
            work(futures, inputSource, outputDir);
        }

        final List<String> result = new LinkedList<>();
        for (Future<String> filePath : futures) {

            try {
                String z = filePath.get();
                result.add(z);
            } catch (InterruptedException e) {
                getLogger().error(e.getMessage());
            } catch (ExecutionException e) {
                Throwable z = e.getCause();
                if (z instanceof AsyncExtractError) {
                    getLogger().error(String.format("%s is fail", ((AsyncExtractError) z).getId()), z);
                } else {
                    getLogger().error(e.getMessage(), e);
                }
            }

        }
        close();
        return result.toArray(new String[0]);
    }

    public void close() {
        this.executorService.shutdown();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}

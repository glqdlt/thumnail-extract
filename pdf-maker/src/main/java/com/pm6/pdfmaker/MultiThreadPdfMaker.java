package com.pm6.pdfmaker;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author glqdlt
 * 2020-10-23
 */
public class MultiThreadPdfMaker implements PdfMaker {

    private final static Logger logger = LoggerFactory.getLogger(MultiThreadPdfMaker.class);

    public void logging(String message) {
        logger.info(message);
    }

    public MultiThreadPdfMaker() {
    }

    public MultiThreadPdfMaker(Integer dpi, Float quality) {
        this.dpi = dpi;
        this.quality = quality;
    }

    private Integer dpi = 300;
    private Float quality = 1F;
    private Boolean forceDelete = true;

    public Boolean getForceDelete() {
        return forceDelete;
    }

    public void setForceDelete(Boolean forceDelete) {
        this.forceDelete = forceDelete;
    }

    public Integer getDpi() {
        return dpi;
    }

    public void setDpi(Integer dpi) {
        this.dpi = dpi;
    }

    public Float getQuality() {
        return quality;
    }

    public void setQuality(Float quality) {
        this.quality = quality;
    }

    @Override
    public File combineToImages(File sourceDir) {
        String name = sourceDir.getName();
        String fileName = name + ".pdf";
        String save = sourceDir.getAbsolutePath() + File.separator + fileName;
        return combineToImages(sourceDir, new File(save));
    }

    @Override
    public File combineToImages(File sourceDir, File target) {
        if (target == null) {
            throw new RuntimeException("target file must set path.");
        }
        ExecutorService pool = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors());
        try (PDDocument doc = new PDDocument();) {

            File[] items = sourceDir.listFiles();
            if (items == null || items.length < 1) {
                throw new RuntimeException("source directory is empty.");
            }

            final int entrySize = items.length;
            logging(String.format("Total images '%s' count.", entrySize));
            int entryPoint = 0;
            List<Future> callBacks = new LinkedList<>();
            PDPage[] queue = new PDPage[entrySize];
            for (final File item : items) {
                final int ___i = entryPoint;
                Future callback = pool.submit(() -> {
                    try {
                        BufferedImage _i = ImageIO.read(item);
                        int width = _i.getWidth();
                        int height = _i.getHeight();
                        PDImageXObject imageXObject = JPEGFactory.createFromImage(doc, _i, getQuality(), getDpi());
                        PDRectangle pageOutFrame = new PDRectangle(width, height);

                        PDPage pdPage = new PDPage(pageOutFrame);
                        try (PDPageContentStream pdPageContentStream = new PDPageContentStream(doc, pdPage)) {
                            pdPageContentStream.drawImage(imageXObject, 0, 0, width, height);
                        }
                        logging(String.format("[%s/%s] file[%s] ==> loaded",___i+1,entrySize,item.getName()));
                        queue[___i] = pdPage;
                        return true;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                entryPoint++;
                callBacks.add(callback);
            }

            while(true){
            boolean isDone = callBacks.stream().allMatch(Future::isDone);
            if(isDone){
                break;
            }
            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            }

            for(int i = 0; i < queue.length ; i++){
                doc.addPage(queue[i]);
            }

            logging(String.format("make pdf... '%s'", target.getName()));
            doc.save(target);
            logging("completed.");
            return target;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            pool.shutdown();
        }
    }

    private double calcPercent(int entrySize, double entryPoint) {
        return (entryPoint / entrySize) * 100;
    }
}

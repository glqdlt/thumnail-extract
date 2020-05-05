package com.glqdlt.pm6.thumbnailextract.impl;

import com.glqdlt.pm6.thumbnailextract.api.ExtractError;
import com.glqdlt.pm6.thumbnailextract.api.ExtractUtil;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Date 2019-11-10
 *
 * 압축 파일 처럼 첫번째 이미지에 해당하는 걸 추출하려 했으나,
 * 사실상 pdf 첫장을 캡처링 하는 것과 동일한 개념.
 *
 * @author glqdlt
 * @see <a href='https://www.baeldung.com/java-pdf-creation'>https://www.baeldung.com/java-pdf-creation</a>
 */
public class PDFExtract implements ExtractUtil {

    final private Logger logger;

    public PDFExtract(Logger logger) {
        this.logger = logger;
    }

    /**
     * @param input
     * @param outputDir
     * @return
     * @throws IOException
     */
    @Override
    public String[] extract(File input, File outputDir) throws IOException {
        return extract(input, outputDir, null, 0);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    public String[] extract(File inputSource, File outputDir, String fileName) throws IOException {
        return extract(inputSource, outputDir, fileName, 0);
    }

    public String[] extract(File inputSource, File outputDir, String fileName, Integer pageNumb) throws IOException {
        if (outputDir == null || !outputDir.isDirectory()) {
            throw new ExtractError("Wrong outputDir");
        }
        PDDocument document = PDDocument.load(inputSource);
        PDPage firstPage = document.getPage(pageNumb);
        PDResources pageResources = firstPage.getResources();
        Iterable<COSName> els = pageResources.getXObjectNames();
        List<String> path = new LinkedList<>();
        int i = 0;
        for (COSName el : els) {
            PDXObject elObj = pageResources.getXObject(el);
            if (elObj instanceof PDImageXObject) {
                getLogger().debug("find image object");
                PDImageXObject imageOrigin = ((PDImageXObject) elObj);
                File f = makeImagePath(outputDir, fileName, i, imageOrigin.getSuffix());
                boolean result = ImageIO.write(imageOrigin.getImage(),
                        imageOrigin.getSuffix(),
                        f);
                if (result) {
                    getLogger().debug("extract done, image path attach");
                    path.add(f.getCanonicalPath());
                }
            }
            i++;
        }
        return path.toArray(new String[0]);
    }

    private File makeImagePath(File dir, String fileName, Integer count, String suffix) throws IOException {
        final String filePrefix;
        if (fileName == null || fileName.equals("")) {
            Random random = new Random();
            int ran = random.nextInt(99);
            filePrefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY_MM_dd_HH_mm_ss_"))
                    + ran;
        } else {
            filePrefix = fileName.split(".")[0];
        }
        final String path = dir.getCanonicalPath()
                + File.separator;

        return new File(path + filePrefix + "_" + count + "." + suffix);
    }

    // 아래는 pdf 를 캡처하는 방식이다.
//    @Override
//    public String extract(File pdf, File save) throws IOException {
//        PDDocument d = PDDocument.load(pdf);
//        PDFRenderer pdfRenderer = new PDFRenderer(d);
//        BufferedImage zz = pdfRenderer.renderImageWithDPI(0, getDpi());
//        ImageIO.write(zz, "PNG", new File("d:\\aa.png"));
//        d.close();
//
//        return null;
//    }
}

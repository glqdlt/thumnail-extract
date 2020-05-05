package com.glqdlt.pm6.thumbnailextract.impl;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PdfExtractTest {
    private final Logger logger = LoggerFactory.getLogger(PdfExtractTest.class);
    @Test
    public void firstPagePdfImageExtract() throws IOException {
        String tempPath = System.getProperty("java.io.tmpdir");
        URL testTargetPdf = ClassLoader.getSystemResource("sample.pdf");
        PDFExtract pdfExtracter = new PDFExtract(logger);
        String[] eee = pdfExtracter.extract(new File(testTargetPdf.getPath()), new File(tempPath));
        Assert.assertEquals(1, eee.length);
        for (String s : eee) {
            new File(s).deleteOnExit();
        }
    }

    @Test
    public void firstPagePdfImageExtract2() throws IOException {
        String tempPath = System.getProperty("java.io.tmpdir");
        URL testTargetPdf = ClassLoader.getSystemResource("sample_text.pdf");
        PDFExtract pdfExtracter = new PDFExtract(logger);
        String[] eee = pdfExtracter.extract(new File(testTargetPdf.getPath()), new File(tempPath));
        Assert.assertEquals(0, eee.length);
    }


}
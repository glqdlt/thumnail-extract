package com.glqdlt.pm6.thumbnailextract.impl;

import com.glqdlt.pm6.thumbnailextract.api.PdfMaker;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class CombineImageToPdfTest {
    @Test
    public void name2() {

        File dir = new File("C:\\Users\\glqdlt\\Desktop\\스캔");
        for(File f : dir.listFiles()){
            if(!f.isDirectory()){
                continue;
            }
            PdfMaker pdfCombiner = new MultiThreadPdfMaker(300,1F);
            pdfCombiner.combineToImages(f);
        }
//        PdfMaker pdfCombiner = new MultiThreadPdfMaker();
//        pdfCombiner.combineToImages(new File("C:\\Users\\glqdlt\\Desktop\\새 폴더\\우분투리눅스기반의IDSIPS설치와운영"));


    }

    @Test
    public void name() throws IOException, URISyntaxException {

        PdfMaker pdfCombiner = new MultiThreadPdfMaker();

        File zzz = pdfCombiner.combineToImages(new File(ClassLoader.getSystemClassLoader().getResource("sampleCompbineImageSourceDir").toURI()), new File(System.getenv("TEMP") + File.separator + "_test.pdf"));
        Assert.assertNotNull(zzz);
        if (zzz.exists()) {
            zzz.delete();
        }

        Assert.assertFalse(zzz.exists());
    }
}
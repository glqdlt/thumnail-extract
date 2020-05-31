package com.glqdlt.pm6.thumbnailextract.impl;

import com.glqdlt.pm6.thumbnailextract.api.PdfCombiner;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class CombineImageToPdfTest {

    @Test
    public void name() throws IOException, URISyntaxException {

        PdfCombiner pdfCombiner = new CombineImageToPdf();

        File zzz = pdfCombiner.combineToImages(new File(ClassLoader.getSystemClassLoader().getResource("sampleCompbineImageSourceDir").toURI()), new File(System.getenv("TEMP") + File.separator + "_test.pdf"));
        Assert.assertNotNull(zzz);
        if (zzz.exists()) {
            zzz.delete();
        }

        Assert.assertFalse(zzz.exists());
    }
}
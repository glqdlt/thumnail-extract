package com.glqdlt.pm6.thumbnailextract.impl;

import com.glqdlt.pm6.thumbnailextract.api.ExtractUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ZipExtractTest {
    private final Logger logger = LoggerFactory.getLogger(ZipExtractTest.class);

    @Test
    public void name() throws IOException {
        String tempPath = System.getProperty("java.io.tmpdir");
        URL testTargetPdf = ClassLoader.getSystemResource("sample_zip.zip");
        ExtractUtil extractUtil = new ZipExtract(logger);
        String[] zz = extractUtil.extract(new File(testTargetPdf.getPath()), new File(tempPath));
        for(String s : zz){
            new File(s).deleteOnExit();
        }
    }
}
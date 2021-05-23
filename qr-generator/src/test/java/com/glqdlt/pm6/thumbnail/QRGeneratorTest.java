package com.glqdlt.pm6.thumbnail;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class QRGeneratorTest {

    @Ignore
    @Test
    public void testMakeImageFileAndDelete() throws IOException {
        Checksum checksum = new Md5Checksum();
        QRGenerator qrGenerator = new QRGenerator();
        String source = "http://127.0.0.1:8080/book/1";
        BufferedImage zxc = qrGenerator.generate(source);
        InputStream bos = new ByteArrayInputStream(source.getBytes());
        String fileName = checksum.digest(bos);
        String tempPath = System.getProperty("java.io.tmpdir");
        File file = new File(tempPath + File.separator + fileName + ".png");
        ImageIO.write(zxc, "png", file);
        bos.close();
        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.delete());
        Assert.assertFalse(file.exists());
    }
}
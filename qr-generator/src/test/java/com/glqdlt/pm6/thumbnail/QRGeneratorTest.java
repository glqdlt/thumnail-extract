package com.glqdlt.pm6.thumbnail;

import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRGeneratorTest {

    @Ignore
    @Test
    public void name() throws IOException {
        QRGenerator qrGenerator = new QRGenerator();
        BufferedImage zxc = qrGenerator.generate("http://127.0.0.1:8080/book/1");
        boolean zzzzzxc = ImageIO.write(zxc, "png", new File(""));
    }
}
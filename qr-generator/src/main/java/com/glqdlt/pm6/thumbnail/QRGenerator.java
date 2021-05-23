package com.glqdlt.pm6.thumbnail;

import net.glxn.qrgen.javase.QRCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author glqdlt
 */
public class QRGenerator implements Bardcode{

    @Override
    public BufferedImage generate(String source) {
        try (ByteArrayOutputStream stream = QRCode.from(source).withSize(250, 250).stream();
             ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());){
            return  ImageIO.read(bis);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

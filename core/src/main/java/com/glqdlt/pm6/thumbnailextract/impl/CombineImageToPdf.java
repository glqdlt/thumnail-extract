package com.glqdlt.pm6.thumbnailextract.impl;

import com.glqdlt.pm6.commons.SimpleRuntimeException;
import com.glqdlt.pm6.thumbnailextract.api.PdfCombiner;
import com.glqdlt.pm6.thumbnailextract.api.UserDeleteFunction;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CombineImageToPdf implements PdfCombiner {


    public void logging(String message) {
        System.out.println(message);
    }

    private Integer dpi = 300;
    private Integer quality = 1;
    private Boolean forceDelete = true;
    private UserDeleteFunction defaultFunction = (f) -> {
        logging(String.format("'%s' is exists.", f.getName()));
        if (getForceDelete()) {
            logging(String.format("try delete, '%s'", f.getName()));
            return true;
        }
        logging("not delete item");
        return false;
    };

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

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
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
            throw new SimpleRuntimeException("target file must set path.");
        }
        try (PDDocument doc = new PDDocument();) {

            File[] items = sourceDir.listFiles();
            if (items == null || items.length < 1) {
                throw new SimpleRuntimeException("source directory is empty.");
            }

            final int entrySize = items.length;
            logging(String.format("Total images '%s' count.", entrySize));
            int entryPoint = 0;
            for (File item : items) {
                logging(String.format("[%.1f%%] loading.. image. '%s' ", calcPercent(entrySize, entryPoint), item.getName()));
                entryPoint++;

                BufferedImage _i = ImageIO.read(item);
                int width = _i.getWidth();
                int height = _i.getHeight();
                PDImageXObject imageXObject = JPEGFactory.createFromImage(doc, _i, getQuality(), getDpi());

                PDRectangle pageOutFrame = new PDRectangle(width, height);

                PDPage pdPage = new PDPage(pageOutFrame);
                doc.addPage(pdPage);
                try (PDPageContentStream pdPageContentStream = new PDPageContentStream(doc, pdPage)) {
                    pdPageContentStream.drawImage(imageXObject, 0, 0, width, height);
                }
                logging("==> loaded");

            }

            if (target.exists()) {
                if (defaultFunction.areYouThisItemDelete(target)) {
                    target.delete();
                }
            }
            logging(String.format("make pdf... '%s'", target.getName()));
            doc.save(target);
            logging("completed.");
            return target;
        } catch (IOException e) {
            throw new SimpleRuntimeException(e);
        }
    }

    private double calcPercent(int entrySize, double entryPoint) {
        return (entryPoint / entrySize) * 100;
    }
}

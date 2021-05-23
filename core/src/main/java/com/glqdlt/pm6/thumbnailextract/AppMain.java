package com.glqdlt.pm6.thumbnailextract;

import com.glqdlt.pm6.thumbnailextract.impl.CombineImageToPdf;

import java.io.File;

public class AppMain {
    public static void main(String[] args) {
        File source = new File(args[0]);
        CombineImageToPdf combineImageToPdf = new CombineImageToPdf();
        if (source.isDirectory()) {
            for (File f : source.listFiles()) {
                combineImageToPdf.combineToImages(f);
            }
        } else {
            combineImageToPdf.combineToImages(source);
        }
    }
}

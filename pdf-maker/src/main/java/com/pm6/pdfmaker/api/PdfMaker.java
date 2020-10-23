package com.pm6.pdfmaker.api;

import java.io.File;

public interface PdfMaker {

    File combineToImages(File sourceDir);

    File combineToImages(File sourceDir, File target);

}

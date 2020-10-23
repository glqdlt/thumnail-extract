package com.pm6.pdfmaker;

import java.io.File;

/**
 * @author glqdlt
 * 2020-10-23
 */
public interface PdfMaker {

    File combineToImages(File sourceDir);

    File combineToImages(File sourceDir, File target);

}

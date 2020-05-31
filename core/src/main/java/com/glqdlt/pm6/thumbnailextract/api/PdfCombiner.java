package com.glqdlt.pm6.thumbnailextract.api;

import java.io.File;

public interface PdfCombiner {

    File combineToImages(File sourceDir);

    File combineToImages(File sourceDir, File target);

}

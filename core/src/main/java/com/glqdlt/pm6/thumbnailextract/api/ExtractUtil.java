package com.glqdlt.pm6.thumbnailextract.api;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Date 2019-11-10
 *
 * @author glqdlt
 */
public interface ExtractUtil {
    String[] extract(File inputSource, File outputDir) throws IOException;

    Logger getLogger();
}

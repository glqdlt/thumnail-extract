package com.glqdlt.pm6.thumbnailextract;

import com.glqdlt.pm6.thumbnailextract.impl.PdfExtractTest;
import com.glqdlt.pm6.thumbnailextract.impl.ZipExtractTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Date 2019-11-12
 *
 * @author glqdlt
 */
@Suite.SuiteClasses({PdfExtractTest.class, ZipExtractTest.class})
@RunWith(Suite.class)
public class ExtractTestSuite {
}

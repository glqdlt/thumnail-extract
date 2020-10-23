package com.pm6.pdfmaker;

import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author glqdlt
 * 2020-10-23
 */
public class MakerCliStarter implements Callable<Integer> {

    @CommandLine.Option(names = {"-D", "--dpi"},description = "PDF Dpi Setting, default 300 dpi.")
    private Integer dpi = 300;
    @CommandLine.Option(names = {"-Q", "--quality"},description = "PDF quality, 0.1 ~ 1 , High is better")
    private Float quality = 1F;
    @CommandLine.Option(names = {"-O", "--out"}, description = "PDF Make target directory")
    private String out;

    @Override
    public Integer call() throws Exception {
        MultiThreadPdfMaker multiThreadPdfMaker = new MultiThreadPdfMaker(dpi,quality);
        return 0;
    }
}

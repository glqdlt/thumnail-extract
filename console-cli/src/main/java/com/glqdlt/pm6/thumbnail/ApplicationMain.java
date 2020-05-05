package com.glqdlt.pm6.thumbnail;

import com.glqdlt.pm6.thumbnailextract.impl.ZipExtract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;


public class ApplicationMain {

    private static Logger logger = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) throws IOException {

        LocaleHandler localeHandler = new LocaleHandler();
        logger.info(localeHandler.getMessage(LocaleHandler.MessageKey.WELCOME_MESSAGE));
        Scanner scanner = new Scanner(System.in);
        logger.info(localeHandler.getMessage(LocaleHandler.MessageKey.PATH_INPUT_MESSAGE));
        String inputPath = scanner.nextLine();
        URI target = new File(inputPath).toURI();
        try {
            ZipExtract extractUtil = new ZipExtract(logger);
            extractUtil.extract(new File(target.getPath()));
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        scanner.close();
        logger.info(localeHandler.getMessage(LocaleHandler.MessageKey.CLOSE_MESSAGE));
    }
}

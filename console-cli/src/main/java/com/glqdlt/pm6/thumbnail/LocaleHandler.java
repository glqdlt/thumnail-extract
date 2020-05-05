package com.glqdlt.pm6.thumbnail;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class LocaleHandler {

    public static class MessageKey {
        public static final String WELCOME_MESSAGE = "msg.001";
        public static final String PATH_INPUT_MESSAGE = "msg.001";
        public static final String CLOSE_MESSAGE = "msg.003";
    }

    private Map messageBox;

    public LocaleHandler() {
        this.messageBox = initMessageBox();
    }

    public Map getMessageBox() {
        return messageBox;
    }

    public String getSystemLocale() {
        Locale l = Locale.getDefault();
        return l.getLanguage();
    }

    public SupportLocale matchLocale(String lang) {
        SupportLocale[] sls = SupportLocale.values();

        for (SupportLocale s : sls) {
            if (s.isSupported(lang)) {
                return s;
            }
        }
        return SupportLocale.ENGLISH;
    }

    private Map initMessageBox() {
        SupportLocale aa = matchLocale(getSystemLocale());
        try (InputStreamReader rrr = new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("i18n/" + aa.getPropertyFileName())))) {
            Properties props = new Properties();
            props.load(rrr);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessage(String key) {
        return getMessageBox().get(key).toString();
    }


    enum SupportLocale {
        KOREAN("ko"),
        ENGLISH("en");

        SupportLocale(String locale) {
            this.locale = locale;
        }

        public String getLocale() {
            return locale;
        }

        public String getPropertyFileName() {
            return getLocale() + ".properties";
        }

        public Boolean isSupported(String locale) {
            return getLocale().equals(locale.toLowerCase());
        }

        private String locale;
    }
}

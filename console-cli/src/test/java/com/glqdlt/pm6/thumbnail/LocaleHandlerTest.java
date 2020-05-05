package com.glqdlt.pm6.thumbnail;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class LocaleHandlerTest {

    @Test
    public void checkLocale() {
        Locale.setDefault(new Locale("ko", "KR"));
        LocaleHandler localeHandler = new LocaleHandler();
        String systemLocale = localeHandler.getSystemLocale();
        Assert.assertEquals("ko", systemLocale);
        Assert.assertNotEquals("en", systemLocale);

        LocaleHandler.SupportLocale a = localeHandler.matchLocale(systemLocale);
        Assert.assertEquals(LocaleHandler.SupportLocale.KOREAN, a);
    }


    @Test
    public void ifWrongLocale() {
        Locale.setDefault(new Locale("haha", "HAHA"));
        LocaleHandler localeHandler = new LocaleHandler();
        String systemLocale = localeHandler.getSystemLocale();
        Assert.assertEquals("haha", systemLocale);
        Assert.assertNotEquals("kr", systemLocale);

        LocaleHandler.SupportLocale a = localeHandler.matchLocale(systemLocale);

        Assert.assertEquals(LocaleHandler.SupportLocale.ENGLISH, a);
    }

    @Test
    public void localeMessage() {
        Locale.setDefault(new Locale("en"));
        LocaleHandler localeHandler = new LocaleHandler();
        String msg = localeHandler.getMessage(LocaleHandler.MessageKey.WELCOME_MESSAGE);
        Assert.assertEquals("Welcome, Iam 'CLI console'.", msg);
    }
}
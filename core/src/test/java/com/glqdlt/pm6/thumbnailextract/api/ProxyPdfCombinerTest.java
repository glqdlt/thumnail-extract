package com.glqdlt.pm6.thumbnailextract.api;

import com.glqdlt.pm6.thumbnailextract.impl.CombineImageToPdf;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

public class ProxyPdfCombinerTest {


    public static class PrePostProxyInvocationHandler<T> implements InvocationHandler {
        private T origin;
        private Consumer<T> preCallBack;
        private Consumer<T> postCallBack;


        public PrePostProxyInvocationHandler(T origin, Consumer<T> pre, Consumer<T> post) {
            this.origin = origin;
            this.preCallBack = pre;
            this.postCallBack = post;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                preCallBack.accept(origin);
                Object obj = method.invoke(origin, args);
                postCallBack.accept(origin);
                return obj;
            }catch(Throwable e){
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void name() {

        PdfCombiner pdfCombiner = new CombineImageToPdf();
        PdfCombiner asd = (PdfCombiner) Proxy
                .newProxyInstance(
                        pdfCombiner.getClass().getClassLoader(),
                        new Class[]{PdfCombiner.class},
                        new PrePostProxyInvocationHandler<>(pdfCombiner, (x) -> {
                            System.out.println("pre:"+x.getClass().getTypeName());
                        }, (x) -> {
                            System.out.println("post:"+x.getClass().getTypeName());
                        }));

        System.out.println(asd.combineToImages(null));

    }
}
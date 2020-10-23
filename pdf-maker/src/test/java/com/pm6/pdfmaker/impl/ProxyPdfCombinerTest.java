package com.pm6.pdfmaker.impl;

import com.pm6.pdfmaker.api.PdfMaker;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;
/**
 * @author glqdlt
 * 2020-10-23
 */
public class ProxyPdfCombinerTest {
    /**
     * @see <a href='https://docs.oracle.com/javase/7/docs/api/java/lang/reflect/Proxy.html'>https://docs.oracle.com/javase/7/docs/api/java/lang/reflect/Proxy.html</a>
     * @param <T> 프록시 대상 인스턴스
     */
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

        PdfMaker pdfCombiner = new MultiThreadPdfMaker();
        PdfMaker asd = (PdfMaker) Proxy
                .newProxyInstance(
                        pdfCombiner.getClass().getClassLoader(),
                        new Class[]{PdfMaker.class},
                        new PrePostProxyInvocationHandler<>(pdfCombiner, (x) -> {
                            System.out.println("pre:"+x.getClass().getTypeName());
                        }, (x) -> {
                            System.out.println("post:"+x.getClass().getTypeName());
                        }));

        System.out.println(asd.combineToImages(null));

    }
}
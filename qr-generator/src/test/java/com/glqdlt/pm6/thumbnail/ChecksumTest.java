package com.glqdlt.pm6.thumbnail;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class ChecksumTest {
    @Test
    public void testMd5Checksum() {
        Md5Checksum md5Checksum = new Md5Checksum();
        Assert.assertEquals("2095312189753DE6AD47DFE20CBE97EC", md5Checksum.digest(new ByteArrayInputStream("hello-world".getBytes())));
    }
}
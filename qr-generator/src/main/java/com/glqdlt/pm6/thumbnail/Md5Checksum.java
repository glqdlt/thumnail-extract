package com.glqdlt.pm6.thumbnail;

import javax.xml.bind.DatatypeConverter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import com.glqdlt.pm6.commons.SimpleRuntimeException;

/**
 * @author glqdlt
 */
public class Md5Checksum implements Checksum{
    public String digest(InputStream source){
        try (InputStreamReader ccc = new InputStreamReader(source)){
            MessageDigest zxc = MessageDigest.getInstance("md5");
            while(ccc.ready()){
                zxc.update((byte)ccc.read());
            }
            return DatatypeConverter
                    .printHexBinary(zxc.digest()).toUpperCase();
        } catch (Throwable e) {
            throw new SimpleRuntimeException(e);
        }
    }
}

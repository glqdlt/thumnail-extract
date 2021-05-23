package com.glqdlt.pm6.thumbnail;

import java.io.InputStream;

public interface Checksum {
    String digest(InputStream source);
}

package org.tnmk.common.jpa.columnconverter.json;

public class JsonConversionException extends RuntimeException {
    public JsonConversionException(String s, Throwable throwable) {
        super(s, throwable);
    }
}

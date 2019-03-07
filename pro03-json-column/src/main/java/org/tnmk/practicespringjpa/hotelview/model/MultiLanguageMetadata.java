package org.tnmk.practicespringjpa.hotelview.model;


public class MultiLanguageMetadata {
    private MultiLanguageMetadataType type;
    private String value;

    public MultiLanguageMetadata(MultiLanguageMetadataType type, String value) {
        this.type = type;
        this.value = value;
    }

    public MultiLanguageMetadataType getType() {
        return type;
    }

    public void setType(MultiLanguageMetadataType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

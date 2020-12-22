package org.tnmk.practicespringjpa.pro06partialupdate.sample.entity;

import org.tnmk.practicespringjpa.pro06partialupdate.sample.repository.SampleJdbcRepostiory;

/**
 * This class show an example that we can compose many columns into one field.
 * <br/>
 * The {@link #url} is not an actual column in DB.<br/>
 * It's actually composited from any different data in DB.
 */
public class SampleEntityWithUrl extends SampleEntity {
    /**
     * The select column alias 'url' in query {@link SampleJdbcRepostiory} must match with the {@link #url} field name. You cannot use {@link javax.persistence.Column} here.
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package org.tnmk.practicespringjpa.pro01simpleentity.sample.entity;

import org.tnmk.practicespringjpa.pro01simpleentity.sample.repository.SampleJdbcRepostiory;

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

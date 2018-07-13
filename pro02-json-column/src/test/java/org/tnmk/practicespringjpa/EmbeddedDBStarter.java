package org.tnmk.practicespringjpa;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;

import java.io.IOException;

public class EmbeddedDBStarter {
    public static final EmbeddedPostgres EMBEDDED_POSTGRES = construct();

    private static EmbeddedPostgres construct() {
        try {
            return EmbeddedPostgres.builder().start();
        } catch (IOException e) {
            throw new RuntimeException("Cannot start EmbeddedPostgres: " + e.getMessage(), e);
        }
    }
}

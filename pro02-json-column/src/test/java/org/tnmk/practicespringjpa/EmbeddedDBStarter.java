package org.tnmk.practicespringjpa;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;

import java.io.IOException;

public class EmbeddedDBStarter {
    public static EmbeddedPostgres embeddedPostgres;

    static {
        try {
            embeddedPostgres = EmbeddedPostgres.start();
        } catch (IOException e) {
            throw new RuntimeException("Cannot start EmbeddedPostgres: " + e.getMessage(), e);
        }
    }
}

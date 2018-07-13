package org.tnmk.practicespringjpa;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;

import java.io.IOException;

public class EmbeddedDBStarter {
    public static final EmbeddedPostgres EMBEDDED_POSTGRES = construct();

    private static EmbeddedPostgres construct() {
        try {
            return EmbeddedPostgres.builder()
//                .
//                .setServerConfig("dbName","practice_spring_jpa_db")
//                .setServerConfig("userName","postgres")
//                .setConnectConfig("dbName", "practice_spring_jpa_db")
//                .setConnectConfig("userName", "postgres")
//                .setPort(3609)
                .start();
        } catch (IOException e) {
            throw new RuntimeException("Cannot start EmbeddedPostgres: " + e.getMessage(), e);
        }
    }
}

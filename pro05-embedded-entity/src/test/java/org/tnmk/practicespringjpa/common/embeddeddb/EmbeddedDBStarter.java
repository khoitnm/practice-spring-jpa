package org.tnmk.practicespringjpa.common.embeddeddb;

import com.wix.mysql.EmbeddedMysql;

import static com.wix.mysql.distribution.Version.v5_7_latest;

/**
 * https://github.com/wix/wix-embedded-mysql
 */
public class EmbeddedDBStarter {
    //TODO tmp disable testcontainers to make our test faster. Use local DB instead.
    public static final EmbeddedMysql EMBEDDED_MYSQL = null;//startEmbeddedDB();
    private static EmbeddedMysql startEmbeddedDB() {
        EmbeddedMysql embeddedMysql = EmbeddedMysql.anEmbeddedMysql(v5_7_latest)
            .addSchema("practice_spring_jpa_db")
            .start();
        return embeddedMysql;
    }
}

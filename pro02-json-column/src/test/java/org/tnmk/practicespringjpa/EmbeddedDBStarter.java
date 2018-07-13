package org.tnmk.practicespringjpa;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.SchemaConfig;

import java.io.IOException;

import static com.wix.mysql.distribution.Version.v5_7_latest;

/**
 * https://github.com/wix/wix-embedded-mysql
 */
public class EmbeddedDBStarter {
    public static final EmbeddedMysql EMBEDDED_MYSQL = construct();
    private static EmbeddedMysql construct() {
        EmbeddedMysql embeddedMysql = EmbeddedMysql.anEmbeddedMysql(v5_7_latest)
            .addSchema("practice_spring_jpa_db")
            .start();
        return embeddedMysql;
    }
}

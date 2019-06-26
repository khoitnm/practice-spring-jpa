package org.tnmk.common.jpa.embeddedMySQL;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;

import static com.wix.mysql.distribution.Version.v5_7_latest;

/**
 * https://github.com/wix/wix-embedded-mysql
 */
public class EmbeddedDBStarter {
    public static final EmbeddedMysql EMBEDDED_MYSQL = startEmbeddedDB();

    private static EmbeddedMysql startEmbeddedDB() {
        MysqldConfig mysqldConfig = MysqldConfig.aMysqldConfig(v5_7_latest)
                .withPort(12345)
                .withCharset(Charset.UTF8)
                .withUser("auser", "sa")
                .build();
        EmbeddedMysql embeddedMysql = EmbeddedMysql.anEmbeddedMysql(mysqldConfig)
                .addSchema("practice_spring_jpa_db")
                .start();
        return embeddedMysql;
    }
}

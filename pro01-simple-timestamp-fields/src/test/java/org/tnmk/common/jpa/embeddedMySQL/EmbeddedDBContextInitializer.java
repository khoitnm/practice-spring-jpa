package org.tnmk.common.jpa.embeddedMySQL;

import com.wix.mysql.config.MysqldConfig;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class EmbeddedDBContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(final ConfigurableApplicationContext configurableApplicationContext) {
        MysqldConfig mysqldConfig = EmbeddedDBStarter.EMBEDDED_MYSQL.getConfig();
        String dataSourceUrl = String.format("jdbc:mysql://localhost:%s/%s?useSSL=false", mysqldConfig.getPort(), "practice_spring_jpa_db");
        String propertyUrl = "spring.datasource.url=" + dataSourceUrl;
        String propertyUserName = "spring.datasource.username=" + mysqldConfig.getUsername();
        String propertyPassword = "spring.datasource.password=" + mysqldConfig.getPassword();
        String propertySchemaUserName = "spring.datasource.schema-username=" + mysqldConfig.getUsername();
        String propertySchemaPassword = "spring.datasource.schema-password=" + mysqldConfig.getPassword();


        TestPropertyValues.of(
            propertyUrl
            , propertyUserName
            , propertyPassword
            , propertySchemaUserName
            , propertySchemaPassword
        ).applyTo(configurableApplicationContext.getEnvironment());
    }
}
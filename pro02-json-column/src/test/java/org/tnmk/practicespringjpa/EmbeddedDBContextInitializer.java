package org.tnmk.practicespringjpa;

import com.sun.istack.internal.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class EmbeddedDBContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(@NotNull final ConfigurableApplicationContext configurableApplicationContext) {
        String propertyUrl = "spring.datasource.url=" + EmbeddedDBStarter.EMBEDDED_POSTGRES.getJdbcUrl("postgres","postgres");
        TestPropertyValues.of(propertyUrl).applyTo(configurableApplicationContext.getEnvironment());
    }
}
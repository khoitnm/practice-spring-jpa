package org.tnmk.practicespringjpa;

import com.sun.istack.internal.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class EmbeddedDBContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(@NotNull final ConfigurableApplicationContext configurableApplicationContext) {
//        TestPropertyValues.of(
//            "spring.datasource.url=" + embeddedPostgres. + "?useSSL=false",
//            "spring.datasource.username=" + MY_SQL_CONTAINER.getUsername(),
//            "spring.datasource.password=" + MY_SQL_CONTAINER.getPassword(),
//            "spring.datasource.schema-username=" + MY_SQL_CONTAINER.getUsername(),
//            "spring.datasource.schema-password=" + MY_SQL_CONTAINER.getPassword()
//        ).applyTo(configurableApplicationContext.getEnvironment());
    }
}
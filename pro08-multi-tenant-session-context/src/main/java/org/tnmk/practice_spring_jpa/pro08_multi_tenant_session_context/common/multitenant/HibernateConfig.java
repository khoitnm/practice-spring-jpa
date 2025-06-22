package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant;

import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.Pro08MultiTenantSessionContextApplication;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateConfig {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            MultiTenantConnectionProvider<?> multiTenantConnectionProvider,
            CurrentTenantIdentifierResolver<?> currentTenantIdentifierResolver,
            JpaProperties jpaProperties,
            JpaVendorAdapter jpaVendorAdapter
    ) {
        logger.debug("Creating entityManagerFactory");

        Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
        jpaPropertiesMap.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, PhysicalNamingStrategyStandardImpl.class.getCanonicalName());
        jpaPropertiesMap.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, SpringImplicitNamingStrategy.class.getCanonicalName());
        jpaPropertiesMap.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        jpaPropertiesMap.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(Pro08MultiTenantSessionContextApplication.class.getPackage().getName());
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setJpaPropertyMap(jpaPropertiesMap);

        logger.debug("Created EntityManagerFactory successfully: \n" + jpaPropertiesMap);

        return em;
    }
}

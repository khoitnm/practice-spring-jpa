package org.tnmk.practicespringjpa.pro07multitenant.common.multitenant;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.tnmk.practicespringjpa.pro07multitenant.Pro07MultiTenantApplication;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateConfig {
  private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Bean
  JpaVendorAdapter jpaVendorAdapter() {
    return new HibernateJpaVendorAdapter();
  }

  @Bean
  LocalContainerEntityManagerFactoryBean entityManagerFactory(
      DataSource dataSource,
      MultiTenantConnectionProvider multiTenantConnectionProvider,
      CurrentTenantIdentifierResolver currentTenantIdentifierResolver,
      JpaProperties jpaProperties,
      JpaVendorAdapter jpaVendorAdapter
  ) {
    logger.debug("Creating entityManagerFactory");

    Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
    jpaPropertiesMap.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, SpringPhysicalNamingStrategy.class.getCanonicalName());
    jpaPropertiesMap.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, SpringImplicitNamingStrategy.class.getCanonicalName());
    jpaPropertiesMap.put(AvailableSettings.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
    jpaPropertiesMap.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
    jpaPropertiesMap.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    //Scan all classes in the package that has the main application class.
    em.setPackagesToScan(Pro07MultiTenantApplication.class.getPackage().getName());
    em.setJpaVendorAdapter(jpaVendorAdapter);
    em.setJpaPropertyMap(jpaPropertiesMap);

    logger.debug("Created EntityManagerFactory successfully: \n" + jpaPropertiesMap);

    return em;
  }
}

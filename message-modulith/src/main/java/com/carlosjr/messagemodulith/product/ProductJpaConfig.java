package com.carlosjr.messagemodulith.product;

import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@RequiredArgsConstructor
//@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.carlosjr.messagemodulith.product",
        entityManagerFactoryRef = "productEntityManagerFactory",
        transactionManagerRef = "productTransactionManager")
public class ProductJpaConfig {

    @Qualifier("productDataSource")
    private final DataSource productDataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean productEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(productDataSource);
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setPackagesToScan("com.carlosjr.messagemodulith.product");
        return em;
    }

    @Bean
    public PlatformTransactionManager productTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(productEntityManagerFactory().getObject());
        return transactionManager;
    }
}
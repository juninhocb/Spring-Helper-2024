package com.carlosjr.messagemodulith.customer;

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
@EnableJpaRepositories(basePackages = "com.carlosjr.messagemodulith.customer",
        entityManagerFactoryRef = "customerEntityManagerFactory",
        transactionManagerRef = "customerTransactionManager")
public class CustomerJpaConfig {

    @Qualifier("customerDataSource")
    private final DataSource customerDataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(customerDataSource);
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setPackagesToScan("com.carlosjr.messagemodulith.customer");
        return em;
    }

    @Bean
    public PlatformTransactionManager customerTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(customerEntityManagerFactory().getObject());
        return transactionManager;
    }
}
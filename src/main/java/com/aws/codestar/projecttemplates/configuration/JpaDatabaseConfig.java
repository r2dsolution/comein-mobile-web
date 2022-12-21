package com.aws.codestar.projecttemplates.configuration;

import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.aws.codestar.comein.utils.SecretManagerUtils;
import javax.persistence.EntityManagerFactory;

@Configuration
@PropertySource("classpath:comein.properties")
//@EnableJpaRepositories("com.aws.codestar.comein.repository")
//@EnableTransactionManagement
@EnableJdbcRepositories("com.aws.codestar.comein.jdbc.repository")
public class JpaDatabaseConfig  extends AbstractJdbcConfiguration {

    @Value("${comein.db.driver}")
    public String driver;

    @Value("${comein.mode}")
    public String mode;

    @Bean
    DataSource dataSource(AWSSecretsManager secretManager) {
        Map<String, String> awsSecrets = SecretManagerUtils.getSecret(secretManager, mode + "/db/postgresql/comein");
        String host = awsSecrets.get("host");
        String port = awsSecrets.get("port");
        String database = awsSecrets.get("dbInstance");
        String username = awsSecrets.get("username");
        String password = awsSecrets.get("password");

        String url = "jdbc:postgresql://" + host + ":+" + port + "/" + database;
        System.out.println("url=" + url);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;

    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(false);
//        vendorAdapter.setShowSql(true);
//       // vendorAdapter.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.aws.codestar.comein.entity");
//
//        factory.setDataSource(ds);
//        return factory;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory);
//        return txManager;
//    }

    @Bean
    NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) { 
        return new NamedParameterJdbcTemplate(dataSource);
    }

    
   
    @Bean
    TransactionManager transactionManager(DataSource dataSource) {                     
        return new DataSourceTransactionManager(dataSource);
    }
}
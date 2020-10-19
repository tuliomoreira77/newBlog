package com.mmoreira.blog.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaConfig {
	@Value("${database.connect.uri}")
	private String databaseUrl;
	@Value("${database.username}")
	private String database_user;
	@Value("${database.password}")
	private String database_pass;
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("com.mmoreira.blog.repository.entity");
        return entityManagerFactoryBean;
    }
	
	@Bean
    public JpaVendorAdapter jpaVendorAdapter(Properties dataSourceProperties){
		boolean showSql = false;
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL);
        adapter.setShowSql(showSql);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        return adapter;
    }
	
	@Bean(name = "dataSourceProperties")		
	public Properties dataSourceProperties(ServletContext servletContext){
		Properties properties = new Properties();
		properties.put("database.driver", "org.postgresql.Driver");
		properties.put("database.url", databaseUrl);
		properties.put("database.username", database_user);
		properties.put("database.password", database_pass);
		properties.put("database.show.sql", "false");
		return properties;
	}
	
	@Bean
	public DataSource dataSource(Properties dataSourceProperties){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dataSourceProperties.getProperty("database.driver"));
		dataSource.setUrl(dataSourceProperties.getProperty("database.url"));
		dataSource.setUsername(dataSourceProperties.getProperty("database.username"));
		dataSource.setPassword(dataSourceProperties.getProperty("database.password"));
		return dataSource;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
}

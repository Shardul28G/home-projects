package com.iot.waterTank.configuration;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "mysqlEntityManagerFactory", transactionManagerRef = "transactionManager", basePackages = {
		"com.iot.waterTank.mysql.*" })
public class MysqlConfig {
	
	private static final Logger logger = LogManager.getLogger(MysqlConfig.class);

	@Value("${mysql.datasource.jdbc-url}")
	String mysqlurl;

	@Value("${mysql.datasource.username}")
	String mysqluname;

	@Value("${mysql.datasource.password}")
	String mysqlpswd;

	@Value("${mysql.datasource.driver-class-name}")
	String mysqldriverclassname;

	@Primary
	@Bean(name = "mysqlDataSource")
	public DataSource dataSource() {

		logger.debug(mysqldriverclassname);

		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(mysqldriverclassname);
		dataSourceBuilder.url(mysqlurl);
		dataSourceBuilder.username(mysqluname);
		dataSourceBuilder.password(mysqlpswd);
		return dataSourceBuilder.build();
	}
	
	
	@Primary
	@Bean("mysqlEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean mysqlentityManagerFactory(
			@Qualifier("mysqlDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		// properties.put("mysql.jpa.hibernate.ddl-auto", "update");
		
		
		return builder.dataSource(dataSource).properties(properties).packages("com.iot.waterTank.mysql.model")
				.build();
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager mysqlTransactionManager(
			@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory) {
		return new JpaTransactionManager(mysqlEntityManagerFactory);
	}
}

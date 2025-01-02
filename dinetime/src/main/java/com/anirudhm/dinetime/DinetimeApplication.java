package com.anirudhm.dinetime;

import com.anirudhm.dinetime.models.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DinetimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DinetimeApplication.class, args);
	}

	@Bean
	public SessionFactory getSessionFactory() {
		Map<String, Object> settings = new HashMap<>();
		settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/dinetime");
		settings.put("hibernate.connection.username", "root");
		settings.put("hibernate.connection.password", "sql56789");

		settings.put("hibernate.hbm2ddl.auto", "update");
		settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		settings.put("hibernate.dialect.storage_engine", "innodb");
		settings.put("hibernate.show-sql", "true");

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(settings)
				.build();
		MetadataSources metaDataSources = new MetadataSources(serviceRegistry);
		metaDataSources.addPackage("com.anirudhm.dinetime.models");
		metaDataSources.addAnnotatedClass(User.class);
		metaDataSources.addAnnotatedClass(Restaurant.class);
		metaDataSources.addAnnotatedClass(Item.class);
		metaDataSources.addAnnotatedClass(Order.class);
		metaDataSources.addAnnotatedClass(OrderItem.class);
		Metadata metaData = metaDataSources.buildMetadata();

		return metaData.getSessionFactoryBuilder().build();
	}

}

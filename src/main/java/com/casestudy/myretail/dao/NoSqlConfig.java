package com.casestudy.myretail.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/*
 This class is used to define database configuration via host, port, keyspace and connection to it.
 */
@Configuration
@PropertySource(value = { "classpath:cassandra.properties" })
@EnableCassandraRepositories(basePackages = { "com.casestudy.myretail.dao" })
public class NoSqlConfig {

	@Autowired
    Environment environment;

	@Bean
	public CassandraClusterFactoryBean cluster() {

		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(environment.getProperty("myRetail.cassandra.host"));
		cluster.setPort(Integer.parseInt(environment.getProperty("myRetail.cassandra.port")));
		return cluster;

	}

	@Bean
	public CassandraMappingContext mappingContext() {
		return new BasicCassandraMappingContext();

	}

	@Bean
	public CassandraConverter converter() {
		return new MappingCassandraConverter(mappingContext());

	}

	@Bean
	public CassandraSessionFactoryBean session() {
		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(cluster().getObject());
		session.setKeyspaceName(environment.getProperty("myRetail.cassandra.keyspace"));
		session.setConverter(converter());
		session.setSchemaAction(SchemaAction.NONE);
		return session;

	}

	@Bean
	public CassandraOperations cassandraTemplate() {
		return new CassandraTemplate(session().getObject());

	}
}

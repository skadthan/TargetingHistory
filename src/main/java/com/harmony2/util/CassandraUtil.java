package com.harmony2.util;

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

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

/**
 * Utility class for getting the CassandraOperations object.
 * @version 1.0
 * @since Feb 02, 2018
 */

@Configuration
@PropertySource(value = { "classpath:cassandra.properties" })
public class CassandraUtil {

    /**
     * Constant String for Keyspace
     */
    private static final String KEYSPACE = "cassandra.keyspace";
    /**
     * Constant String for ContactPoints
     */
    private static final String CONTACTPOINTS = "cassandra.contactpoints";
    /**
     * Constant String for Port 
     */
    private static final String PORT = "cassandra.port";
    
    @Autowired
    private Environment environment;

    public CassandraUtil() {
        System.out.println("CassandraUtil()");
    }
    
    private String getKeyspaceName() {
        return environment.getProperty(KEYSPACE);       
    }
    
    private String getContactPoints() {
        return environment
                .getProperty(CONTACTPOINTS);        
    }
    
    private int getPortNumber() {
        return Integer.parseInt(environment
                .getProperty(PORT));        
    }

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(getContactPoints());
        cluster.setPort(getPortNumber());
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
    public CassandraSessionFactoryBean session() throws Exception {
        CassandraSessionFactoryBean cassandraSessionFactoryBean = new CassandraSessionFactoryBean();
        cassandraSessionFactoryBean.setCluster(cluster().getObject());
        cassandraSessionFactoryBean.setKeyspaceName(getKeyspaceName());
        cassandraSessionFactoryBean.setConverter(converter());
        cassandraSessionFactoryBean.setSchemaAction(SchemaAction.NONE);
        return cassandraSessionFactoryBean;
    }

    @Bean
    public CassandraOperations cassandraTemplate() throws Exception {
        return new CassandraTemplate(session().getObject());
    }
    
    @Bean
    public Session getPoolSession(){
        PoolingOptions poolingOptions = new PoolingOptions();
        poolingOptions
        .setCoreConnectionsPerHost(HostDistance.LOCAL,  4)
        .setMaxConnectionsPerHost( HostDistance.LOCAL, 10)
        .setMaxRequestsPerConnection(HostDistance.LOCAL, 32768)
        .setMaxRequestsPerConnection(HostDistance.REMOTE, 2000)
        .setHeartbeatIntervalSeconds(120);

        Cluster cluster = Cluster.builder()
            .addContactPoints(this.getContactPoints())
            .withPoolingOptions(poolingOptions)
            .build();

        Session session = cluster.connect(this.getKeyspaceName());
        return session;
        }
    public void testClusterConnection() {
    	DseCluster dseCluster=null;

    	try{
    		dseCluster=DseCluster.builder().addContactPoint(this.getContactPoints()).build();
    		DseSession dseSession=dseCluster.connect();
    		Row row=dseSession.execute("select release_version from system.local").one();
    		System.out.println("release_version--> "+row.getString("release_version"));
    		}
    	finally
    		{
    		if(dseCluster!=null)
    		dseCluster.close();
    		}
    }
}
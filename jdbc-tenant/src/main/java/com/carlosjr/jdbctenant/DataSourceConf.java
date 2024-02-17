package com.carlosjr.jdbctenant;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConf {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConf.class);
    @Bean
    public DataSource ocli(OneClientConfiguration config){

        DataSource ds = DataSourceBuilder.create()
                .url(config.getUrl())
                .username(config.getUsername())
                .password(config.getPassword())
                .build();

        try ( Connection connection = ds.getConnection()){

            String sql = "SELECT now() as DATETIME";

            Statement stmt = connection.createStatement();

            stmt.execute(sql);

            try (ResultSet rs = stmt.getResultSet()){

                String dateTime = "";
                while ( rs.next()){
                    dateTime = rs.getString("DATETIME");
                }

                logger.info("Database was succesffully loaded datetime is: '{}' ", dateTime);

            }


        } catch (SQLException e){
            logger.error("Error while creating datasource ocli {} ", e.getMessage());
        }

        return ds;

    }

    @Bean
    public DataSource tcli(TwoClientConfiguration config){

        return DataSourceBuilder
                .create()
                .url(config.getUrl())
                .username(config.getUsername())
                .password(config.getPassword())
                .build();

    }


    @Bean
    @Primary
    public DataSource dataSourceRouting(Map<String, DataSource> dataSources){

        Map<Object, Object> mappedDataSources = new HashMap<>();


        mappedDataSources.put(1, dataSources.get("ocli"));
        mappedDataSources.put(2, dataSources.get("tcli"));

        DataSourceRouting ds = new DataSourceRouting();
        ds.setTargetDataSources(mappedDataSources);
        ds.setDataSourceLookup(null);
        return ds;
    }

}

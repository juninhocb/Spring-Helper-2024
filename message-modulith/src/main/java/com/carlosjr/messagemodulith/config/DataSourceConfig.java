package com.carlosjr.messagemodulith.config;

import com.carlosjr.messagemodulith.customer.CustomerDataSourceConfig;
import com.carlosjr.messagemodulith.product.ProductDataSourceConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
    private final ResourceLoader resourceLoader;
    @Bean
    @Primary
    public DataSource customerDataSource(CustomerDataSourceConfig config) throws Exception {
        DataSource ds = DataSourceBuilder.create()
                .url(config.getUrl())
                .username(config.getUsername())
                .password(config.getPassword())
                .build();


        Map<Integer, String> queriesFromFile = getQueriesFromFile("classpath:InitCustomerDb.txt");

        initDatabase(queriesFromFile, ds);
        return ds;

    }
    @Bean
    public DataSource productDataSource(ProductDataSourceConfig config) throws Exception{

        DataSource ds = DataSourceBuilder.create()
                .url(config.getUrl())
                .username(config.getUsername())
                .password(config.getPassword())
                .build();

        Map<Integer, String> queriesFromFile = getQueriesFromFile("classpath:InitProductDb.txt");
        initDatabase(queriesFromFile, ds);
        return ds;
    }

    @Bean
    public CommandLineRunner clr(Map<String, DataSource> mapDatasource){
        return args -> {
            mapDatasource.forEach( ( key, value) -> {
                logger.info("DataSource injected {} ::: {} ", key, value );
            });
        };
    }

    private void initDatabase(Map<Integer, String> commands, DataSource ds) throws SQLException{

        try (Connection connection = ds.getConnection()){

            Statement stmt = connection.createStatement();
            commands.forEach( ( index, command ) -> {
                try {
                    stmt.execute(command);
                    logger.info("Index {} :::: {} executed! ", index, command );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }

    private Map<Integer, String> getQueriesFromFile(String locale) throws IOException {
        Resource resource = resourceLoader.getResource(locale);
        Map<Integer, String> commands = new HashMap<>();
        try (InputStream inputStream = resource.getInputStream()){

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String command;
            Integer counter = 0;
            while ( (command = bufferedReader.readLine()) != null ){
                counter ++;
                commands.putIfAbsent(counter, command);
            }
        }

        return commands;

    }

}

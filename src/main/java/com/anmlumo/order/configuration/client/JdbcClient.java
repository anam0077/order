package com.anmlumo.order.configuration.client;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcClient {

    @Bean
    public static DataSource mysqlDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/order");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

}

package com.anmlumo.order.repository;

import com.anmlumo.order.configuration.client.JdbcClient;
import com.anmlumo.order.model.OrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class OrderlistRepository {

    @Autowired
    final DataSource dataSource = JdbcClient.mysqlDataSource();

    public Integer insertOrderList(OrderList orderList){

        Connection connection = null;
        PreparedStatement prepStatement = null;
        Integer result=0;
        try {
            // Attempt for connection to MySQL
            connection = dataSource.getConnection();
            prepStatement = connection.prepareStatement("insert into orderlist (customerId, product, amount) values (?,?,?)");
            prepStatement.setString(1, orderList.getCustomerId());
            prepStatement.setString(2, orderList.getProduct());
            prepStatement.setInt(3, orderList.getAmount());
            result = prepStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.printf("Error create orderlist : %s", e.getMessage());
        }
        return result;
    }

    public Integer deleteAll(){
        Connection connection = null;
        PreparedStatement prepStatement = null;
        Integer result=0;
        try {
            // Attempt for connection to MySQL
            connection = dataSource.getConnection();
            prepStatement = connection.prepareStatement("delete from orderlist");
            result = prepStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.printf("Error create orderlist : %s", e.getMessage());
        }
        return result;
    }
}

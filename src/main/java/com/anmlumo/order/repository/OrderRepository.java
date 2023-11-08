package com.anmlumo.order.repository;

import com.anmlumo.order.configuration.client.JdbcClient;
import com.anmlumo.order.model.OrderQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


@Repository
public class OrderRepository {

    @Autowired
    final DataSource dataSource = JdbcClient.mysqlDataSource();

    public Integer createOrder(Integer queueNumber, String customerId, String status){
        Connection connection = null;
        PreparedStatement prepStatement = null;
        Integer result=0;
        try {
            // Attempt for connection to MySQL
            connection = dataSource.getConnection();

            // produce date now
            Date date=new Date();
            Object objectOrderAt = new java.sql.Timestamp(date.getTime());

            prepStatement = connection.prepareStatement("insert into orderqueue (queueNo, customerId, status,  orderAt) values (?,?,?,?)");
            prepStatement.setInt(1, queueNumber);
            prepStatement.setString(2, customerId);
            prepStatement.setString(3, status);
            prepStatement.setObject(4, objectOrderAt);
            result = prepStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.printf("Error create order : %s", e.getMessage());
        }
        return result;
    }

    public List<OrderQueue> getOrderQuee(){
        Connection connection = null;
        PreparedStatement prepStatement = null;
        List<OrderQueue> result=new ArrayList<>();
        try{
            SimpleDateFormat dateFrmt= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            connection = dataSource.getConnection();
            prepStatement = connection.prepareStatement("select * from orderqueue order by queueNo asc");
            ResultSet rs =prepStatement.executeQuery();
            while(rs.next()){
                OrderQueue obj=new OrderQueue();
                obj.setQueueNo(Integer.parseInt(rs.getString("queueNo")));
                obj.setStatus(rs.getString("status"));
                obj.setOrderAt(dateFrmt.parse(rs.getString("orderAt")));
                result.add(obj);
            }
        }catch (Exception e){
            System.out.printf("Error get orderqueue : %s", e.getMessage());
        }
        return result;
    }

    public List<Map<String,String>> getOrderQueueDetail(){
        Connection connection = null;
        PreparedStatement prepStatement = null;
        List<Map<String,String>> result=new ArrayList<>();

        try{
            connection = dataSource.getConnection();
            prepStatement = connection.prepareStatement("select queueNo, orderId, status, orderAt, productName, description, price, amount, orderqueue.customerId as customerId, customerName from orderqueue inner join " +
                    "(select orderId, product.name as productName, description, price, amount, customerId, co.name as customerName from product inner join (select customer.name, orderlist.product as orderId, orderlist.amount, orderlist.customerId from customer inner join orderlist on customer.customerId =orderlist.customerId) as co " +
                    "on product.productId=co.orderId) as cop on orderqueue.customerId=cop.customerId");
            ResultSet rs =prepStatement.executeQuery();
            while (rs.next()){
                Map<String,String> map=new HashMap<>();
                map.put("queueNo",rs.getString("queueNo"));
                map.put("orderId",rs.getString("orderId"));
                map.put("status",rs.getString("status"));
                map.put("orderAt",rs.getString("orderAt"));
                map.put("productName",rs.getString("productName"));
                map.put("description",rs.getString("description"));
                map.put("price",rs.getString("price"));
                map.put("amount",rs.getString("amount"));
                map.put("customerId",rs.getString("customerId"));
                map.put("customerName",rs.getString("customerName"));
                result.add(map);
            }
        }catch (Exception e){
            System.out.printf("Error get orderqueue : %s", e.getMessage());
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
            prepStatement = connection.prepareStatement("delete from orderqueue");
            result = prepStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.printf("Error create orderlist : %s", e.getMessage());
        }
        return result;
    }
}

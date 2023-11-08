package com.anmlumo.order.repository;

import com.anmlumo.order.configuration.client.JdbcClient;
import com.anmlumo.order.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    @Autowired
    final DataSource dataSource = JdbcClient.mysqlDataSource();

    public Integer insertProduct(String productName, String productDescription, Integer price){

        Connection connection = null;
        PreparedStatement prepStatement = null;
        Integer result=0;
        try {
            // Attempt for connection to MySQL
            connection = dataSource.getConnection();

            // produce date now
            Date date=new Date();
            Object objectCreatedAt = new java.sql.Timestamp(date.getTime());

            prepStatement = connection.prepareStatement("insert into product (productId, name, description, price, createdAt) values (?,?,?,?,?)");
            prepStatement.setString(1, UUID.randomUUID().toString());
            prepStatement.setString(2, productName);
            prepStatement.setString(3, productDescription);
            prepStatement.setInt(4, price);
            prepStatement.setObject(5, objectCreatedAt);
            result = prepStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.printf("Error create product : %s", e.getMessage());
        }
        return result;
    }


    public Integer deleteProduct(String productId){
        Connection connection = null;
        PreparedStatement prepStatement = null;
        Integer result=0;

        try{
            // Attempt for connection to MySQL
            connection = dataSource.getConnection();
            prepStatement = connection.prepareStatement("delete from product where productId=?");
            prepStatement.setString(1, productId);
            result = prepStatement.executeUpdate();
        }catch (Exception e){
            System.out.printf("Error delete product : %s", e.getMessage());
        }
        return  result;
    }

    public List<Product> getProduct(){
        Connection connection = null;
        PreparedStatement prepStatement = null;
        List<Product> result=new ArrayList<>();
        try{
            SimpleDateFormat dateFrmt= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            connection = dataSource.getConnection();
            prepStatement = connection.prepareStatement("select * from product");
            ResultSet rs =prepStatement.executeQuery();
            while(rs.next()){
                Product obj=new Product();
                obj.setName(rs.getString("name"));
                obj.setProductId(rs.getString("productId"));
                obj.setPrice(Integer.parseInt(rs.getString("price")));
                obj.setDescription(rs.getString("description"));
                obj.setCreatedAt(dateFrmt.parse(rs.getString("createdAt")));
                result.add(obj);
            }
        }catch (Exception e){
            System.out.printf("Error get product : %s", e.getMessage());
        }
        return result;
    }
}

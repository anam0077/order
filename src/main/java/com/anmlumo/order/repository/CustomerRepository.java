package com.anmlumo.order.repository;

import com.anmlumo.order.configuration.client.JdbcClient;
import com.anmlumo.order.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class CustomerRepository {

    @Autowired
    final DataSource dataSource = JdbcClient.mysqlDataSource();

    public Integer insertCustomer(String customerName){

        Connection connection = null;
        PreparedStatement prepStatement = null;
        Integer result=0;
        try {
            // Attempt for connection to MySQL
            connection = dataSource.getConnection();
            prepStatement = connection.prepareStatement("insert into customer (customerId, name) values (?,?)");
            prepStatement.setString(1, UUID.randomUUID().toString());
            prepStatement.setString(2, customerName);
            result = prepStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.printf("\n Error create customer : %s", e.getMessage());
        }
        return result;
    }

    public Customer getCustomerByName(String name){
        Connection connection = null;
        PreparedStatement prepStatement = null;
        Customer result=new Customer();
        List<Customer> customerList=new ArrayList<>();
        try{
            connection = dataSource.getConnection();
            prepStatement = connection.prepareStatement("select * from customer where name=?");
            prepStatement.setString(1, name);
            ResultSet rs  =prepStatement.executeQuery();
            while (rs.next()){
                Customer obj=new Customer();
                obj.setCustomerId(rs.getString("customerId"));
                obj.setName(rs.getString("name"));
                customerList.add(obj);
            }
            if(customerList.size()==1){
                result=customerList.get(0);
            }else{
                throw new Exception("duplicate name");
            }
            return result;
        }catch (Exception e){
            System.out.printf("\n Error get customer by name : %s", e.getMessage());
        }
        return result;
    }

    public Boolean isCustomerExistByName(String name){
        Connection connection = null;
        PreparedStatement prepStatement = null;
        Boolean result=false;
        List<Customer> customerList=new ArrayList<>();
        try{
            connection = dataSource.getConnection();
            prepStatement = connection.prepareStatement("select * from customer where name=?");
            prepStatement.setString(1, name);
            ResultSet rs  =prepStatement.executeQuery();
            while (rs.next()){
                Customer obj=new Customer();
                obj.setCustomerId(rs.getString("customerId"));
                obj.setName(rs.getString("name"));
                customerList.add(obj);
            }
            if(customerList.size()>=1){
               result=true;
            }else{
                result=false;
            }

        }catch (Exception e){
            System.out.printf("\n Error get customer by name : %s", e.getMessage());
        }
        return result;
    }
}

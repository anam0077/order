package com.anmlumo.order.usecase;

import com.anmlumo.order.model.GenericResponse;
import com.anmlumo.order.model.Product;
import com.anmlumo.order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductProcess {

    @Autowired
    private ProductRepository repo;

    public ResponseEntity<?> newProduct(Product product){

        ResponseEntity<?> response;
        GenericResponse<String> res=new GenericResponse<>();

        try{
            Integer ret=repo.insertProduct(product.getName(), product.getDescription(), product.getPrice());
            if(ret==1){
                res.setCode("00");
                res.setStatus("ok");
                res.setData("success");
            }else{
                res.setCode("99");
                res.setStatus("ok");
                res.setData("Business Error");
            }
            response=ResponseEntity.status(200).body(res);
        }catch (Exception e){
            GenericResponse<String> errRes=new GenericResponse<>();
            errRes.setStatus("Err");
            errRes.setCode("91");
            errRes.setData("System Error");
            System.out.println(e.getMessage());
            response=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errRes);
        }
        return response;
    }


    public ResponseEntity<?> deleteProduct(String productId){
        ResponseEntity<?> response;
        GenericResponse<String> res=new GenericResponse<>();
        try{
            Integer ret=repo.deleteProduct(productId);
            if(ret==1){
                res.setCode("00");
                res.setStatus("ok");
                res.setData("success");
            }else {
                res.setCode("99");
                res.setStatus("failed");
                res.setData("Bussiness Error");
            }
            response=ResponseEntity.status(200).body(res);
        }catch (Exception e){
            GenericResponse<String> errRes=new GenericResponse<>();
            errRes.setStatus("Err");
            errRes.setCode("91");
            errRes.setData("System Error");
            System.out.println(e.getMessage());
            response=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errRes);
        }
        return response;
    }

    public ResponseEntity<?> getProduct(Map<String,String> param){
        ResponseEntity<?> response;
        GenericResponse<List<Product>> res=new GenericResponse<>();
        try{
            res.setStatus("ok");
            res.setCode("00");
            res.setData(repo.getProduct());
           response=ResponseEntity.status(200).body(res);
        }catch (Exception e){
            GenericResponse<String> errRes=new GenericResponse<>();
            errRes.setStatus("Err");
            errRes.setCode("91");
            errRes.setData("System Error");
            System.out.println(e.getMessage());
            response=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errRes);
        }
        return response;
    }
}

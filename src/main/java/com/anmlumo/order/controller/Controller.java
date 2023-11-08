package com.anmlumo.order.controller;


import com.anmlumo.order.model.OrderForm;
import com.anmlumo.order.model.Product;
import com.anmlumo.order.usecase.OrderProcess;
import com.anmlumo.order.usecase.ProductProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class Controller {

    @Autowired
    private ProductProcess productProcess;

    @Autowired
    private OrderProcess orderProcess;


    @PostMapping("/product/create")
    public ResponseEntity<?> create(@RequestBody Product product){
        return productProcess.newProduct(product);
    }


    @DeleteMapping("/product/delete")
    public ResponseEntity<?> delete(@RequestParam("productId") String productId){
        return productProcess.deleteProduct(productId);
    }

    @GetMapping("/product/get")
    public ResponseEntity<?> get(@RequestParam Map<String,String> param){
          return productProcess.getProduct(param);
    }


    @PostMapping("/order/create")
    public ResponseEntity<?> order(@RequestBody OrderForm body){
        return orderProcess.createOrder(body);
    }

    @GetMapping("/order/queue/retrieve-detail")
    public ResponseEntity<?> orderDetail(){
        return orderProcess.getQueueDetail();
    }

    @DeleteMapping("/order/queue/restart")
    public ResponseEntity<?> restartOrderQueue(){
        return orderProcess.clearOrderQueue();
    }

}

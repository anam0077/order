package com.anmlumo.order.usecase;


import com.anmlumo.order.model.*;
import com.anmlumo.order.repository.CustomerRepository;
import com.anmlumo.order.repository.OrderRepository;
import com.anmlumo.order.repository.OrderlistRepository;
import com.anmlumo.order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderProcess {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderlistRepository orderListRepository;

    public ResponseEntity<?> createOrder(OrderForm orderForm){
        ResponseEntity<?> response;
        GenericResponse<String> res=new GenericResponse<>();
        try{
            // check queeNumber
            List<OrderQueue> orderQueues =orderRepo.getOrderQuee();
            Integer sizeOrderQuee= orderQueues.size();
            Integer lastQueeNumber=0;
            if(sizeOrderQuee>0){
                lastQueeNumber= orderQueues.get(sizeOrderQuee-1).getQueueNo();
            }

            Integer orderQueeNum=0;
            if(lastQueeNumber==null || lastQueeNumber==0){
               orderQueeNum=1;
            }else {
                lastQueeNumber++;
                orderQueeNum=lastQueeNumber;
            }

            //check customerName existing
            if(!customerRepository.isCustomerExistByName(orderForm.getCustomer().getName())){
                // create customer
                customerRepository.insertCustomer(orderForm.getCustomer().getName());
            }

            // get customer id
            String customerId=customerRepository.getCustomerByName(orderForm.getCustomer().getName()).getCustomerId();

            //check product existing
                    //load from product repo
                    List<Product> product=productRepo.getProduct();
                    List<OrderList> finalOrderList=new ArrayList<>();


                    for(OrderList oL : orderForm.getOrderList()){
                        Boolean isProductExist=false;
                        for(Product prod : product){

                            if(prod.getProductId().equals(oL.getProduct())){
                                isProductExist=true;
                            }
                        }
                        if(!isProductExist){
                            throw new Exception("no product found");
                        }
                        oL.setCustomerId(customerId);
                        finalOrderList.add(oL);
                    }

                    // store to order list
                   for(OrderList ol : finalOrderList){
                       orderListRepository.insertOrderList(ol);
                   }
            // store order
            if(orderRepo.createOrder(orderQueeNum,customerId,"process")==1){
                res.setCode("00");
                res.setStatus("ok");
                res.setData("success");
            }else{
                res.setCode("99");
                res.setStatus("failed");
                res.setData("business error");
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


    public ResponseEntity<?> getQueueDetail(){
        ResponseEntity<?> response;
        GenericResponse<Map<String,Object>> res=new GenericResponse<>();
        try{

            // streams
            List<Map<String,String>> list= orderRepo.getOrderQueueDetail();
            Map<String, List<Map<String,String>>> collectQueue=list.stream().collect(
                    Collectors.groupingBy(a->a.get("queueNo"))
            );

            Map<String,Object> obj=new HashMap<>();
            obj.put("queueNo",collectQueue);
            res.setCode("00");
            res.setStatus("ok");
            res.setData(obj);

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


    public ResponseEntity<?> clearOrderQueue(){
        ResponseEntity<?> response;
        GenericResponse<String> res=new GenericResponse<>();

        try{
            //delete table orderqueue
            orderRepo.deleteAll();
            //delete table orderlist
            orderListRepository.deleteAll();
            res.setCode("00");
            res.setStatus("ok");
            res.setData("success");
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

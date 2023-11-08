package com.anmlumo.order.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderQueueDetail {
    private Integer queeNo;
    private List<Customer> customerList;

}



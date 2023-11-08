package com.anmlumo.order.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderQueue {
    private Integer queueNo;
    private Integer customerId;
    private String status;
    private Date orderAt;
}

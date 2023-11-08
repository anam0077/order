package com.anmlumo.order.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Product {
    private String productId;
    private String name;
    private String description;
    private Integer price;
    private Date createdAt;
}

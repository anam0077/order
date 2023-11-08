package com.anmlumo.order.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class OrderForm {
    private Customer customer;
    private List<OrderList> orderList;
}

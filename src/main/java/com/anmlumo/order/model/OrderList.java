package com.anmlumo.order.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderList {
      private Integer no;
      private String customerId;
      private String product;
      private Integer amount;
}

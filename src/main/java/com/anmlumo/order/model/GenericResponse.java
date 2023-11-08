package com.anmlumo.order.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenericResponse<T> {
    private String status;
    private String code;
    private T data;
}

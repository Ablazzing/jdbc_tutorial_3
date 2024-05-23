package org.javaacademy.jdbc_tutorial_3.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer companyId;
}

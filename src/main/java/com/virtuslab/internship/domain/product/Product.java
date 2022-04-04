package com.virtuslab.internship.domain.product;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class Product {
    String name;
    Type type;
    BigDecimal price;

    public enum Type {
        DAIRY, FRUITS, VEGETABLES, MEAT, GRAINS
    }
}

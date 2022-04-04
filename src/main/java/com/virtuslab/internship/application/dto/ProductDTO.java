package com.virtuslab.internship.application.dto;

import com.virtuslab.internship.domain.product.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
    String name;
    Product.Type type;
    BigDecimal price;
}

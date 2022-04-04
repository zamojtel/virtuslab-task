package com.virtuslab.internship.application.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketDTO {
    private UUID uuid;
    private List<ProductDTO> products;
}

package com.peercore.nexgen.Mini_inventory_management_system.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {

    private int id;
    private String name;
    private int stockQuantity;
    private int minQuantity;
    private int restockAmount;
}

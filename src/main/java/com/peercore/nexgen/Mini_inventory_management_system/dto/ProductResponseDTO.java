package com.peercore.nexgen.Mini_inventory_management_system.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ProductResponseDTO {

    private int id;
    private String name;
    private int stockQuantity;
    private int minQuantity;
    private int restockAmount;

    public ProductResponseDTO(int id, String name, int stockQuantity, int minQuantity, int restockAmount) {
        this.id = id;
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.minQuantity = minQuantity;
        this.restockAmount = restockAmount;
    }

}

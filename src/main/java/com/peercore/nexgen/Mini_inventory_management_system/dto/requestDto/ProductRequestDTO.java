package com.peercore.nexgen.Mini_inventory_management_system.dto.requestDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {

    private String name;
    private int stockQuantity;
    private int minQuantity;
    private int restockAmount;
}

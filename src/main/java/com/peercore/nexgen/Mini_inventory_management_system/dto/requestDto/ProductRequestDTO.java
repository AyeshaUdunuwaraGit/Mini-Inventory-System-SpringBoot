package com.peercore.nexgen.Mini_inventory_management_system.dto.requestDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private String name;
    private String priority;
    private int stockQuantity;
    private int minThreshold;
    private int restockQuantity;
}

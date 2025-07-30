package com.peercore.nexgen.Mini_inventory_management_system.dto.ResponseDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStatusResponseDTO {
    private int productId;
    private String productName;
    private String status; // ok / below_threshold / out_of_stock
    private int stockQuantity;
    private int minThreshold;
}

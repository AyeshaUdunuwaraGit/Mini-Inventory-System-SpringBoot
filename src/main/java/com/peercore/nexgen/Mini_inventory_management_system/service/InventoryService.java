package com.peercore.nexgen.Mini_inventory_management_system.service;

import com.peercore.nexgen.Mini_inventory_management_system.dto.ResponseDto.ProductStatusResponseDTO;
import com.peercore.nexgen.Mini_inventory_management_system.dto.requestDto.ProductRequestDTO;
import com.peercore.nexgen.Mini_inventory_management_system.dto.requestDto.PurchaseRequestDTO;
import com.peercore.nexgen.Mini_inventory_management_system.entity.Product;

import java.util.List;

public interface InventoryService {
    Product createProduct(ProductRequestDTO productRequestDTO);
    List<Product> getAllProducts();
    ProductStatusResponseDTO getProductStatus(int id);
    Product purchaseProduct(int id, PurchaseRequestDTO request);
    Product manualRestock(int id);
}

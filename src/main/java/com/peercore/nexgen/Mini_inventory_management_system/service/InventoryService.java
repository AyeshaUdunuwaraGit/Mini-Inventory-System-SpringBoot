package com.peercore.nexgen.Mini_inventory_management_system.service;

import com.peercore.nexgen.Mini_inventory_management_system.entity.Product;

import java.util.List;

public interface InventoryService {

    Product addProduct(String ProductName, int stockQuantity, int minQuantity, int restockAmount);

    Product purchaseProduct(int productId, int purchaseQuantity);

    List<Product> checkInventoryAndRestock(int productId);

    List<Product> getAllProducts();

    Product findProductById(int id);

    void deleteProduct(int id);
}

package com.peercore.nexgen.Mini_inventory_management_system.api;

import com.peercore.nexgen.Mini_inventory_management_system.dto.requestDto.*;
import com.peercore.nexgen.Mini_inventory_management_system.dto.ResponseDto.*;
import com.peercore.nexgen.Mini_inventory_management_system.entity.Product;
import com.peercore.nexgen.Mini_inventory_management_system.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService InventoryService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDTO request) {
        Product createdProduct = InventoryService.createProduct(request);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = InventoryService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<ProductStatusResponseDTO> getProductStatus(@PathVariable int id) {
        ProductStatusResponseDTO statusResponse = InventoryService.getProductStatus(id);
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<Product> purchaseProduct(
            @PathVariable int id,
            @RequestBody PurchaseRequestDTO request
    ) {
        Product product = InventoryService.purchaseProduct(id, request);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/{id}/restock")
    public ResponseEntity<Product> manualRestock(@PathVariable int id) {
        Product product = InventoryService.manualRestock(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
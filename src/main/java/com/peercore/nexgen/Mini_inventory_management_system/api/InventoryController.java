package com.peercore.nexgen.Mini_inventory_management_system.api;

import com.peercore.nexgen.Mini_inventory_management_system.dto.ProductResponseDTO;
import com.peercore.nexgen.Mini_inventory_management_system.dto.requestDto.ProductRequestDTO;
import com.peercore.nexgen.Mini_inventory_management_system.dto.requestDto.PurchaseRequestDTO;
import com.peercore.nexgen.Mini_inventory_management_system.entity.Product;
import com.peercore.nexgen.Mini_inventory_management_system.service.InventoryService;
import com.peercore.nexgen.Mini_inventory_management_system.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<StandardResponse> addProduct(@RequestBody ProductRequestDTO requestDTO) {
        Product product = inventoryService.addProduct(
                requestDTO.getName(),
                requestDTO.getStockQuantity(),
                requestDTO.getMinQuantity(),
                requestDTO.getRestockAmount()
        );

        return new ResponseEntity<>(
                new StandardResponse(201, mapToDTO(product), "Product added successfully."),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/purchase/{productId}")
    public ResponseEntity<StandardResponse> purchaseProduct(@PathVariable int productId,
                                                            @RequestBody PurchaseRequestDTO dto) {
        try {
            Product updated = inventoryService.purchaseProduct(productId, dto.getQuantity());
            return new ResponseEntity<>(
                    new StandardResponse(200, mapToDTO(updated), "Product purchased successfully."),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new StandardResponse(400, null, e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/restock/{productId}")
    public ResponseEntity<StandardResponse> restockIfNeeded(@PathVariable int productId) {
        try {
            List<Product> restocked = inventoryService.checkInventoryAndRestock(productId);
            List<ProductResponseDTO> dtos = restocked.stream().map(this::mapToDTO).collect(Collectors.toList());
            return new ResponseEntity<>(
                    new StandardResponse(200, dtos, "Restock check complete."),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new StandardResponse(404, null, e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllProducts() {
        List<ProductResponseDTO> dtos = inventoryService.getAllProducts()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(
                new StandardResponse(200, dtos, "All products fetched."),
                HttpStatus.OK
        );
    }

    @GetMapping("/find-by-id/{productId}")
    public ResponseEntity<StandardResponse> findProductById(@PathVariable int productId) {
        try {
            Product product = inventoryService.findProductById(productId);
            return new ResponseEntity<>(
                    new StandardResponse(200, mapToDTO(product), "Product found."),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new StandardResponse(404, null, e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<StandardResponse> deleteProduct(@PathVariable int productId) {
        try {
            inventoryService.deleteProduct(productId);
            return new ResponseEntity<>(
                    new StandardResponse(204, null, "Product deleted."),
                    HttpStatus.NO_CONTENT
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new StandardResponse(404, null, e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    private ProductResponseDTO mapToDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .stockQuantity(product.getStockQuantity())
                .minQuantity(product.getMinQuantity())
                .restockAmount(product.getRestockAmount())
                .build();
    }
}
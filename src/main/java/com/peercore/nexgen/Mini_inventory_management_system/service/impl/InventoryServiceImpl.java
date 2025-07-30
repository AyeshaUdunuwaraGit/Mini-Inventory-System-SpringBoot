package com.peercore.nexgen.Mini_inventory_management_system.service.impl;

import com.peercore.nexgen.Mini_inventory_management_system.dto.requestDto.*;
import com.peercore.nexgen.Mini_inventory_management_system.dto.ResponseDto.*;
import com.peercore.nexgen.Mini_inventory_management_system.entity.Product;
import com.peercore.nexgen.Mini_inventory_management_system.repository.ProductRepository;
import com.peercore.nexgen.Mini_inventory_management_system.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

 @Service
 @RequiredArgsConstructor
 public class InventoryServiceImpl implements InventoryService {

        private final ProductRepository productRepository;

        @Override
        public Product createProduct(ProductRequestDTO request) {
            int correctedThreshold = request.getMinThreshold();

            // Apply business rule for threshold
            if ("high".equalsIgnoreCase(request.getPriority()) && request.getMinThreshold() < 10) {
                correctedThreshold = 10;
            }

            // Category assignment
            String category = request.getRestockQuantity() > 50 ? "high_volume" : "low_volume";

            Product product = Product.builder()
                    .name(request.getName())
                    .priority(request.getPriority().toLowerCase())
                    .stockQuantity(request.getStockQuantity())
                    .minThreshold(correctedThreshold)
                    .restockQuantity(request.getRestockQuantity())
                    .category(category)
                    .build();

            return productRepository.save(product);
        }

        @Override
        public List<Product> getAllProducts() {
            return productRepository.findAll();
        }

        @Override
        public ProductStatusResponseDTO getProductStatus(int id) {
            Product product = getProductById(id);

            String status;
            if (product.getStockQuantity() == 0) {
                status = "out_of_stock";
            } else if (product.getStockQuantity() < product.getMinThreshold()) {
                // Auto-restock if high priority
                if ("high".equalsIgnoreCase(product.getPriority())) {
                    product.setStockQuantity(product.getStockQuantity() + product.getRestockQuantity());
                    productRepository.save(product);
                    status = "ok";
                } else {
                    status = "below_threshold";
                }
            } else {
                status = "ok";
            }

            return ProductStatusResponseDTO.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .status(status)
                    .stockQuantity(product.getStockQuantity())
                    .minThreshold(product.getMinThreshold())
                    .build();
        }

        @Transactional
        @Override
        public Product purchaseProduct(int id, PurchaseRequestDTO request) {
            Product product = getProductById(id);

            if (product.getStockQuantity() < request.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for purchase.");
            }

            product.setStockQuantity(product.getStockQuantity() - request.getQuantity());

            // Auto-restock if high priority and below threshold after purchase
            if ("high".equalsIgnoreCase(product.getPriority()) &&
                    product.getStockQuantity() < product.getMinThreshold()) {
                product.setStockQuantity(product.getStockQuantity() + product.getRestockQuantity());
            }

            return productRepository.save(product);
        }

        @Transactional
        @Override
        public Product manualRestock(int id) {
            Product product = getProductById(id);

            if (!"low".equalsIgnoreCase(product.getPriority())) {
                throw new IllegalArgumentException("Manual restock allowed only for low priority products.");
            }

            product.setStockQuantity(product.getStockQuantity() + product.getRestockQuantity());
            return productRepository.save(product);
        }

        private Product getProductById(int id) {
            return productRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Product with ID " + id + " not found."));
        }
    }
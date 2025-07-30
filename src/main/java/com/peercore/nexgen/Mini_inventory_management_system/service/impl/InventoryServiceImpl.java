package com.peercore.nexgen.Mini_inventory_management_system.service.impl;

import com.peercore.nexgen.Mini_inventory_management_system.entity.Product;
import com.peercore.nexgen.Mini_inventory_management_system.repository.ProductRepository;
import com.peercore.nexgen.Mini_inventory_management_system.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;

    public InventoryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product addProduct(String productName, int stockQuantity, int minQuantity, int restockAmount) {
        return productRepository.findByName(productName)
                .map(existing -> {
                    existing.setStockQuantity(existing.getStockQuantity() + stockQuantity);
                    existing.setMinQuantity(minQuantity);
                    existing.setRestockAmount(restockAmount);
                    return productRepository.save(existing);
                })
                .orElseGet(() -> {
                    Product product = Product.builder()
                            .name(productName)
                            .stockQuantity(stockQuantity)
                            .minQuantity(minQuantity)
                            .restockAmount(restockAmount)
                            .build();
                    return productRepository.save(product);
                });
    }

    @Override
    @Transactional
    public Product purchaseProduct(int productId, int purchaseQuantity) {
        Product product = findProductById(productId);

        if (purchaseQuantity > product.getStockQuantity()) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }

        product.setStockQuantity(product.getStockQuantity() - purchaseQuantity);

        // Auto-restock if below minQuantity
        if (product.getStockQuantity() < product.getMinQuantity()) {
            product.setStockQuantity(product.getStockQuantity() + product.getRestockAmount());
        }

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public List<Product> checkInventoryAndRestock(int productId) {
        Product product = findProductById(productId);

        List<Product> result = new ArrayList<>();

        if (product.getStockQuantity() < product.getMinQuantity()) {
            product.setStockQuantity(product.getStockQuantity() + product.getRestockAmount());
            productRepository.save(product);
            result.add(product);
        }

        return result;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteProduct(int id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }
}
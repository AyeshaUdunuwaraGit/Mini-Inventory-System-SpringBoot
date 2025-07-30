package com.peercore.nexgen.Mini_inventory_management_system.repository;

import com.peercore.nexgen.Mini_inventory_management_system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
}

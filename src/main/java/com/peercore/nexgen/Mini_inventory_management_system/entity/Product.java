package com.peercore.nexgen.Mini_inventory_management_system.entity;


import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String priority; // "high" or "low"
    private int stockQuantity;
    private int minThreshold;
    private int restockQuantity;
    private String category; // "high_volume" or "low_volume"
}

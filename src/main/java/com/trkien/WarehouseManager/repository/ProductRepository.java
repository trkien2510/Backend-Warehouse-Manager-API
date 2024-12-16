package com.trkien.WarehouseManager.repository;

import com.trkien.WarehouseManager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByNameAndUserId(String name, String id);
    boolean existsByNameAndUserId(String name, String id);
    List<Product> findByUserId(String id);
}

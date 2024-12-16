package com.trkien.WarehouseManager.repository;

import com.trkien.WarehouseManager.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByNameAndUserId(String name, String id);
    boolean existsByName(String name);
    List<Supplier> findByUserId(String id);

    boolean existsByNameAndUserId(String name, String id);
}

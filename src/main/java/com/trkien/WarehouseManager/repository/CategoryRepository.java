package com.trkien.WarehouseManager.repository;

import com.trkien.WarehouseManager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndUserId(String name, String loggedInUsername);
    boolean existsByName(String name);
    List<Category> findByUserId(String id);
    boolean existsByNameAndUserId(String name, String id);
}

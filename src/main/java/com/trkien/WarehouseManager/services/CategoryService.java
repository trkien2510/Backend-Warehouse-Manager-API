package com.trkien.WarehouseManager.services;

import com.trkien.WarehouseManager.dto.request.CategoryRequest;
import com.trkien.WarehouseManager.dto.response.CategoryResponse;
import com.trkien.WarehouseManager.entity.Category;
import com.trkien.WarehouseManager.entity.User;
import com.trkien.WarehouseManager.exception.AppException;
import com.trkien.WarehouseManager.exception.ErrorCode;
import com.trkien.WarehouseManager.mapper.CategoryMapper;
import com.trkien.WarehouseManager.repository.CategoryRepository;
import com.trkien.WarehouseManager.repository.ProductRepository;
import com.trkien.WarehouseManager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryResponse> getAllCategory(String loggedInUsername) {
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        return categoryRepository.findByUserId(user.getId()).stream().map(categoryMapper::toCategoryResponse).toList();
    }

    public CategoryResponse createCategory(CategoryRequest request, String loggedInUsername) {
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        if (categoryRepository.existsByNameAndUserId(request.getName(), user.getId())){
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        Category category = categoryMapper.toCategory(request, user);
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public boolean deleteCategory(CategoryRequest request, String loggedInUsername) {
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        Category category = categoryRepository.findByNameAndUserId(request.getName(), user.getId())
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        boolean hasProducts = productRepository.existsByCategory(category);
        if (hasProducts) {
            return false;
        }

        try {
            categoryRepository.delete(category);
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.CANNOT_DELETE);
        }
    }
}

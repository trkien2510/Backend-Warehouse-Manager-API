package com.trkien.WarehouseManager.services;

import com.trkien.WarehouseManager.dto.request.*;
import com.trkien.WarehouseManager.dto.response.*;
import com.trkien.WarehouseManager.entity.*;
import com.trkien.WarehouseManager.exception.AppException;
import com.trkien.WarehouseManager.exception.ErrorCode;
import com.trkien.WarehouseManager.repository.*;
import com.trkien.WarehouseManager.mapper.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    public ProductService(UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository, SupplierRepository supplierRepository, ProductMapper productMapper) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.productMapper = productMapper;
    }

    public List<ProductResponse> getAllProducts(String loggedInUsername) {
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        return productRepository.findByUserId(user.getId()).stream().map(productMapper::toProductResponse).toList();
    }

    public ProductResponse createProduct(ProductCreateRequest request, String loggedInUsername) {
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        if (productRepository.existsByNameAndUserId(request.getName(), user.getId())){
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        Category category = categoryRepository.findById(request.getCategory())
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        Supplier supplier = supplierRepository.findById(request.getSupplier())
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        Product product = productMapper.toProduct(request, category, supplier, user);
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    public ProductResponse updateProduct(ProductUpdateRequest request, String loggedInUsername){
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        Product product = productRepository.findByNameAndUserId(request.getName(), user.getId())
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        Category category = categoryRepository.findById(request.getCategory())
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        Supplier supplier = supplierRepository.findById(request.getSupplier())
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        product.setPrice(request.getPrice());
        product.setNote(request.getNote());
        product.setQuantity(request.getQuantity());
        product.setCategory(category);
        product.setSupplier(supplier);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    public boolean deleteProduct(ProductDeleteRequest request, String loggedInUsername){
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        Product product = productRepository.findByNameAndUserId(request.getName(), user.getId())
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        try {
            productRepository.delete(product);
            return true;
        } catch (Exception e){
            throw new AppException(ErrorCode.CANNOT_DELETE);
        }
    }
}
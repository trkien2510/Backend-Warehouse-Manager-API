package com.trkien.WarehouseManager.controller;

import com.trkien.WarehouseManager.dto.ApiResponse;
import com.trkien.WarehouseManager.dto.request.ProductCreateRequest;
import com.trkien.WarehouseManager.dto.request.ProductDeleteRequest;
import com.trkien.WarehouseManager.dto.request.ProductUpdateRequest;
import com.trkien.WarehouseManager.dto.response.ProductResponse;
import com.trkien.WarehouseManager.exception.AppException;
import com.trkien.WarehouseManager.exception.ErrorCode;
import com.trkien.WarehouseManager.services.ProductService;
import com.trkien.WarehouseManager.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final JwtUtils jwtUtils;

    public ProductController(ProductService productService, JwtUtils jwtUtils) {
        this.productService = productService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<List<ProductResponse>> response = new ApiResponse<>();
        response.setData(productService.getAllProducts(loggedInUsername));
        return response;
    }

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProductCreateRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<ProductResponse> response = new ApiResponse<>();
        response.setData(productService.createProduct(request, loggedInUsername));
        return response;
    }

    @PutMapping
    public ApiResponse<ProductResponse> updateProduct(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProductUpdateRequest request){
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<ProductResponse> response = new ApiResponse<>();
        response.setData(productService.updateProduct(request, loggedInUsername));
        return response;
    }

    @DeleteMapping
    public ApiResponse deleteProduct(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProductDeleteRequest request){
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<ProductResponse> response = new ApiResponse<>();
        boolean isDeleted = productService.deleteProduct(request, loggedInUsername);
        if (isDeleted){
            response.setMessage("Successful");
        } else {
            throw new AppException(ErrorCode.CANNOT_DELETE);
        }
        return response;
    }
}
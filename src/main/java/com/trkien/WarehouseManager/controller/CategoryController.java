package com.trkien.WarehouseManager.controller;

import com.trkien.WarehouseManager.dto.ApiResponse;
import com.trkien.WarehouseManager.dto.request.CategoryRequest;
import com.trkien.WarehouseManager.dto.response.CategoryResponse;
import com.trkien.WarehouseManager.exception.AppException;
import com.trkien.WarehouseManager.exception.ErrorCode;
import com.trkien.WarehouseManager.services.CategoryService;
import com.trkien.WarehouseManager.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final JwtUtils jwtUtils;

    public CategoryController(CategoryService categoryService, JwtUtils jwtUtils) {
        this.categoryService = categoryService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    ApiResponse<List<CategoryResponse>> getAllCategory(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<List<CategoryResponse>> response = new ApiResponse<>();
        response.setData(categoryService.getAllCategory(loggedInUsername));
        return response;
    }

    @PostMapping
    ApiResponse<CategoryResponse> createSupplier(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CategoryRequest request){
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<CategoryResponse> response = new ApiResponse<>();
        response.setData(categoryService.createCategory(request, loggedInUsername));
        return response;
    }

    @DeleteMapping
    ApiResponse deleteCategory(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CategoryRequest request){
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        boolean isDeleted = categoryService.deleteCategory(request, loggedInUsername);
        ApiResponse response = new ApiResponse<>();
        if (isDeleted){
            response.setMessage("Successful");
        } else {
            throw new AppException(ErrorCode.CANNOT_DELETE);
        }
        return response;
    }
}

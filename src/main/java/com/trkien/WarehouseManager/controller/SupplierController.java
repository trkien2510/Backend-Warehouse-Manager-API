package com.trkien.WarehouseManager.controller;

import com.trkien.WarehouseManager.dto.ApiResponse;
import com.trkien.WarehouseManager.dto.request.CategoryRequest;
import com.trkien.WarehouseManager.dto.request.SupplierCreateRequest;
import com.trkien.WarehouseManager.dto.request.SupplierDeleteRequest;
import com.trkien.WarehouseManager.dto.response.ProductResponse;
import com.trkien.WarehouseManager.dto.response.SupplierResponse;
import com.trkien.WarehouseManager.dto.response.UserResponse;
import com.trkien.WarehouseManager.exception.AppException;
import com.trkien.WarehouseManager.exception.ErrorCode;
import com.trkien.WarehouseManager.services.SupplierService;
import com.trkien.WarehouseManager.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    private final SupplierService supplierService;
    private final JwtUtils jwtUtils;

    public SupplierController(SupplierService supplierService, JwtUtils jwtUtils) {
        this.supplierService = supplierService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    ApiResponse<List<SupplierResponse>> getAllSupplier(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<List<SupplierResponse>> response = new ApiResponse<>();
        response.setData(supplierService.getAllSupplier(loggedInUsername));
        return response;
    }

    @PostMapping
    ApiResponse<SupplierResponse> createSupplier(@RequestHeader("Authorization") String authorizationHeader, @RequestBody SupplierCreateRequest request){
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<SupplierResponse> response = new ApiResponse<>();
        response.setData(supplierService.createSupplier(request, loggedInUsername));
        return response;
    }

    @DeleteMapping
    ApiResponse deleteSupplier(@RequestHeader("Authorization") String authorizationHeader, @RequestBody SupplierDeleteRequest request){
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        boolean isDeleted = supplierService.deleteSupplier(request, loggedInUsername);
        ApiResponse response = new ApiResponse<>();
        if (isDeleted){
            response.setMessage("Successful");
        } else {
            throw new AppException(ErrorCode.CANNOT_DELETE);
        }
        return response;
    }
}

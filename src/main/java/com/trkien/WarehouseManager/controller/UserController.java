package com.trkien.WarehouseManager.controller;

import com.trkien.WarehouseManager.dto.ApiResponse;
import com.trkien.WarehouseManager.dto.request.UserCreateRequest;
import com.trkien.WarehouseManager.dto.request.UserUpdateRequest;
import com.trkien.WarehouseManager.dto.response.UserResponse;
import com.trkien.WarehouseManager.services.UserService;
import com.trkien.WarehouseManager.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public UserController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setData(userService.getAllUsers(loggedInUsername));
        return response;
    }

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.register(request));
        return apiResponse;
    }

    @PutMapping
    ApiResponse<UserResponse> updateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UserUpdateRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.update(request, loggedInUsername));
        return apiResponse;
    }
}

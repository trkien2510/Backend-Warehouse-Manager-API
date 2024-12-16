package com.trkien.WarehouseManager.mapper;

import com.trkien.WarehouseManager.dto.request.UserCreateRequest;
import com.trkien.WarehouseManager.dto.response.UserResponse;
import com.trkien.WarehouseManager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserCreateRequest request){
        if (request == null){
            return null;
        } else {
            return new User(request.getUsername(), request.getPassword(), request.getEmail());
        }
    }

    public UserResponse toUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId((user.getId()));
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }
}

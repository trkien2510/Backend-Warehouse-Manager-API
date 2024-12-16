package com.trkien.WarehouseManager.services;

import com.trkien.WarehouseManager.dto.request.UserCreateRequest;
import com.trkien.WarehouseManager.dto.request.UserUpdateRequest;
import com.trkien.WarehouseManager.dto.response.UserResponse;
import com.trkien.WarehouseManager.entity.User;
import com.trkien.WarehouseManager.exception.AppException;
import com.trkien.WarehouseManager.exception.ErrorCode;
import com.trkien.WarehouseManager.mapper.UserMapper;
import com.trkien.WarehouseManager.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse register(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse update(UserUpdateRequest request, String loggedInUsername) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);

        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setEmail(request.getNewEmail());
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}

package com.trkien.WarehouseManager.services;

import com.trkien.WarehouseManager.dto.request.SupplierCreateRequest;
import com.trkien.WarehouseManager.dto.request.SupplierDeleteRequest;
import com.trkien.WarehouseManager.dto.response.SupplierResponse;
import com.trkien.WarehouseManager.entity.Supplier;
import com.trkien.WarehouseManager.entity.User;
import com.trkien.WarehouseManager.exception.AppException;
import com.trkien.WarehouseManager.exception.ErrorCode;
import com.trkien.WarehouseManager.mapper.SupplierMapper;
import com.trkien.WarehouseManager.repository.SupplierRepository;
import com.trkien.WarehouseManager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierService(UserRepository userRepository, SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    public List<SupplierResponse> getAllSupplier(String loggedInUsername) {
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        return supplierRepository.findByUserId(user.getId()).stream().map(supplierMapper::toSupplierResponse).toList();
    }

    public SupplierResponse createSupplier(SupplierCreateRequest request, String loggedInUsername) {
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        if (supplierRepository.existsByNameAndUserId(request.getName(), user.getId())){
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        Supplier supplier = supplierMapper.toSupplier(request, user);
        supplierRepository.save(supplier);
        return supplierMapper.toSupplierResponse(supplier);
    }

    public boolean deleteSupplier(SupplierDeleteRequest request, String loggedInUsername) {
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        Supplier supplier = supplierRepository.findByNameAndUserId(request.getName(), user.getId())
                .orElseThrow(()->new AppException(ErrorCode.INVALID_KEY));

        try {
            supplierRepository.delete(supplier);
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.CANNOT_DELETE);
        }
    }
}

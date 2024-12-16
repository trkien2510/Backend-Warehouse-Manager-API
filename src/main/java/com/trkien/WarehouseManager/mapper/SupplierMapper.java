package com.trkien.WarehouseManager.mapper;

import com.trkien.WarehouseManager.dto.request.SupplierCreateRequest;
import com.trkien.WarehouseManager.dto.response.SupplierResponse;
import com.trkien.WarehouseManager.entity.Supplier;
import com.trkien.WarehouseManager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    public Supplier toSupplier(SupplierCreateRequest request, User user){
        if (request == null){
            return null;
        } else {
            return new Supplier(request.getName(), request.getContactInfo(), user);
        }
    }

    public SupplierResponse toSupplierResponse(Supplier supplier){
        SupplierResponse response = new SupplierResponse();
        response.setId(supplier.getId());
        response.setName(supplier.getName());
        response.setContactInfo(supplier.getContactInfo());
        return response;
    }
}

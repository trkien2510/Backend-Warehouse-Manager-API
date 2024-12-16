package com.trkien.WarehouseManager.mapper;

import com.trkien.WarehouseManager.dto.request.ProductCreateRequest;
import com.trkien.WarehouseManager.dto.response.ProductResponse;
import com.trkien.WarehouseManager.entity.Category;
import com.trkien.WarehouseManager.entity.Product;
import com.trkien.WarehouseManager.entity.Supplier;
import com.trkien.WarehouseManager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toProduct(ProductCreateRequest request, Category category, Supplier supplier, User user) {
        if (request == null) {
            return null;
        } else {
            return new Product(
                    request.getName(),
                    request.getPrice(),
                    request.getNote(),
                    request.getQuantity(),
                    category,
                    supplier,
                    user
            );
        }
    }

    public ProductResponse toProductResponse(Product product){
        ProductResponse response = new ProductResponse();
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setNote(product.getNote());
        response.setQuantity(product.getQuantity());
        response.setCategory(product.getCategory());
        response.setSupplier(product.getSupplier());
        return response;
    }
}

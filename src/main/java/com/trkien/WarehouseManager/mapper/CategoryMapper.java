package com.trkien.WarehouseManager.mapper;

import com.trkien.WarehouseManager.dto.request.CategoryRequest;
import com.trkien.WarehouseManager.dto.response.CategoryResponse;
import com.trkien.WarehouseManager.entity.Category;
import com.trkien.WarehouseManager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toCategory(CategoryRequest request, User user){
        if (request == null){
            return null;
        } else {
            return new Category(request.getName(), user);
        }
    }

    public CategoryResponse toCategoryResponse(Category category){
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        return response;
    }
}

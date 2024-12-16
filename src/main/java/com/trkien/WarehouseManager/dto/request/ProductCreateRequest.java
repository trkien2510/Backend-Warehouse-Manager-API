package com.trkien.WarehouseManager.dto.request;

import com.trkien.WarehouseManager.entity.Category;
import com.trkien.WarehouseManager.entity.Supplier;

public class ProductCreateRequest {
    private String name;
    private Double price;
    private String note;
    private int quantity;
    private Long category;
    private Long supplier;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }
}

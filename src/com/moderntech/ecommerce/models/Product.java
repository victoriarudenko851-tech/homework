package com.moderntech.ecommerce.models;

import com.moderntech.ecommerce.enums.ProductCategory;
import java.util.UUID;

public record Product(
        String id,
        String name,
        double price,
        ProductCategory category,
        int stock
) {
    public Product {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        if (stock < 0) throw new IllegalArgumentException("Stock cannot be negative");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Product name required");
    }

    public Product(String name, double price, ProductCategory category, int stock) {
        this(UUID.randomUUID().toString(), name, price, category, stock);
    }

    public boolean isInStock() {
        return stock > 0;
    }

    public double getPriceWithVAT() {
        return price * 1.20;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %.2f руб. (с НДС: %.2f) | Остаток: %d шт.",
                category.getRussianName(), name, price, getPriceWithVAT(), stock);
    }
}
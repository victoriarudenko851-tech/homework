package com.moderntech.ecommerce.models;

public record CartItem(
        Product product,
        int quantity
) {
    public CartItem {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
    }

    public double getSubtotal() {
        return product.price() * quantity;
    }

    public double getSubtotalWithVAT() {
        return getSubtotal() * 1.20;
    }

    @Override
    public String toString() {
        return String.format("  %s x%d = %.2f руб. (с НДС: %.2f руб.)",
                product.name(), quantity, getSubtotal(), getSubtotalWithVAT());
    }
}
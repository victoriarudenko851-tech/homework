package com.moderntech.ecommerce.models;

public record OrderItem(
        Product product,
        int quantity,
        double priceAtOrderTime
) {
    public OrderItem(Product product, int quantity) {
        this(product, quantity, product.price());
    }

    public double getSubtotal() {
        return priceAtOrderTime * quantity;
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
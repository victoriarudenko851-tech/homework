package com.moderntech.ecommerce.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    private final Map<String, CartItem> items;
    private final Customer customer;

    public ShoppingCart(Customer customer) {
        this.customer = customer;
        this.items = new HashMap<>();
    }

    public void addItem(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (product.stock() < quantity) {
            throw new IllegalStateException("Not enough stock. Available: " + product.stock());
        }

        String productId = product.id();
        if (items.containsKey(productId)) {
            CartItem existing = items.get(productId);
            int newQuantity = existing.quantity() + quantity;
            items.put(productId, new CartItem(product, newQuantity));
        } else {
            items.put(productId, new CartItem(product, quantity));
        }
    }

    public void removeItem(String productId, int quantity) {
        if (!items.containsKey(productId)) {
            throw new IllegalArgumentException("Product not in cart");
        }

        CartItem existing = items.get(productId);
        int newQuantity = existing.quantity() - quantity;

        if (newQuantity <= 0) {
            items.remove(productId);
        } else {
            items.put(productId, new CartItem(existing.product(), newQuantity));
        }
    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public double getTotalWithoutVAT() {
        return items.values().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public double getTotalWithVAT() {
        return items.values().stream()
                .mapToDouble(CartItem::getSubtotalWithVAT)
                .sum();
    }

    public double getVATAmount() {
        return getTotalWithVAT() - getTotalWithoutVAT();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void displayCart() {
        if (isEmpty()) {
            System.out.println("Корзина пуста.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                        КОРЗИНА ПОКУПАТЕЛЯ                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("Покупатель: " + customer.getName());
        System.out.println("\nТовары в корзине:");
        System.out.println("────────────────────────────────────────────────────────────────");
        for (CartItem item : getItems()) {
            System.out.println(item);
        }
        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.printf("Сумма без НДС: %.2f руб.%n", getTotalWithoutVAT());
        System.out.printf("НДС (20%%): %.2f руб.%n", getVATAmount());
        System.out.printf("ИТОГО К ОПЛАТЕ: %.2f руб.%n", getTotalWithVAT());
        System.out.println("════════════════════════════════════════════════════════════════\n");
    }
}
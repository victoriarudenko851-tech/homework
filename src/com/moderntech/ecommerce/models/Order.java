package com.moderntech.ecommerce.models;

import com.moderntech.ecommerce.enums.OrderStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String orderId;
    private final Customer customer;
    private final List<OrderItem> items;
    private final LocalDateTime orderDate;
    private OrderStatus status;
    private double totalAmountWithoutVAT;
    private double totalAmountWithVAT;

    public Order(Customer customer, List<CartItem> cartItems) {
        this.orderId = UUID.randomUUID().toString();
        this.customer = customer;
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;

        for (CartItem cartItem : cartItems) {
            this.items.add(new OrderItem(cartItem.product(), cartItem.quantity()));
        }

        calculateTotals();
    }

    private void calculateTotals() {
        this.totalAmountWithoutVAT = items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
        this.totalAmountWithVAT = items.stream()
                .mapToDouble(OrderItem::getSubtotalWithVAT)
                .sum();
    }

    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public List<OrderItem> getItems() { return new ArrayList<>(items); }
    public LocalDateTime getOrderDate() { return orderDate; }
    public OrderStatus getStatus() { return status; }
    public double getTotalAmountWithoutVAT() { return totalAmountWithoutVAT; }
    public double getTotalAmountWithVAT() { return totalAmountWithVAT; }
    public double getVATAmount() { return totalAmountWithVAT - totalAmountWithoutVAT; }

    public void setStatus(OrderStatus newStatus) {
        OrderStatus oldStatus = this.status;
        this.status = newStatus;
        System.out.println("   Заказ #" + getShortId() + ": " + oldStatus + " → " + newStatus);
    }

    public String getShortId() {
        return orderId.substring(0, 8);
    }

    public void displayOrderDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      ДЕТАЛИ ЗАКАЗА                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("Номер заказа: " + getShortId() + "...");
        System.out.println("Дата: " + orderDate.format(formatter));
        System.out.println("Статус: " + status);
        System.out.println("Покупатель: " + customer.getName());
        System.out.println("\nСостав заказа:");
        System.out.println("────────────────────────────────────────────────────────────────");
        for (OrderItem item : items) {
            System.out.printf("  %s x%d = %.2f руб.%n",
                    item.product().name(), item.quantity(), item.getSubtotal());
        }
        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.printf("Сумма без НДС: %.2f руб.%n", totalAmountWithoutVAT);
        System.out.printf("НДС (20%%): %.2f руб.%n", getVATAmount());
        System.out.printf("ИТОГО К ОПЛАТЕ: %.2f руб.%n", totalAmountWithVAT);
        System.out.println("════════════════════════════════════════════════════════════════\n");
    }
}
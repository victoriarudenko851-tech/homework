package com.moderntech.ecommerce.payment;

import com.moderntech.ecommerce.models.Order;

public final class CashOnDelivery implements PaymentMethod {
    private final String deliveryAddress;
    private final String recipientPhone;
    private PaymentStatus status;

    public CashOnDelivery(String deliveryAddress, String recipientPhone) {
        this.deliveryAddress = deliveryAddress;
        this.recipientPhone = recipientPhone;
        this.status = PaymentStatus.PENDING;
    }

    @Override
    public String getProviderName() {
        return "Наложенный платеж";
    }

    @Override
    public boolean processPayment(Order order, double amount) {
        System.out.println("\n   НАЛОЖЕННЫЙ ПЛАТЕЖ");
        System.out.println("  ┌─────────────────────────────────────────");
        System.out.println("  │ Адрес доставки: " + deliveryAddress);
        System.out.println("  │ Телефон получателя: " + recipientPhone);
        System.out.println("  │ Сумма к оплате при получении: " + String.format("%.2f", amount) + " руб.");
        System.out.println("  └─────────────────────────────────────────");
        System.out.println("   Заказ оформлен. Оплата при получении.");
        this.status = PaymentStatus.PROCESSING;
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return String.format("Наложенный платеж на адрес: %s", deliveryAddress);
    }
}
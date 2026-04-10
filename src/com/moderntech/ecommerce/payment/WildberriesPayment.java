package com.moderntech.ecommerce.payment;

import com.moderntech.ecommerce.models.Order;

public class WildberriesPayment implements Payment {
    private final PaymentMethod paymentMethod;

    public WildberriesPayment(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean pay(Order order) {
        double amount = order.getTotalAmountWithVAT();

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 WILDBERRIES - ОПЛАТА ЗАКАЗА                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("Маркетплейс: Wildberries");
        System.out.println("Способ оплаты: " + paymentMethod.getProviderName());

        boolean result = paymentMethod.processPayment(order, amount);

        if (result) {
            System.out.println("\n   ОПЛАТА ЧЕРЕЗ WILDBERRIES УСПЕШНО ЗАВЕРШЕНА");
        }
        return result;
    }

    @Override
    public String getProviderName() {
        return "Wildberries";
    }
}
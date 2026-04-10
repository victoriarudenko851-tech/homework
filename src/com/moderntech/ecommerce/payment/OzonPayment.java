package com.moderntech.ecommerce.payment;

import com.moderntech.ecommerce.models.Order;

public class OzonPayment implements Payment {
    private final PaymentMethod paymentMethod;

    public OzonPayment(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean pay(Order order) {
        double amount = order.getTotalAmountWithVAT();

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     OZON - ОПЛАТА ЗАКАЗА                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println("Маркетплейс: OZON");
        System.out.println("Способ оплаты: " + paymentMethod.getProviderName());

        boolean result = paymentMethod.processPayment(order, amount);

        if (result) {
            System.out.println("\n   ОПЛАТА ЧЕРЕЗ OZON УСПЕШНО ЗАВЕРШЕНА");
        }
        return result;
    }

    @Override
    public String getProviderName() {
        return "Ozon";
    }
}
package com.moderntech.ecommerce.payment;

import com.moderntech.ecommerce.models.Order;

public final class DigitalWalletPayment implements PaymentMethod {
    private final String walletId;
    private final String walletEmail;
    private PaymentStatus status;

    public DigitalWalletPayment(String walletId, String walletEmail) {
        this.walletId = walletId;
        this.walletEmail = walletEmail;
        this.status = PaymentStatus.PENDING;
    }

    @Override
    public String getProviderName() {
        return "Электронный кошелек";
    }

    @Override
    public boolean processPayment(Order order, double amount) {
        System.out.println("\n   ПЛАТЕЖ ЭЛЕКТРОННЫМ КОШЕЛЬКОМ");
        System.out.println("  ┌─────────────────────────────────────────");
        System.out.println("  │ Кошелек ID: " + walletId);
        System.out.println("  │ Email: " + walletEmail);
        System.out.println("  │ Сумма: " + String.format("%.2f", amount) + " руб.");
        System.out.println("  └─────────────────────────────────────────");
        System.out.println("   Средства списаны с электронного кошелька!");
        this.status = PaymentStatus.SUCCESS;
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return String.format("Электронный кошелек %s (%s)", walletId, walletEmail);
    }
}
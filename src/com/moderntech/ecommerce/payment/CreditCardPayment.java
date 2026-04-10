package com.moderntech.ecommerce.payment;

import com.moderntech.ecommerce.models.Order;

public final class CreditCardPayment implements PaymentMethod {
    private final String cardNumber;
    private final String cardHolderName;
    private final String expiryDate;
    private PaymentStatus status;

    public CreditCardPayment(String cardNumber, String cardHolderName, String expiryDate) {
        this.cardNumber = maskCardNumber(cardNumber);
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.status = PaymentStatus.PENDING;
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) return "****";
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }

    @Override
    public String getProviderName() {
        return "Банковская карта";
    }

    @Override
    public boolean processPayment(Order order, double amount) {
        System.out.println("\n   ПЛАТЕЖ БАНКОВСКОЙ КАРТОЙ");
        System.out.println("  ┌─────────────────────────────────────────");
        System.out.println("  │ Карта: " + cardNumber);
        System.out.println("  │ Держатель: " + cardHolderName);
        System.out.println("  │ Срок действия: " + expiryDate);
        System.out.println("  │ Сумма: " + String.format("%.2f", amount) + " руб.");
        System.out.println("  └─────────────────────────────────────────");
        System.out.println("   Платеж успешно обработан!");
        this.status = PaymentStatus.SUCCESS;
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return String.format("Карта %s (держатель: %s)", cardNumber, cardHolderName);
    }
}
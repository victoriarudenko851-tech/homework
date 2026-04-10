package com.moderntech.ecommerce.payment;

import com.moderntech.ecommerce.models.Order;

public sealed interface PaymentMethod
        permits CreditCardPayment, DigitalWalletPayment, CashOnDelivery {

    String getProviderName();
    boolean processPayment(Order order, double amount);
    String getPaymentDetails();
}
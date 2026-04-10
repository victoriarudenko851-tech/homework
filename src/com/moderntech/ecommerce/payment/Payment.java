package com.moderntech.ecommerce.payment;

import com.moderntech.ecommerce.models.Order;

public interface Payment {
    boolean pay(Order order);
    String getProviderName();
}
package com.moderntech.ecommerce.payment;

public enum PaymentStatus {
    PENDING("Ожидает оплаты"),
    SUCCESS("Успешно оплачен"),
    FAILED("Ошибка оплаты"),
    REFUNDED("Возврат"),
    PROCESSING("В обработке");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
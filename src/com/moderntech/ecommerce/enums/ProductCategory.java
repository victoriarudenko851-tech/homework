package com.moderntech.ecommerce.enums;

public enum ProductCategory {
    SMARTPHONE("Смартфоны"),
    LAPTOP("Ноутбуки"),
    TABLET("Планшеты"),
    ACCESSORY("Аксессуары"),
    CAMERA("Фотоаппараты");

    private final String russianName;

    ProductCategory(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianName() {
        return russianName;
    }
}
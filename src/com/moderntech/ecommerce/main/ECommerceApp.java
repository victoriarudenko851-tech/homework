package com.moderntech.ecommerce.main;

import com.moderntech.ecommerce.enums.OrderStatus;
import com.moderntech.ecommerce.enums.ProductCategory;
import com.moderntech.ecommerce.models.*;
import com.moderntech.ecommerce.payment.*;

import java.util.*;

public class ECommerceApp {

    private static final Map<String, Product> productCatalog = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         ИНТЕРНЕТ-МАГАЗИН ЭЛЕКТРОНИКИ                         ║");
        System.out.println("║                      Электронная коммерция - Демонстрация                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");

        // 1. СОЗДАНИЕ КАТАЛОГА
        initializeCatalog();
        displayCatalog();

        // 2. СОЗДАНИЕ ПОКУПАТЕЛЯ
        Customer customer = new Customer("Иван Петров", "ivan@example.com");
        System.out.println("\n Создан покупатель: " + customer);

        // 3. РАБОТА С КОРЗИНОЙ
        ShoppingCart cart = new ShoppingCart(customer);

        // Получаем товары из каталога
        Product smartphone = null;
        Product laptop = null;
        Product accessory = null;

        for (Product p : productCatalog.values()) {
            if (p.category() == ProductCategory.SMARTPHONE) smartphone = p;
            if (p.category() == ProductCategory.LAPTOP) laptop = p;
            if (p.category() == ProductCategory.ACCESSORY) accessory = p;
        }

        cart.addItem(smartphone, 2);
        cart.addItem(laptop, 1);
        cart.addItem(accessory, 3);

        cart.displayCart();

        // 4. ОФОРМЛЕНИЕ ЗАКАЗА
        System.out.println("\n ОФОРМЛЕНИЕ ЗАКАЗА...");
        Order order = new Order(customer, cart.getItems());
        order.displayOrderDetails();

        // 5. ИЗМЕНЕНИЕ СТАТУСА
        System.out.println("\n ИЗМЕНЕНИЕ СТАТУСА ЗАКАЗА:");
        order.setStatus(OrderStatus.CONFIRMED);
        order.setStatus(OrderStatus.PROCESSING);
        order.setStatus(OrderStatus.SHIPPED);

        // 6. ПЛАТЕЖНЫЕ СЦЕНАРИИ
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        ПЛАТЕЖНЫЕ СЦЕНАРИИ (3 шт.)                                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════╝");

        // Сценарий 1: Ozon + банковская карта
        System.out.println("\n СЦЕНАРИЙ №1: Ozon + Банковская карта");
        System.out.println("═══════════════════════════════════════════════════════════════════");
        PaymentMethod creditCard = new CreditCardPayment("1234567890123456", "IVAN PETROV", "12/28");
        Payment ozonPayment = new OzonPayment(creditCard);
        ozonPayment.pay(order);

        // Сценарий 2: Wildberries + электронный кошелёк
        System.out.println("\n\n СЦЕНАРИЙ №2: Wildberries + Электронный кошелек");
        System.out.println("═══════════════════════════════════════════════════════════════════");
        PaymentMethod digitalWallet = new DigitalWalletPayment("WALLET789012", "ivan@example.com");
        Payment wbPayment = new WildberriesPayment(digitalWallet);
        wbPayment.pay(order);

        // Сценарий 3: Ozon + наложенный платёж
        System.out.println("\n\n СЦЕНАРИЙ №3: Ozon + Наложенный платеж");
        System.out.println("═══════════════════════════════════════════════════════════════════");
        PaymentMethod cashOnDelivery = new CashOnDelivery("г. Москва, ул. Тверская, д. 1, кв. 10", "+7 (999) 123-45-67");
        Payment ozonCashPayment = new OzonPayment(cashOnDelivery);
        ozonCashPayment.pay(order);

        // 7. ИТОГОВАЯ СВОДКА
        displayFinalSummary(order, customer);

        System.out.println("\n РАБОТА ПРИЛОЖЕНИЯ ЗАВЕРШЕНА УСПЕШНО!");
        System.out.println("════════════════════════════════════════════════════════════════════════════════\n");
    }

    private static void initializeCatalog() {
        Product p1 = new Product("iPhone 15 Pro", 99900.00, ProductCategory.SMARTPHONE, 10);
        Product p2 = new Product("MacBook Pro 14", 199900.00, ProductCategory.LAPTOP, 5);
        Product p3 = new Product("iPad Air", 69900.00, ProductCategory.TABLET, 7);
        Product p4 = new Product("AirPods Pro", 24900.00, ProductCategory.ACCESSORY, 15);
        Product p5 = new Product("Sony A7 IV", 249900.00, ProductCategory.CAMERA, 3);

        productCatalog.put(p1.id(), p1);
        productCatalog.put(p2.id(), p2);
        productCatalog.put(p3.id(), p3);
        productCatalog.put(p4.id(), p4);
        productCatalog.put(p5.id(), p5);
    }

    private static void displayCatalog() {
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           КАТАЛОГ ТОВАРОВ                                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════╝");
        System.out.printf("%-4s %-25s %-15s %-12s %-10s%n", "№", "Название", "Цена", "Категория", "Остаток");
        System.out.println("────────────────────────────────────────────────────────────────────────────────");

        int i = 1;
        for (Product product : productCatalog.values()) {
            System.out.printf("%-4d %-25s %-15.2f %-12s %-10d%n",
                    i++, product.name(), product.price(),
                    product.category().getRussianName(), product.stock());
        }
        System.out.println("────────────────────────────────────────────────────────────────────────────────");
        System.out.println("Примечание: Цены указаны без НДС. НДС 20% добавляется при оплате.\n");
    }

    private static void displayFinalSummary(Order order, Customer customer) {
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         ИТОГОВАЯ СВОДКА ПО ЗАКАЗУ                                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ %-20s │ %-45s │%n", "Параметр", "Значение");
        System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-20s │ %-45s │%n", "Номер заказа", order.getShortId() + "...");
        System.out.printf("│ %-20s │ %-45s │%n", "Покупатель", customer.getName());
        System.out.printf("│ %-20s │ %-45s │%n", "Email", customer.getEmail());
        System.out.printf("│ %-20s │ %-45s │%n", "Статус заказа", order.getStatus());
        System.out.printf("│ %-20s │ %-45s │%n", "Сумма без НДС", String.format("%.2f руб.", order.getTotalAmountWithoutVAT()));
        System.out.printf("│ %-20s │ %-45s │%n", "НДС (20%%)", String.format("%.2f руб.", order.getVATAmount()));
        System.out.printf("│ %-20s │ %-45s │%n", "ИТОГО К ОПЛАТЕ", String.format("%.2f руб.", order.getTotalAmountWithVAT()));
        System.out.printf("│ %-20s │ %-45s │%n", "Дата оформления", order.getOrderDate().toString());
        System.out.println("└────────────────────────────────────────────────────────────────────────────┘");

        System.out.println("\n КОЛИЧЕСТВО ТОВАРОВ В ЗАКАЗЕ:");
        for (OrderItem item : order.getItems()) {
            System.out.printf("  • %s — %d шт. x %.2f руб. = %.2f руб.%n",
                    item.product().name(), item.quantity(),
                    item.priceAtOrderTime(), item.getSubtotal());
        }
    }
}
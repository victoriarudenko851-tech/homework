# Консольное приложение электронной коммерции

**Дисциплина:** Современные технологии программирования

---

## Описание

Консольное Java-приложение интернет-магазина электроники, демонстрирующее объектно-ориентированный дизайн. Приложение реализует каталог товаров, корзину покупок с расчётом НДС, оформление заказов и оплату через различных провайдеров (Ozon, Wildberries) с использованием паттерна «Стратегия».

---

## Команда

```
Группа: Пи24-1в
Команда: Руденко+Пушкарева

Студенты:
1. Руденко Виктория Николаевна — порядковый номер в группе: 14
2. Пушкарева Екатерина Дмитриевна — порядковый номер в группе: 13
```

---

## Из IntelliJ IDEA

1. File → Open → выбрать папку проекта
2. Указать `src` как Sources Root (правой кнопкой → Mark Directory as → Sources Root)
3. Запустить `ECommerceApp.java` (правой кнопкой → Run)

## Из командной строки

```bash
cd src
javac com/moderntech/ecommerce/main/ECommerceApp.java com/moderntech/ecommerce/models/*.java com/moderntech/ecommerce/payment/*.java com/moderntech/ecommerce/enums/*.java
java com.moderntech.ecommerce.main.ECommerceApp
```

---

## Структура проекта

```
com/moderntech/ecommerce/
├── main/
│   └── ECommerceApp.java          — точка входа
├── models/
│   ├── Product.java               (record)
│   ├── Customer.java              (класс)
│   ├── ShoppingCart.java           (класс)
│   ├── Order.java                  (класс)
│   ├── CartItem.java              (record)
│   └── OrderItem.java             (record)
├── payment/
│   ├── Payment.java               (interface — Стратегия)
│   ├── PaymentMethod.java         (sealed interface)
│   ├── CreditCardPayment.java     (final, implements PaymentMethod)
│   ├── DigitalWalletPayment.java  (final, implements PaymentMethod)
│   ├── CashOnDelivery.java        (final, implements PaymentMethod)
│   ├── OzonPayment.java           (implements Payment)
│   ├── WildberriesPayment.java    (implements Payment)
│   └── PaymentStatus.java         (enum)
└── enums/
    ├── OrderStatus.java           (enum)
    └── ProductCategory.java       (enum)
```

---

## Ключевые решения

- **Records** (`Product`, `CartItem`, `OrderItem`) — неизменяемые данные с автоматической генерацией `equals`, `hashCode`, `toString`.
- **Sealed interface** `PaymentMethod` с тремя разрешёнными реализациями — обеспечивает исчерпывающую проверку способов оплаты.
- **Паттерн «Стратегия»** — интерфейс `Payment` позволяет подключать разных провайдеров (`OzonPayment`, `WildberriesPayment`) без изменения клиентского кода.
- **`HashMap`** для каталога товаров (быстрый доступ по ID), **`ArrayList`** для хранения позиций корзины и заказа.
- **НДС 20%** рассчитывается автоматически в `ShoppingCart`.

---

## Скриншот работы

![Каталог и корзина](Скрин 1.png)

![Заказ и оплата](Скрин 2.png)

![Итоговая сводка](Скрин 3.png)

![Платёжные сценарии](Скрин 4.png)

![Результат работы](Скрин 5.png)

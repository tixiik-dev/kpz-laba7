# ADR-001: Рефакторинг системи бронювання ресторану за методологією GRASP

## Статус
Прийнято (Accepted)

## Контекст
У вихідному legacy-коді `LegacyRestaurantProcessor` бізнес-логіка розрахунку чека замовлення ресторану, нарахування сервісного збору (10% від 5 осіб) та дисконту HappyHours були монолітно змішані з CLI-введенням (`Scanner`), виведенням у консоль, збереженням у файл та відправкою SMS. Сутності `TableReservation`, `OrderLine`, `Guest` були пасивними DTO (Anemic Domain Model) з публічними полями.

## Рішення
1. **Information Expert & Creator**: Перенести обчислення підсумкових сум рядка та всього замовлення безпосередньо в `OrderLine` та `TableReservation`. Закрити сеттери полів, а колекцію страв зробити імутабельною. Створення об'єкта агрегату довірити фабричному методу `TableReservation.create(...)`.
2. **Polymorphism & Protected Variations**: Виділити цінові слоти (HappyHours, Standard, Evening) в інтерфейсний контракт `ITimeSlotPricingPolicy` з окремими поліморфними реалізаціями.
3. **Controller & Indirection**: Очистити CLI-шар від логіки. Створити Use-Case Controller `ReservationApplicationService` та проміжні порти-інтерфейси `IReservationRepository` й `INotificationService`.
4. **Pure Fabrication**: Створити технічні класи `FileReservationRepository` та `SmsNotificationService` для ізоляції інфраструктурних деталей.

## Наслідки
### Позитивні
- Coupling Between Objects (CBO) для контролера знижено з 8 до 3.
- LCOM4 усіх доменних класів дорівнює 1 (High Cohesion).
- CLI-шар ізольовано від бізнес-правил (Stable Dependencies Principle).
### Компроміси
- Збільшилася загальна кількість класів та файлів у проєкті (додано DTO, порти та стратегії).

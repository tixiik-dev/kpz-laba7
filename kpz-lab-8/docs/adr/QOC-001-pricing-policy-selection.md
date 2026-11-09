# QOC-001: Вибір механізму розрахунку цінових слотів (HappyHours / Evening / Standard)

- **Question (Питання)**: Хто і як повинен відповідати за розрахунок ціни залежно від часового слоту відвідування ресторану, щоб не порушувати Open/Closed Principle та не перевантажувати агрегат `TableReservation`?
- **Options (Альтернативи)**:
  - **Option A**: Залишити каскад операторів `if-else` всередині монолітного сервісу або всередині методу `TableReservation`.
  - **Option B**: Застосувати патерни **Polymorphism** та **Protected Variations** — виділити інтерфейс `ITimeSlotPricingPolicy` із конкретними реалізаціями `HappyHoursPricingPolicy`, `StandardPricingPolicy` та `EveningPricingPolicy`.
- **Criteria (Критерії оцінки за GRASP)**:
  - *Low Coupling*: Агрегат `TableReservation` не повинен залежати від деталей алгоритмів знижок чи націнок.
  - *Protected Variations*: Поява нових слотів (наприклад, "Night") не повинна вимагати модифікації коду сутності `TableReservation`.
- **Conclusion (Висновок)**: Обрано **Option B**. Агрегат `TableReservation` приймає об'єкт `ITimeSlotPricingPolicy` як параметр методу обчислення, виступаючи Information Expert для базових сум, але делегуючи розрахунок коефіцієнта поліморфній стратегії.
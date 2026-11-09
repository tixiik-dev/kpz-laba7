package variant6_restaurantbooking.ui;

import variant6_restaurantbooking.application.dto.CreateReservationRequest;
import variant6_restaurantbooking.application.service.ReservationApplicationService;
import variant6_restaurantbooking.domain.model.TableReservation;
import variant6_restaurantbooking.infrastructure.FileReservationRepository;
import variant6_restaurantbooking.infrastructure.SmsNotificationService;

import java.util.Scanner;

public class ConsoleUi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Бронювання столика в ресторані ===");
        System.out.print("Введіть ім'я гостя: ");
        String name = scanner.nextLine();
        System.out.print("Введіть час відвідування (HappyHours/Standard/Evening): ");
        String timeSlot = scanner.nextLine();

        // Форматування даних у DTO (без бізнес-логіки)
        CreateReservationRequest request = new CreateReservationRequest();
        request.guestName = name;
        request.guestPhone = "+380631234567";
        request.guestsCount = 6;
        request.timeSlot = timeSlot;

        // Координація залежностей
        ReservationApplicationService service = new ReservationApplicationService(
                new FileReservationRepository(),
                new SmsNotificationService()
        );

        TableReservation res = service.processReservation(request);

        // Тільки виведення результату
        System.out.println("\nЧек резерву #" + res.getReservationId());
        System.out.println("Гість: " + res.getGuest().getName() + " (" + res.getGuestsCount() + " осіб)");
        System.out.println("Страви: " + res.getFoodSubtotal() + " грн");
        System.out.println("Обслуговування: " + res.getServiceCharge() + " грн");
        System.out.println("Всього до сплати: " + res.getTotalBill() + " грн");
    }
}
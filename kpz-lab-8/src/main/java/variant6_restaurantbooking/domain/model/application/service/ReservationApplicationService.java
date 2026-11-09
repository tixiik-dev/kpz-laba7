package variant6_restaurantbooking.application.service;

import variant6_restaurantbooking.application.dto.CreateReservationRequest;
import variant6_restaurantbooking.application.port.INotificationService;
import variant6_restaurantbooking.application.port.IReservationRepository;
import variant6_restaurantbooking.domain.model.Guest;
import variant6_restaurantbooking.domain.model.MenuItem;
import variant6_restaurantbooking.domain.model.TableReservation;

public class ReservationApplicationService {
    private final IReservationRepository repository;
    private final INotificationService notificationService;

    public ReservationApplicationService(IReservationRepository repository, INotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    // Use-Case Controller: приймає DTO, керує доменним сценарієм
    public TableReservation processReservation(CreateReservationRequest request) {
        Guest guest = new Guest(3, request.guestName, request.guestPhone);
        TableReservation reservation = TableReservation.create(404, guest, request.guestsCount, request.timeSlot);

        // Демо-наповнення меню
        reservation.addOrderLine(new MenuItem("Стейк Рібай", 650.0, "Main"), 4);
        reservation.addOrderLine(new MenuItem("Вино Червоне", 400.0, "Drink"), 2);

        // Виклик бізнес-обчислень в агрегаті
        reservation.calculateBaseBill();

        // Збереження та сповіщення через абстракції (Indirection)
        repository.save(reservation);
        notificationService.sendNotification(guest.getPhone(), "Деталі резерву #" + reservation.getReservationId() + " підтверджено.");

        return reservation;
    }
}
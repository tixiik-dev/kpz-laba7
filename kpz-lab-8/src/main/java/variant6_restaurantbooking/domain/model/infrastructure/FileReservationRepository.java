package variant6_restaurantbooking.infrastructure;

import variant6_restaurantbooking.application.port.IReservationRepository;
import variant6_restaurantbooking.domain.model.TableReservation;

public class FileReservationRepository implements IReservationRepository {
    @Override
    public void save(TableReservation reservation) {
        System.out.println("[БД Ресторану] Бронювання #" + reservation.getReservationId() + " збережено в reservations.json.");
    }
}
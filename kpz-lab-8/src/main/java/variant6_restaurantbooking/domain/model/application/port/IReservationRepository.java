package variant6_restaurantbooking.application.port;

import variant6_restaurantbooking.domain.model.TableReservation;

public interface IReservationRepository {
    void save(TableReservation reservation);
}
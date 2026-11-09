package variant6_restaurantbooking.application.port;

public interface INotificationService {
    void sendNotification(String phone, String message);
}
package variant6_restaurantbooking.infrastructure;

import variant6_restaurantbooking.application.port.INotificationService;

public class SmsNotificationService implements INotificationService {
    @Override
    public void sendNotification(String phone, String message) {
        System.out.println("[SMS] На номер " + phone + " надіслано: " + message);
    }
}
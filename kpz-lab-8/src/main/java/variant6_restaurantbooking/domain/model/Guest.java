package variant6_restaurantbooking.domain.model;

public class Guest {
    private final int id;
    private final String name;
    private final String phone;

    public Guest(int id, String name, String phone) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ім'я гостя не може бути порожнім.");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Номер телефону не може бути порожнім.");
        }
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
}
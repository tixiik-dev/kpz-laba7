package variant6_restaurantbooking.domain.model;

public class MenuItem {
    private final String name;
    private final double price;
    private final String courseType; // "Main", "Drink", "Dessert"

    public MenuItem(String name, double price, String courseType) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна страви не може бути від'ємною.");
        }
        this.name = name;
        this.price = price;
        this.courseType = courseType;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCourseType() { return courseType; }
}
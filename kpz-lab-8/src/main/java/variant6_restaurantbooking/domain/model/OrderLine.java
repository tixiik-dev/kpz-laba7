package variant6_restaurantbooking.domain.model;

import java.util.Objects;

public class OrderLine {
    private final MenuItem item;
    private final int count;

    public OrderLine(MenuItem item, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Кількість порцій має бути більшою за 0.");
        }
        this.item = Objects.requireNonNull(item, "Страва не може бути null.");
        this.count = count;
    }


    public double getLineSubtotal() {
        return item.getPrice() * count;
    }

    public MenuItem getItem() { return item; }
    public int getCount() { return count; }
}
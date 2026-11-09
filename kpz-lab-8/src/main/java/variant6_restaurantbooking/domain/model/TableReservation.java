package variant6_restaurantbooking.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableReservation {
    private final int reservationId;
    private final Guest guest;
    private final int guestsCount;
    private final String timeSlot;
    private final List<OrderLine> preOrderedItems;
    private double foodSubtotal;
    private double serviceCharge;
    private double totalBill;
    private String status;

    private TableReservation(int reservationId, Guest guest, int guestsCount, String timeSlot) {
        if (guestsCount <= 0) {
            throw new IllegalArgumentException("Кількість гостей має бути більше 0.");
        }
        this.reservationId = reservationId;
        this.guest = guest;
        this.guestsCount = guestsCount;
        this.timeSlot = timeSlot;
        this.preOrderedItems = new ArrayList<>();
        this.status = "Draft";
    }


    public static TableReservation create(int reservationId, Guest guest, int guestsCount, String timeSlot) {
        return new TableReservation(reservationId, guest, guestsCount, timeSlot);
    }

    public void addOrderLine(MenuItem item, int count) {
        this.preOrderedItems.add(new OrderLine(item, count));
    }


    public void calculateBaseBill() {
        double rawSubtotal = 0.0;
        for (OrderLine line : preOrderedItems) {
            rawSubtotal += line.getLineSubtotal();
        }

        this.foodSubtotal = rawSubtotal;

        if (this.guestsCount >= 5) {
            this.serviceCharge = this.foodSubtotal * 0.10;
        } else {
            this.serviceCharge = 0.0;
        }

        this.totalBill = this.foodSubtotal + this.serviceCharge;
        this.status = "Reserved";
    }

    public int getReservationId() { return reservationId; }
    public Guest getGuest() { return guest; }
    public int getGuestsCount() { return guestsCount; }
    public String getTimeSlot() { return timeSlot; }

    public List<OrderLine> getPreOrderedItems() {
        return Collections.unmodifiableList(preOrderedItems);
    }

    public double getFoodSubtotal() { return foodSubtotal; }
    public double getServiceCharge() { return serviceCharge; }
    public double getTotalBill() { return totalBill; }
    public String getStatus() { return status; }
}
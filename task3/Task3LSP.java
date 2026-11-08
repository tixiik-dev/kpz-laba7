
package task3;

import java.util.Locale;

public class Task3LSP {
    public static void main(String[] args) {
        FuelVehicle petrolCar =
                new FuelVehicle("CE1234AB", 20.0, 50.0);

        ElectricVehicle electricCar =
                new ElectricVehicle("CE5678CD", 30.0, 60.0);

        GasStationManager gasStation = new GasStationManager();
        ChargingStationManager chargingStation = new ChargingStationManager();

        gasStation.refuelVehicleToFull(petrolCar);
        chargingStation.chargeVehicleToFull(electricCar);

        Vehicle[] fleet = { petrolCar, electricCar };

        for (Vehicle vehicle : fleet) {
            vehicle.drive(100.0);
            System.out.printf(Locale.US,
                    "%s: mileage = %.1f km%n",
                    vehicle.getLicensePlate(),
                    vehicle.getMileageKm());
        }

        try {
            petrolCar.refuel(-5.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Fail-fast: " + e.getMessage());
        }
    }
}

// Базовий клас транспортного засобу
abstract class Vehicle {
    private final String licensePlate;
    private double mileageKm;

    protected Vehicle(String licensePlate) {
        if (licensePlate == null || licensePlate.isBlank()) {
            throw new IllegalArgumentException("License plate is required");
        }
        this.licensePlate = licensePlate;
    }

    public final String getLicensePlate() {
        return licensePlate;
    }

    public final double getMileageKm() {
        return mileageKm;
    }

    protected final void addMileage(double distanceKm) {
        mileageKm += distanceKm;
    }

    protected static void requirePositive(double value, String name) {
        if (!Double.isFinite(value) || value <= 0) {
            throw new IllegalArgumentException(
                    name + " must be positive and finite");
        }
    }

    protected static void validateStorage(double level, double capacity) {
        requirePositive(capacity, "Capacity");

        if (!Double.isFinite(level) || level < 0 || level > capacity) {
            throw new IllegalArgumentException(
                    "Level must be between 0 and capacity");
        }
    }

    public abstract void drive(double distanceKm);
}

// Бензиновий автомобіль
class FuelVehicle extends Vehicle {
    private double fuelLevelLiters;
    private final double tankCapacityLiters;

    public FuelVehicle(String plate, double fuelLevelLiters,
                       double tankCapacityLiters) {
        super(plate);
        validateStorage(fuelLevelLiters, tankCapacityLiters);

        this.fuelLevelLiters = fuelLevelLiters;
        this.tankCapacityLiters = tankCapacityLiters;
    }

    public double getFuelLevelLiters() {
        return fuelLevelLiters;
    }

    public double getTankCapacityLiters() {
        return tankCapacityLiters;
    }

    public void refuel(double liters) {
        requirePositive(liters, "Liters");

        if (liters > tankCapacityLiters - fuelLevelLiters) {
            throw new IllegalStateException("Tank overflow");
        }

        fuelLevelLiters += liters;

        System.out.printf(Locale.US,
                "Fuel car %s: %.1f L in tank%n",
                getLicensePlate(), fuelLevelLiters);
    }

    @Override
    public void drive(double distanceKm) {
        requirePositive(distanceKm, "Distance");

        double needed = distanceKm * 0.08;

        if (needed > fuelLevelLiters) {
            throw new IllegalStateException("Not enough fuel");
        }

        fuelLevelLiters -= needed;
        addMileage(distanceKm);

        System.out.printf(Locale.US,
                "Fuel car drove %.1f km; remaining fuel: %.1f L%n",
                distanceKm, fuelLevelLiters);
    }
}

// Електромобіль
class ElectricVehicle extends Vehicle {
    private double batteryLevelKWh;
    private final double batteryCapacityKWh;

    public ElectricVehicle(String plate, double batteryLevelKWh,
                           double batteryCapacityKWh) {
        super(plate);
        validateStorage(batteryLevelKWh, batteryCapacityKWh);

        this.batteryLevelKWh = batteryLevelKWh;
        this.batteryCapacityKWh = batteryCapacityKWh;
    }

    public double getBatteryLevelKWh() {
        return batteryLevelKWh;
    }

    public double getBatteryCapacityKWh() {
        return batteryCapacityKWh;
    }

    public void charge(double kWh) {
        requirePositive(kWh, "Charge");

        if (kWh > batteryCapacityKWh - batteryLevelKWh) {
            throw new IllegalStateException("Battery overflow");
        }

        batteryLevelKWh += kWh;

        System.out.printf(Locale.US,
                "Electric car %s: %.1f kWh in battery%n",
                getLicensePlate(), batteryLevelKWh);
    }

    @Override
    public void drive(double distanceKm) {
        requirePositive(distanceKm, "Distance");

        double needed = distanceKm * 0.20;

        if (needed > batteryLevelKWh) {
            throw new IllegalStateException("Not enough battery charge");
        }

        batteryLevelKWh -= needed;
        addMileage(distanceKm);

        System.out.printf(Locale.US,
                "Electric car drove %.1f km; remaining charge: %.1f kWh%n",
                distanceKm, batteryLevelKWh);
    }
}

// Заправна станція
class GasStationManager {
    public void refuelVehicleToFull(FuelVehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle is required");
        }

        double needed = vehicle.getTankCapacityLiters()
                - vehicle.getFuelLevelLiters();

        if (needed > 0) {
            vehicle.refuel(needed);
        }
    }
}

// Зарядна станція
class ChargingStationManager {
    public void chargeVehicleToFull(ElectricVehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle is required");
        }

        double needed = vehicle.getBatteryCapacityKWh()
                - vehicle.getBatteryLevelKWh();

        if (needed > 0) {
            vehicle.charge(needed);
        }
    }
}

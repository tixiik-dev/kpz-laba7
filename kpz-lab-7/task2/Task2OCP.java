import java.math.BigDecimal;
import java.math.RoundingMode;

// Головний клас
public class Task2OCP {
    public static void main(String[] args) {
        PolicyHolder holder = new PolicyHolder(
            "C-2001", 68, new BigDecimal("10000.00"), true
        );

        InsurancePremiumCalculator calculator =
            new InsurancePremiumCalculator();

        PremiumStrategy[] strategies = {
            new AutoPolicyStrategy(),
            new HealthPolicyStrategy(),
            new PropertyPolicyStrategy(),
            new TravelPolicyStrategy(),
            new PetPolicyStrategy()
        };

        for (PremiumStrategy strategy : strategies) {
            calculator.calculatePremium(holder, strategy);
        }
    }
}

// Дані клієнта
class PolicyHolder {
    private final String id;
    private final int age;
    private final BigDecimal amount;
    private final boolean priorClaims;

    PolicyHolder(String id, int age,
                 BigDecimal amount, boolean priorClaims) {
        if (id == null || id.isBlank() || age < 0 ||
            amount == null || amount.signum() < 0) {
            throw new IllegalArgumentException("Invalid client data");
        }

        this.id = id;
        this.age = age;
        this.amount = amount;
        this.priorClaims = priorClaims;
    }

    String getId() { return id; }
    int getAge() { return age; }
    BigDecimal getAmount() { return amount; }
    boolean hasClaims() { return priorClaims; }
}

// Інтерфейс для всіх страхових стратегій
interface PremiumStrategy {
    String getName();
    BigDecimal calculate(PolicyHolder holder);
}

// Автострахування
class AutoPolicyStrategy implements PremiumStrategy {
    public String getName() { return "AUTO"; }

    public BigDecimal calculate(PolicyHolder holder) {
        BigDecimal premium = holder.getAmount()
            .multiply(new BigDecimal("0.03"));

        if (holder.getAge() < 25 || holder.hasClaims()) {
            premium = premium.multiply(new BigDecimal("1.20"));
        }

        return premium;
    }
}

// Медичне страхування
class HealthPolicyStrategy implements PremiumStrategy {
    public String getName() { return "HEALTH"; }

    public BigDecimal calculate(PolicyHolder holder) {
        BigDecimal premium = new BigDecimal("200.00");

        if (holder.getAge() > 40) {
            premium = premium.add(
                BigDecimal.valueOf(holder.getAge() - 40)
                    .multiply(new BigDecimal("10.00"))
            );
        }

        if (holder.hasClaims()) {
            premium = premium.add(new BigDecimal("150.00"));
        }

        return premium;
    }
}

// Страхування майна
class PropertyPolicyStrategy implements PremiumStrategy {
    public String getName() { return "PROPERTY"; }

    public BigDecimal calculate(PolicyHolder holder) {
        return holder.getAmount()
            .multiply(new BigDecimal("0.005"));
    }
}

// Туристичне страхування
class TravelPolicyStrategy implements PremiumStrategy {
    public String getName() { return "TRAVEL"; }

    public BigDecimal calculate(PolicyHolder holder) {
        BigDecimal premium = new BigDecimal("30.00");

        if (holder.getAge() >= 65) {
            premium = premium.multiply(new BigDecimal("1.50"));
        }

        return premium;
    }
}

// Новий тип страхування для демонстрації OCP
class PetPolicyStrategy implements PremiumStrategy {
    public String getName() { return "PET"; }

    public BigDecimal calculate(PolicyHolder holder) {
        BigDecimal premium = holder.getAmount()
            .multiply(new BigDecimal("0.02"));

        if (holder.hasClaims()) {
            premium = premium.add(new BigDecimal("25.00"));
        }

        return premium;
    }
}

// Калькулятор, який не залежить від типу страхування
class InsurancePremiumCalculator {
    BigDecimal calculatePremium(
        PolicyHolder holder, PremiumStrategy strategy
    ) {
        if (holder == null || strategy == null) {
            throw new IllegalArgumentException("Missing data");
        }

        BigDecimal premium = strategy.calculate(holder)
            .setScale(2, RoundingMode.HALF_UP);

        System.out.println(
            holder.getId() + " | " + strategy.getName() +
            " insurance: $" + premium
        );

        return premium;
    }
}

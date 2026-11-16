package lab_task.task_1;

public class Equipment {
    private String weapon;
    private String armor;

    public Equipment(String weapon, String armor) {
        this.weapon = weapon;
        this.armor = armor;
    }

    public Equipment(Equipment other) {
        this.weapon = other.weapon;
        this.armor = other.armor;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getArmor() {
        return armor;
    }

    public void setArmor(String armor) {
        this.armor = armor;
    }

    @Override
    public String toString() {
        return "Зброя='" + weapon + "', Броня='" + armor + "'";
    }
}
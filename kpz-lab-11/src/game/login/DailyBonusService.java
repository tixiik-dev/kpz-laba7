package game.login;

import game.common.GameLog;
import java.util.HashSet;
import java.util.Set;

/** Підсистема щоденного бонусу: бонус видається лише раз на день. */
public class DailyBonusService {
    private final Set<String> grantedToday = new HashSet<>();

    public int grantDailyBonus(String playerName) {
        if (grantedToday.add(playerName)) {
            GameLog.log("DailyBonusService", "Нараховано щоденний бонус гравцю " + playerName + ": 100 золота");
            return 100;
        }
        GameLog.log("DailyBonusService", "Бонус гравцю " + playerName + " уже отримано сьогодні");
        return 0;
    }
}

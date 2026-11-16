package lab_task.task_2;

import lab_task.util.EventLogger;

public class SpawnSubsystem {
    public void updateMonsterSpawns() {
        WorldTime worldTime = WorldTime.getInstance();
        EventLogger.log("ПідсистемаСпавну", "Отримано екземпляр часу: " + worldTime.getInstanceId());

        if (worldTime.isNight()) {
            EventLogger.log("ПідсистемаСпавну", "Зафіксовано ніч (" + worldTime.getFormattedTime()
                    + ") -> Спавн нічних монстрів (Перевертні, Примари)");
        } else {
            EventLogger.log("ПідсистемаСпавну", "Зафіксовано день (" + worldTime.getFormattedTime()
                    + ") -> Спавн денних монстрів (Лісові вовки, Кабани)");
        }
    }
}
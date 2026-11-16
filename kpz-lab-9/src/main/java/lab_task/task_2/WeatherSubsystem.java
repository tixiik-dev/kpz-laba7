package lab_task.task_2;

import lab_task.util.EventLogger;

public class WeatherSubsystem {
    public void advanceTimeToNight() {
        WorldTime worldTime = WorldTime.getInstance();
        EventLogger.log("ПідсистемаПогоди", "Отримано екземпляр часу: " + worldTime.getInstanceId());
        worldTime.setHour(22);
        EventLogger.log("ПідсистемаПогоди", "Освітлення переведено в нічний режим (" + worldTime.getFormattedTime() + ")");
    }
}
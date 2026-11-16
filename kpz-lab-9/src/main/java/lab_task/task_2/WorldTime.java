package lab_task.task_2;

import lab_task.util.EventLogger;

public class WorldTime {
    private static volatile WorldTime instance;

    private int hour;
    private String season;

    private WorldTime() {
        this.hour = 12;
        this.season = "Осінь";
        EventLogger.log("WorldTime", "Ігровий час світу ініціалізовано (початковий час: 12:00, " + season + ")");
    }

    public static WorldTime getInstance() {
        if (instance == null) {
            synchronized (WorldTime.class) {
                if (instance == null) {
                    instance = new WorldTime();
                }
            }
        }
        return instance;
    }

    public synchronized void setHour(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Година має бути в межах 0..23");
        }
        this.hour = hour;
        EventLogger.log("WorldTime", "Час світу змінено на " + getFormattedTime()
                + " (" + (isNight() ? "Ніч" : "День") + ")");
    }

    public synchronized boolean isNight() {
        return hour >= 21 || hour < 6;
    }

    public synchronized String getFormattedTime() {
        return String.format("%02d:00", hour);
    }

    public String getInstanceId() {
        return "WorldTime@" + Integer.toHexString(System.identityHashCode(this));
    }
}
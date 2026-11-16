package lab_task.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventLogger {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public static void log(String source, String event) {
        String time = LocalTime.now().format(FORMATTER);
        System.out.printf("[%s] [%s] %s%n", time, source, event);
    }

    public static void simulateHeavyOperation(String source, String description, long millis) {
        log(source, "Початок операції: " + description + "...");
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log(source, "Завершено: " + description);
    }
}
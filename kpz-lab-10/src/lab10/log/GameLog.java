package lab10.log;

/** Єдиний журнал подій у форматі [час] [джерело] подія. Час умовний: кожна подія +1 секунда. */
public final class GameLog {
    private static int seconds = 12 * 3600;

    private GameLog() { }

    public static void log(String source, String event) {
        int s = seconds++;
        System.out.printf("[%02d:%02d:%02d] [%s] %s%n", (s / 3600) % 24, (s / 60) % 60, s % 60, source, event);
    }
}

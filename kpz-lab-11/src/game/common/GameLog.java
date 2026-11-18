package game.common;

/** Консольний журнал подій у форматі [час] [джерело] подія. Час умовний. */
public final class GameLog {
    private static int seconds = 0;

    private GameLog() { }

    public static void log(String source, String event) {
        seconds++;
        System.out.printf("[12:%02d:%02d] [%s] %s%n", (seconds / 60) % 60, seconds % 60, source, event);
    }

    public static void section(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }
}

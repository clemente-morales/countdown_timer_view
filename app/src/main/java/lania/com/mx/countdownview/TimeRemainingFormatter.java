package lania.com.mx.countdownview;

import java.util.concurrent.TimeUnit;

/**
 * @author Clemente Morales Fernandez
 * @since 1/23/2017.
 */

public abstract class TimeRemainingFormatter {
    protected final String format;

    protected long days;
    protected long hours;
    protected long minutes;
    protected long seconds;

    public TimeRemainingFormatter(String format) {
        this.format = format;
    }

    public void calcValues(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
    }

    public final String format(long timeRemaining) {
        calcValues(timeRemaining);
        return formatTimeRemaining();
    }

    public String getLabel1() {
        return "Days";
    }

    public String getLabel2() {
        return "Hour";
    }

    public String getLabel3() {
        return "Min";
    }

    protected abstract String formatTimeRemaining();
}

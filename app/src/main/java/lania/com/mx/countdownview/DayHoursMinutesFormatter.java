package lania.com.mx.countdownview;

/**
 * @author Clemente Morales Fernandez
 * @since 1/23/2017.
 */

public class DayHoursMinutesFormatter extends TimeRemainingFormatter {

    public static final String THREE_TIME_UNITS_FORMAT = "%02d:%02d:%02d";

    public DayHoursMinutesFormatter() {
        super(THREE_TIME_UNITS_FORMAT);
    }

    @Override
    protected String formatTimeRemaining() {
        return String.format(format, days, hours, minutes);
    }
}

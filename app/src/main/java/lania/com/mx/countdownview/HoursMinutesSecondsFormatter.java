package lania.com.mx.countdownview;

/**
 * @author Clemente Morales Fernandez
 * @since 1/23/2017.
 */

public class HoursMinutesSecondsFormatter extends TimeRemainingFormatter {
    public static final String THREE_TIME_UNITS_FORMAT = "%02d:%02d:%02d";

    public String getLabel1() {
        return "Hours";
    }

    public String getLabel2() {
        return "Min";
    }

    public String getLabel3() {
        return "Sec";
    }

    public HoursMinutesSecondsFormatter() {
        super(THREE_TIME_UNITS_FORMAT);
    }

    @Override
    protected String formatTimeRemaining() {
        return String.format(format, hours, minutes, seconds);
    }
}

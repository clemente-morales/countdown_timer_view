package lania.com.mx.countdownview;

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.concurrent.TimeUnit;

/**
 * @author Clemente Morales Fernandez
 * @since 1/23/2017.
 */

public class CountdownTime {
    private final String label1;
    private final String label2;
    private final String label3;
    private String value;
    private final float valueTopMargin;
    private PointF timeLabel1DrawPosition;
    private PointF timeLabel2DrawPosition;
    private PointF timeLabel3DrawPosition;

    private PointF timeValueDrawPosition;

    public CountdownTime(String label1, String label2, String label3, String value, float valueTopMargin) {
        this.label1 = label1;
        this.label2 = label2;
        this.label3 = label3;
        this.value = value;
        this.valueTopMargin = valueTopMargin;
    }

    public String getLabel1() {
        return label1;
    }

    public String getLabel2() {
        return label2;
    }

    public String getLabel3() {
        return label3;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PointF getTimeLabel1DrawPosition() {
        return timeLabel1DrawPosition;
    }

    public PointF getTimeLabel2DrawPosition() {
        return timeLabel2DrawPosition;
    }

    public PointF getTimeLabel3DrawPosition() {
        return timeLabel3DrawPosition;
    }

    public PointF getTimeValueDrawPosition() {
        return timeValueDrawPosition;
    }

    void calculatePosition(Paint timeLabelPaint, Paint timeValuePaint, PointF drawPosition) {
        Rect timeLabelBounds = new Rect();
        Rect timeValueBounds = new Rect();

        timeLabelPaint.getTextBounds(label1, 0, label1.length(), timeLabelBounds);

        float labelYPosition = drawPosition.y + timeLabelBounds.height();
        timeLabel1DrawPosition = new PointF(drawPosition.x, labelYPosition);

        timeValuePaint.getTextBounds(value, 0, value.length(), timeValueBounds);
        float valueYPosition = valueTopMargin + labelYPosition + timeValueBounds.height();
        timeValueDrawPosition = new PointF(drawPosition.x, valueYPosition);

        float timeValuesXPosition = drawPosition.x + timeValueBounds.width();
        timeLabelPaint.getTextBounds(label2, 0, label2.length(), timeLabelBounds);
        float secondLabelXPosition = (timeValuesXPosition / 2) - (timeLabelBounds.width() / 2);
        timeLabel2DrawPosition = new PointF(secondLabelXPosition, labelYPosition);

        timeLabelPaint.getTextBounds(label3, 0, label3.length(), timeLabelBounds);
        float thirdLabelXPosition = timeValuesXPosition - timeLabelBounds.width();
        timeLabel3DrawPosition = new PointF(thirdLabelXPosition, labelYPosition);

    }

    public static String formatDuration(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);


        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

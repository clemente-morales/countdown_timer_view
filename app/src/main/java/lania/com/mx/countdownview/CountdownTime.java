package lania.com.mx.countdownview;

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * @author Clemente Morales Fernandez
 * @since 1/23/2017.
 */

public class CountdownTime {
    private String value;
    private long remainingTime;

    private final float valueTopMargin;

    private PointF timeLabel1DrawPosition;
    private PointF timeLabel2DrawPosition;
    private PointF timeLabel3DrawPosition;

    private PointF timeValueDrawPosition;

    private TimeRemainingFormatter formatter;

    public CountdownTime(TimeRemainingFormatter formatter, long remainingTime, float valueTopMargin) {
        this.formatter = formatter;
        this.remainingTime = remainingTime;
        this.valueTopMargin = valueTopMargin;
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

        timeLabelPaint.getTextBounds(formatter.getLabel1(), 0, formatter.getLabel1().length(), timeLabelBounds);

        float labelYPosition = drawPosition.y + timeLabelBounds.height();
        timeLabel1DrawPosition = new PointF(drawPosition.x, labelYPosition);

        value = formatter.format(remainingTime);
        timeValuePaint.getTextBounds(value, 0, value.length(), timeValueBounds);
        float valueYPosition = valueTopMargin + labelYPosition + timeValueBounds.height();
        timeValueDrawPosition = new PointF(drawPosition.x, valueYPosition);

        float timeValuesXPosition = drawPosition.x + timeValueBounds.width();
        timeLabelPaint.getTextBounds(formatter.getLabel2(), 0, formatter.getLabel2().length(), timeLabelBounds);
        float secondLabelXPosition = (timeValuesXPosition / 2) - (timeLabelBounds.width() / 2);
        timeLabel2DrawPosition = new PointF(secondLabelXPosition, labelYPosition);

        timeLabelPaint.getTextBounds(formatter.getLabel3(), 0, formatter.getLabel3().length(), timeLabelBounds);
        float thirdLabelXPosition = timeValuesXPosition - timeLabelBounds.width();
        timeLabel3DrawPosition = new PointF(thirdLabelXPosition, labelYPosition);
    }
}

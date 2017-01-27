package lania.com.mx.countdownview;

import android.graphics.Canvas;
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

    private Paint labelTextPaint;
    private Paint valueTextPaint;

    private PointF timeLabel1DrawPosition;
    private PointF timeLabel2DrawPosition;
    private PointF timeLabel3DrawPosition;

    private PointF timeValueDrawPosition;

    private TimeRemainingFormatter formatter;

    private Rect timeValueBounds;

    public CountdownTime(TimeRemainingFormatter formatter, long remainingTime, float valueTopMargin, Paint labelTextPaint, Paint valueTextPaint) {
        this.formatter = formatter;
        this.remainingTime = remainingTime;
        this.valueTopMargin = valueTopMargin;
        this.labelTextPaint = labelTextPaint;
        this.valueTextPaint = valueTextPaint;
    }

    public void setFormatter(TimeRemainingFormatter formatter) {
        this.formatter = formatter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLabelTextPaint(Paint labelTextPaint) {
        this.labelTextPaint = labelTextPaint;
    }

    public void setValueTextPaint(Paint valueTextPaint) {
        this.valueTextPaint = valueTextPaint;
    }

    public void updateValue(long millisUntilFinished) {
        this.value = formatter.format(millisUntilFinished);
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

    public Rect getTimeValueBounds() {
        return timeValueBounds;
    }

    void calculatePosition(PointF drawPosition) {
        Rect timeLabelBounds = new Rect();
        timeValueBounds = new Rect();

        labelTextPaint.getTextBounds(formatter.getLabel1(), 0, formatter.getLabel1().length(), timeLabelBounds);

        float labelYPosition = drawPosition.y + timeLabelBounds.height();
        timeLabel1DrawPosition = new PointF(drawPosition.x, labelYPosition);

        value = formatter.format(remainingTime);
        valueTextPaint.getTextBounds(value, 0, value.length(), timeValueBounds);
        float valueYPosition = valueTopMargin + labelYPosition + timeValueBounds.height();
        timeValueDrawPosition = new PointF(drawPosition.x, valueYPosition);

        float timeValuesXPosition = drawPosition.x + timeValueBounds.width();
        labelTextPaint.getTextBounds(formatter.getLabel2(), 0, formatter.getLabel2().length(), timeLabelBounds);
        float secondLabelXPosition = (timeValuesXPosition / 2) - (timeLabelBounds.width() / 2);
        timeLabel2DrawPosition = new PointF(secondLabelXPosition, labelYPosition);

        labelTextPaint.getTextBounds(formatter.getLabel3(), 0, formatter.getLabel3().length(), timeLabelBounds);
        float thirdLabelXPosition = timeValuesXPosition - timeLabelBounds.width();
        timeLabel3DrawPosition = new PointF(thirdLabelXPosition, labelYPosition);
    }

    public void displayTime(Canvas canvas) {
        canvas.drawText(formatter.getLabel1(), timeLabel1DrawPosition.x, timeLabel1DrawPosition.y, labelTextPaint);
        canvas.drawText(formatter.getLabel2(), timeLabel2DrawPosition.x, timeLabel2DrawPosition.y, labelTextPaint);
        canvas.drawText(formatter.getLabel3(), timeLabel3DrawPosition.x, timeLabel3DrawPosition.y, labelTextPaint);

        canvas.drawText(value, timeValueDrawPosition.x, timeValueDrawPosition.y, valueTextPaint);
    }
}

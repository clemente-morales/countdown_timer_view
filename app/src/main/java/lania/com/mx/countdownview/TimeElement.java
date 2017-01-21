package lania.com.mx.countdownview;

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by clerks on 1/21/17.
 */

public class TimeElement {
    private final String label;
    private final String value;
    private final float valueTopMargin;
    private PointF timeLabelDrawPosition;
    private PointF timeLabelDrawEndPosition;

    private PointF timeValueDrawPosition;
    private PointF timeValueDrawEndPosition;

    public TimeElement(String label, String value, float valueTopMargin) {
        this.label = label;
        this.value = value;
        this.valueTopMargin = valueTopMargin;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public PointF getTimeLabelDrawPosition() {
        return timeLabelDrawPosition;
    }

    public PointF getTimeLabelDrawEndPosition() {
        return timeLabelDrawEndPosition;
    }

    public PointF getTimeValueDrawPosition() {
        return timeValueDrawPosition;
    }

    public PointF getTimeValueDrawEndPosition() {
        return timeValueDrawEndPosition;
    }

    void calculatePosition(Paint timeLabelPaint, Paint timeValuePaint, PointF drawPosition) {
        Rect timeLabelBounds = new Rect();
        Rect timeValueBounds = new Rect();

        timeLabelPaint.getTextBounds(label, 0, label.length(), timeLabelBounds);
        float labelYPosition = drawPosition.y + timeLabelBounds.height();
        timeLabelDrawPosition = new PointF(drawPosition.x, labelYPosition);
        timeLabelDrawEndPosition = new PointF(drawPosition.x + timeLabelBounds.width(), drawPosition.y);

        timeValuePaint.getTextBounds(value, 0, value.length(), timeValueBounds);
        float valueYPosition = valueTopMargin + labelYPosition + timeValueBounds.height();
        timeValueDrawPosition = new PointF(drawPosition.x, valueYPosition);
        timeValueDrawEndPosition = new PointF(drawPosition.x + timeValueBounds.width(), labelYPosition);

    }
}

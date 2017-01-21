package lania.com.mx.countdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import static lania.com.mx.countdownview.R.attr.valueTextSize;

/**
 * Created by clerks on 1/21/17.
 */

public class CountdownView extends View {
    public static final int DEFAULT_TIME = 10000;

    private long time;
    private final int mTextColor;
    private final int labelTextSize;
    private final int valueTextSize;
    private Paint labelTextPaint;
    private Paint valueTextPaint;
    private TimeElement timeElement;

    public CountdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountDown,
                0, 0);

        try {
            time = a.getInt(R.styleable.CountDown_time, DEFAULT_TIME);
            mTextColor = a.getColor(R.styleable.CountDown_labelTextColor, getContext().getColor(R.color.defaultTextColor));
            labelTextSize = a.getDimensionPixelSize(R.styleable.CountDown_labelTextSize, (int) getResources().getDimension(R.dimen.countdown_label_text_size));
            valueTextSize = a.getDimensionPixelSize(R.styleable.CountDown_valueTextSize, (int) getResources().getDimension(R.dimen.countdown_value_text_size));
        } finally {
            a.recycle();
        }

        init();
    }

    public void setTime(long time) {
        this.time = time;
        invalidate();
    }

    private void init() {
        labelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelTextPaint.setColor(mTextColor);
        labelTextPaint.setTextSize(labelTextSize);
        labelTextPaint.setTypeface(Typeface.DEFAULT);

        valueTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valueTextPaint.setColor(mTextColor);
        valueTextPaint.setTextSize(valueTextSize);
        valueTextPaint.setTypeface(Typeface.SANS_SERIF);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // calculate padding
        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());


        float ww = (float) w - xpad;
        float hh = (float) h - ypad;

        float initialXPosition = 0.0f;
        float initialYPosition = 0.0f;
        float valueTopMargin = 20f;
        PointF initialDrawingPosition = new PointF(initialXPosition, initialYPosition);
        timeElement = new TimeElement("Day", "21", valueTopMargin);
        timeElement.calculatePosition(labelTextPaint, valueTextPaint, initialDrawingPosition);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PointF labelLocation = timeElement.getTimeLabelDrawPosition();
        PointF valueLocation = timeElement.getTimeValueDrawPosition();
        canvas.drawText(timeElement.getLabel(), labelLocation.x, labelLocation.y, labelTextPaint);
        canvas.drawText(timeElement.getValue(), valueLocation.x, valueLocation.y, valueTextPaint);
    }
}

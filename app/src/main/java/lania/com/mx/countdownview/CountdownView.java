package lania.com.mx.countdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by clerks on 1/21/17.
 */

public class CountdownView extends View {
    public static final int DEFAULT_TIME = 10000;
    public static final int ONE_SECOND_INTERVAL = 1000;

    private long time;
    private final int mTextColor;
    private final int labelTextSize;
    private final int valueTextSize;
    private Paint labelTextPaint;
    private Paint valueTextPaint;
    private CountdownTime timeElement;
    private TimeRemainingFormatter formatter;

    private MilestoneListener onCompleteCountdownListener;

    /**
     * Points of interest for the view holder. Allows to interact with the Countdown view and set new parameters or formats.
     */
    private List<Milestone> milestones = new ArrayList<>();

    public CountdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountDown,
                0, 0);

        try {
            time = a.getInt(R.styleable.CountDown_time, DEFAULT_TIME);
            mTextColor = a.getColor(R.styleable.CountDown_labelTextColor, getResources().getColor(R.color.defaultTextColor));
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

    public void setOnCompleteCountdownListener(MilestoneListener onCompleteCountdownListener) {
        this.onCompleteCountdownListener = onCompleteCountdownListener;
    }

    public void addMilestone(Milestone milestone) {
        this.milestones.add(milestone);
    }

    public void setFormatter(TimeRemainingFormatter formatter) {
        this.formatter = formatter;
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

        formatter = new DayHoursMinutesFormatter();

        new CountDownTimer(time, ONE_SECOND_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                if (timeElement == null)
                    return;

                for (Iterator<Milestone> iterator = milestones.iterator(); iterator.hasNext();) {
                    Milestone milestone = iterator.next();
                    if (milestone.isCompleted(millisUntilFinished)) {
                        iterator.remove();
                        milestone.onComplete();
                    }
                }

                timeElement.setValue(formatter.format(millisUntilFinished));
                invalidate();
            }

            public void onFinish() {
                if (onCompleteCountdownListener != null)
                    onCompleteCountdownListener.onComplete();
            }
        }.start();
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
        timeElement = new CountdownTime(formatter, time, valueTopMargin);
        timeElement.calculatePosition(labelTextPaint, valueTextPaint, initialDrawingPosition);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PointF label1Location = timeElement.getTimeLabel1DrawPosition();
        PointF label2Location = timeElement.getTimeLabel2DrawPosition();
        PointF label3Location = timeElement.getTimeLabel3DrawPosition();
        canvas.drawText(formatter.getLabel1(), label1Location.x, label1Location.y, labelTextPaint);
        canvas.drawText(formatter.getLabel2(), label2Location.x, label2Location.y, labelTextPaint);
        canvas.drawText(formatter.getLabel3(), label3Location.x, label3Location.y, labelTextPaint);

        PointF valueLocation = timeElement.getTimeValueDrawPosition();
        canvas.drawText(timeElement.getValue(), valueLocation.x, valueLocation.y, valueTextPaint);
    }
}

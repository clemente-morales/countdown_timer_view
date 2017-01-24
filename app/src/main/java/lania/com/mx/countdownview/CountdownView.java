package lania.com.mx.countdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by clerks on 1/21/17.
 */

public class CountdownView extends View {
    public static final int DEFAULT_TIME = 10000;
    public static final int ONE_SECOND_INTERVAL = 1000;

    private float valueTopMargin;
    private long time;
    private final int mTextColor;
    private final int labelTextSize;
    private final int valueTextSize;
    private Paint labelTextPaint;
    private Paint valueTextPaint;
    private CountdownTime timeElement;
    private TimeRemainingFormatter formatter;

    private MilestoneListener onCompleteCountdownListener;

    float initialXPosition = 0.0f;
    float initialYPosition = 0.0f;

    /**
     * Points of interest for the view holder. Allows to interact with the Countdown view and set new parameters or formats.
     */
    private List<Milestone> milestones = new ArrayList<>();

    private PointF initialDrawingPosition;
    private CountDownTimer countdownTimer;

    public CountdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountDown,
                0, 0);

        try {
            time = a.getInt(R.styleable.CountDown_time, DEFAULT_TIME);
            valueTopMargin = a.getDimensionPixelSize(R.styleable.CountDown_valueTopMargin, (int) getResources().getDimension(R.dimen.countdown_value_top_margin));
            mTextColor = a.getColor(R.styleable.CountDown_labelTextColor, getResources().getColor(R.color.defaultTextColor));
            labelTextSize = a.getDimensionPixelSize(R.styleable.CountDown_labelTextSize, (int) getResources().getDimension(R.dimen.countdown_label_text_size));
            valueTextSize = a.getDimensionPixelSize(R.styleable.CountDown_valueTextSize, (int) getResources().getDimension(R.dimen.countdown_value_text_size));
        } finally {
            a.recycle();
        }

        init();
    }

    /**
     * Use only to animate the timer.
     * @param time Remaining time to display.
     */
    public void setTime(int time) {
        this.time = time;
        countdownTimer.onTick(time);
    }

    public void setRemainingTime(long time) {
        this.time = time;
        countdownTimer.cancel();
        countdownTimer = buildCountdownTimer();
    }

    public void setOnCompleteCountdownListener(MilestoneListener onCompleteCountdownListener) {
        this.onCompleteCountdownListener = onCompleteCountdownListener;
    }

    public void addMilestone(Milestone milestone) {
        this.milestones.add(milestone);
    }

    public void setFormatter(TimeRemainingFormatter formatter) {
        timeElement.setFormatter(formatter);
    }

    public void start() {
        countdownTimer.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // calculate padding
        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());


        float ww = (float) w - xpad;
        float hh = (float) h - ypad;

        timeElement.calculatePosition(initialDrawingPosition);
    }

    /**
     * Override countdown creation if you require to display more than 3 time units.
     */
    @NonNull
    protected CountdownTime buildCountdownTime(float valueTopMargin, Paint labelTextPaint, Paint valueTextPaint) {
        return new CountdownTime(formatter, time, valueTopMargin, labelTextPaint, valueTextPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        timeElement.displayTime(canvas);
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

        if (formatter == null)
            formatter = new DayHoursMinutesFormatter();

        timeElement = buildCountdownTime(valueTopMargin, labelTextPaint, valueTextPaint);
        initialDrawingPosition = new PointF(initialXPosition, initialYPosition);
        timeElement.calculatePosition(initialDrawingPosition);

        countdownTimer = buildCountdownTimer();
    }

    private CountDownTimer buildCountdownTimer() {

        return new CountDownTimer(time, ONE_SECOND_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                for (Iterator<Milestone> iterator = milestones.iterator(); iterator.hasNext(); ) {
                    Milestone milestone = iterator.next();
                    if (milestone.isCompleted(millisUntilFinished)) {
                        iterator.remove();
                        milestone.onComplete();
                    }
                }

                timeElement.updateValue(millisUntilFinished);
                invalidate();
            }

            public void onFinish() {
                if (onCompleteCountdownListener != null)
                    onCompleteCountdownListener.onComplete();
            }
        };
    }

    public CountdownTime getTimeElement() {
        return timeElement;
    }

    public List<Milestone> getMilestones() {
        return Collections.unmodifiableList(milestones);
    }
}

package lania.com.mx.countdownview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public static final String REMAINING_TIME_PROPERTY = "time";
    public static final int ANIM_DURATION = 3000;
    private Milestone switchDaysToHoursMilestone = new Milestone(new MilestoneListener() {
        @Override
        public void onComplete() {
            countdownView.setFormatter(new HoursMinutesSecondsFormatter());
        }
    }, new MilestoneEvaluator() {
        @Override
        public boolean isCompleted(long remainingTime) {
            long days = TimeUnit.MILLISECONDS.toDays(remainingTime);
            return days == 0;
        }
    });

    private CountdownView countdownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownView = (CountdownView) findViewById(R.id.offerCountdown);
        countdownView.setOnCompleteCountdownListener(new MilestoneListener() {
            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "Countdown has finished", Toast.LENGTH_SHORT).show();
            }
        });
        countdownView.addMilestone(switchDaysToHoursMilestone);
        countdownView.setFormatter(new DayHoursMinutesFormatter());
        addAnimation();

        Button boldText = (Button) findViewById(R.id.boldLabel);
        boldText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextPaint textPaint = buildBoldLabelTextPaint();
                countdownView.setLabelTextPaint(textPaint);
            }
        });

    }

    private TextPaint buildBoldLabelTextPaint() {
        TextPaint labelTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        labelTextPaint.setColor(getResources().getColor(R.color.defaultTextColor));
        labelTextPaint.setTextSize((int)getResources().getDimension(R.dimen.countdown_label_text_size));
        labelTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        return labelTextPaint;
    }

    private void addAnimation() {
        // Animate starting point
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        ObjectAnimator restoreCurrentRemainingTime = ObjectAnimator.ofInt(countdownView, REMAINING_TIME_PROPERTY, 180000, 5000);
        restoreCurrentRemainingTime.setDuration(ANIM_DURATION);
        restoreCurrentRemainingTime.setInterpolator(linearInterpolator);
        restoreCurrentRemainingTime.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                countdownView.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        restoreCurrentRemainingTime.start();
    }
}

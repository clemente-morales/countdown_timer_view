package lania.com.mx.countdownview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

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
    }
}

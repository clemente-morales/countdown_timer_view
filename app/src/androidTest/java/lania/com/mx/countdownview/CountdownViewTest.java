package lania.com.mx.countdownview;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by clerks on 1/23/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CountdownViewTest {
    boolean completed = false;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void when_countdown_reaches_zero_on_finish_listener_is_invoked() throws InterruptedException {
        final long waitingTime = TimeUnit.SECONDS.toMillis(3);

        final CountDownLatch signals = new CountDownLatch(1);

        final CountdownView offerCountDown = (CountdownView) mActivityRule.getActivity().findViewById(R.id.offerCountdown);

        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                offerCountDown.setRemainingTime(waitingTime);
            }
        });


        offerCountDown.setOnCompleteCountdownListener(new MilestoneListener() {
            @Override
            public void onComplete() {
                completed = true;
                signals.countDown();
            }
        });

        offerCountDown.start();
        signals.await();
        Assert.assertTrue(completed);

        CountdownTime timeElement = offerCountDown.getTimeElement();
        Assert.assertEquals("00:00:01", timeElement.getValue());
    }
}

# countdown_timer_view
Countdown timer to be displayed in a view. Allows to pass callbacks to be executed in specific points and format setting to display the remaining time.

![Alt text](countdownView.gif?raw=true "Countdown view")

## Adding the control
Adding the control is simple
 ```xml

 <?xml version="1.0" encoding="utf-8"?>
 <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
     xmlns:custom="http://schemas.android.com/apk/res-auto"
     xmlns:tools="http://schemas.android.com/tools"
     android:id="@+id/activity_main"
     android:layout_width="match_parent"
     android:layout_height="match_parent"
     android:paddingBottom="@dimen/activity_vertical_margin"
     android:paddingLeft="@dimen/activity_horizontal_margin"
     android:paddingRight="@dimen/activity_horizontal_margin"
     android:paddingTop="@dimen/activity_vertical_margin"
     tools:context="lania.com.mx.countdownview.MainActivity">

     <lania.com.mx.countdownview.CountdownView
         android:id="@+id/offerCountdown"
         android:layout_width="match_parent"
         android:layout_height="match_parent"
         custom:labelTextSize="@dimen/countdown_label_text_size"
         custom:time="5000"
         custom:valueTextSize="@dimen/countdown_value_text_size" />
 </RelativeLayout>
```

Don't forget to start the timer
 ```java
  countdownView = (CountdownView) findViewById(R.id.offerCountdown);
  countdownView.start();
```

## Setting formatters
Let's change the formatter

 ```java
countdownView.setFormatter(new DayHoursMinutesFormatter());
```

## On complete countdown lister

Let's register for the on complete countdown lister

```java
countdownView.setOnCompleteCountdownListener(new MilestoneListener() {
            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "Countdown has finished", Toast.LENGTH_SHORT).show();
            }
        });
```


## Milestones

Let's register a milestone to switch between days-hour-min to hours-min-sec
```java
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

countdownView.addMilestone(switchDaysToHoursMilestone);
```

## Animate
Let's animate the countdown view restore values

```java
LinearInterpolator linearInterpolator = new LinearInterpolator();
ObjectAnimator restoreCurrentRemainingTime = ObjectAnimator.ofInt(countdownView, REMAINING_TIME_PROPERTY, 180000 , 5000 );
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
```


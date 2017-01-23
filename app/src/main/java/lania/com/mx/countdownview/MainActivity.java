package lania.com.mx.countdownview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountdownView countdownView = (CountdownView) findViewById(R.id.offerCountdown);
        countdownView.setCountdownListener(new CountdownView.CountdownListener() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Countdown reched 0", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

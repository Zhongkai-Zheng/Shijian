package com.example.alexzheng.shijian;

import android.os.SystemClock;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class RecordTime extends AppCompatActivity {

    private Button mStartButton, mResetButton, mSaveButton;
    private Chronometer mChronometer;

    private boolean isStopped; // true if timer stopped, false if timer going

    // timeWhenStopped stuff from http://stackoverflow.com/questions/5740516/chronometer-doesnt-stop-in-android
    private long timeWhenStopped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_time);

        setUpButtons();
        setUpChronometer();
    }

    private void setUpButtons() {
        isStopped = true;

        mStartButton = (Button) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTimerState();
            }
        });

        mResetButton = (Button) findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNextActivity = new Intent(getApplicationContext(), SaveTimePage.class);
                startActivity(goToNextActivity);
            }
        });
    }

    private void setUpChronometer() {
        mChronometer = (Chronometer) findViewById(R.id.time_Chronometer);
        timeWhenStopped = 0;

        // http://stackoverflow.com/questions/4152569/how-to-change-format-of-chronometer
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String hh = h < 10 ? "0" + h : h + "";
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                cArg.setText(hh + ":" + mm + ":" + ss);
            }
        });
    }

    private void changeTimerState() {
        if(isStopped) {
            // previously was stopped
            if (timeWhenStopped == 0) {
                mChronometer.setBase(SystemClock.elapsedRealtime());
            }
            else {
                mChronometer.setBase(mChronometer.getBase() + SystemClock.elapsedRealtime() - timeWhenStopped);
            }

            mChronometer.start();
            mStartButton.setText("Stop");
        }
        else {
            // previously was running
            timeWhenStopped = SystemClock.elapsedRealtime();
            mChronometer.stop();
            mStartButton.setText("Start");
        }

        isStopped = !isStopped;
    }

    private void reset() {
        mChronometer.stop();

        isStopped = true;
        timeWhenStopped = 0;
        
        mStartButton.setText("Start");
        mChronometer.setText("00:00:00");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_time, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

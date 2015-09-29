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

import java.util.Calendar;

public class RecordTime extends AppCompatActivity {

    private Button startButton, resetButton, saveButton;
    private Chronometer timer;
    Calendar c;
    private boolean started;
    private boolean isStopped; // true if timer stopped, false if timer going

    // timeWhenStopped stuff from http://stackoverflow.com/questions/5740516/chronometer-doesnt-stop-in-android
    private long timeWhenStopped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_time);

        setUpButtons();
        setUpChronometer();
        c = Calendar.getInstance();
        started = false;
    }

    private void setUpButtons() {
        isStopped = true;

        startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(started == false) {
                    int seconds = c.get(Calendar.SECOND);
                    int hours = c.get(Calendar.HOUR_OF_DAY);
                    int minutes = c.get(Calendar.MINUTE);
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int[] time = new int[]{year, month, day, hours, minutes, seconds};
                    GlobalClass.getInstance().setTempStartTime(time);
                }
                if(started == true){
                    int seconds = c.get(Calendar.SECOND);
                    int hours = c.get(Calendar.HOUR_OF_DAY);
                    int minutes = c.get(Calendar.MINUTE);
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int[] time = new int[]{year, month, day, hours, minutes, seconds};
                    GlobalClass.getInstance().setTempEndTime(time);
                }
                changeTimerState();
            }
        });

        resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = SystemClock.elapsedRealtime() - timer.getBase();
                GlobalClass.getInstance().setTempDuration(time);

                Intent goToNextActivity = new Intent(getApplicationContext(), SaveTimePage.class);
                startActivity(goToNextActivity);
            }
        });
    }

    private void setUpChronometer() {
        timer = (Chronometer) findViewById(R.id.time_Chronometer);
        timeWhenStopped = 0;

        // http://stackoverflow.com/questions/4152569/how-to-change-format-of-chronometer
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
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
                timer.setBase(SystemClock.elapsedRealtime());
            }
            else {
                timer.setBase(timer.getBase() + SystemClock.elapsedRealtime() - timeWhenStopped);
            }

            timer.start();
            startButton.setText(getString(R.string.stop));
        }
        else {
            // previously was running
            timeWhenStopped = SystemClock.elapsedRealtime();
            timer.stop();
            startButton.setText(getString(R.string.start));
        }

        isStopped = !isStopped;
    }

    private void reset() {
        timer.stop();

        isStopped = true;
        timeWhenStopped = 0;

        startButton.setText(getString(R.string.start));
        timer.setText(getString(R.string.zero));
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

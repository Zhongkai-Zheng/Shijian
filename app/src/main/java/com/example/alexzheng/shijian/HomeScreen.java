package com.example.alexzheng.shijian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    private Button mNewTimeButton, mHistoryButton, mOptionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        mHistoryButton = (Button)findViewById(R.id.history_button);
        mNewTimeButton = (Button)findViewById(R.id.new_time_button);
        mOptionsButton = (Button)findViewById(R.id.options_button);

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNextActivity = new Intent(getApplicationContext(), HistoryPage.class);
                startActivity(goToNextActivity);
            }
        });

        mNewTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNextActivity = new Intent(getApplicationContext(), RecordTime.class);
                startActivity(goToNextActivity);
            }
        });

        mOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNextActivity = new Intent(getApplicationContext(), SettingsPage.class);
                startActivity(goToNextActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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

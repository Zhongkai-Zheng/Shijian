package com.example.alexzheng.shijian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SaveTimePage extends AppCompatActivity {

    private EditText timeName, newFolderName;
    private Button saveButton, cancelButton;
    private boolean useNewFolder = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_time_page);
        timeName = (EditText)findViewById(R.id.time_name);
        newFolderName = (EditText)findViewById(R.id.time_name);
        saveButton = (Button)findViewById(R.id.save_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(useNewFolder == true){
                    GlobalClass.getInstance().addFolder(new Folder(newFolderName.toString()));
                    GlobalClass.getInstance().getFolderList().get(0).addTime(new Time(timeName.toString(), GlobalClass.getInstance().getTempStartTime(), GlobalClass.getInstance().getTempEndTime()));
                }
                else{
                    GlobalClass.getInstance().getFolderList().get(GlobalClass.getInstance().getTempFolderSelection()).addTime(new Time(timeName.toString(), GlobalClass.getInstance().getTempStartTime(), GlobalClass.getInstance().getTempEndTime()));
                }
                Intent goToNextActivity = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(goToNextActivity);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNextActivity = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(goToNextActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save_time_page, menu);
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

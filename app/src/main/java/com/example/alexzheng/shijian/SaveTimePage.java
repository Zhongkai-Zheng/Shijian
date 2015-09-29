package com.example.alexzheng.shijian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SaveTimePage extends AppCompatActivity {

    private EditText nameEditText, folderEditText;
    private Button saveButton, cancelButton;
    private boolean useNewFolder = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_time_page);

        nameEditText = (EditText) findViewById(R.id.name_editText);
        folderEditText = (EditText) findViewById(R.id.folder_editText);

        setUpButtons();
    }

    private void setUpButtons() {
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalClass g = GlobalClass.getInstance();
                int index;
                if (useNewFolder == true) {
                    g.addFolder(new Folder(folderEditText.getText().toString())); // create new folder and add to global class
                    index = 0; // add new folder to beginning
                } else {
                    Log.d("test2", "false");
                    index = g.getTempFolderSelection();
                }

                // add time to appropriate folder
                Time time = new Time(nameEditText.getText().toString(), g.getTempDuration());
                time.setStartTime(GlobalClass.getInstance().getTempStartTime());
                time.setEndTime(GlobalClass.getInstance().getTempEndTime());
                g.getFolderList().get(index).addTime(time);

                Intent goToNextActivity = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(goToNextActivity);
            }
        });

        cancelButton = (Button)findViewById(R.id.cancel_button);
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

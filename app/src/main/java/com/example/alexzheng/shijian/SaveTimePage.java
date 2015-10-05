package com.example.alexzheng.shijian;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SaveTimePage extends Activity {

    private EditText nameEditText, folderEditText;
    private Spinner folderSpinner;
    private Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_time_page);

        nameEditText = (EditText) findViewById(R.id.name_editText);
        folderEditText = (EditText) findViewById(R.id.folder_editText);

        setUpSpinner();
        setUpButtons();
    }

    private void setUpSpinner() {
        folderSpinner = (Spinner) findViewById(R.id.folder_spinner);
        String[] names = GlobalClass.getInstance().getFolderNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        folderSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected, // Optional
                        this));
    }

    private void setUpButtons() {
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalClass g = GlobalClass.getInstance();
                int index = 0;

                boolean useNewFolder = folderEditText.getText().toString().trim().length() != 0;
                boolean useOldFolder = folderSpinner.getSelectedItem() != null;
                if ((useNewFolder && useOldFolder) || (!useNewFolder && !useOldFolder)) {
                    // user inputted something in both text box and spinner or did not input anything
                    Toast errorToast = Toast.makeText(getApplicationContext(),
                            "Please make sure you filled in exactly one of the folder options.", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
                else {
                    if (useNewFolder) {
                        // create new folder and add to global class
                        g.addFolder(new Folder(folderEditText.getText().toString()));
                        index = 0; // add new folder to beginning
                    } else {
                        // set index to selected item on spinner
                        g.setTempFolderSelection(folderSpinner.getSelectedItemPosition());
                        index = g.getTempFolderSelection() - 1;
                    }

                    // add time to appropriate folder
                    Time time = new Time(nameEditText.getText().toString(), g.getTempDuration());
                    time.setStartTime(g.getTempStartTime());
                    time.setEndTime(g.getTempEndTime());
                    g.getFolderList().get(index).addTime(time);

                    Intent goToNextActivity = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(goToNextActivity);
                }
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

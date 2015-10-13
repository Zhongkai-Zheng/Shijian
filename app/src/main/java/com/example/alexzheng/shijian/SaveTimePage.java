package com.example.alexzheng.shijian;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class SaveTimePage extends AppCompatActivity {

    private EditText nameEditText, folderEditText;
    private Spinner folderSpinner;
    private Button saveButton, cancelButton;
    private ArrayList<String> names;
    private boolean useOldFolder, useNewFolder;
    private GlobalClass g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_time_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        g = GlobalClass.getInstance();

        //if an existing time is being edited, delete the original time.
        if(g.getOnEditMode() == 1){
            g.getFolder(g.getTempFolderSelection()).removeTime(g.getTempTimeSelection());
            //reset to non-edit mode
        }

        nameEditText = (EditText) findViewById(R.id.name_editText);
        folderEditText = (EditText) findViewById(R.id.folder_editText);

        setUpSpinner();
        setUpButtons();
    }

    private void setUpSpinner() {
        folderSpinner = (Spinner) findViewById(R.id.folder_spinner);
        names = new ArrayList<String>(Arrays.asList(g.getFolderNames()));
        names.add("+ Create New Folder");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        folderSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        this));

        folderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == names.size()) {
                    // selected create new folder item
                    folderEditText.setVisibility(View.VISIBLE);
                    setBooleans(false, true);

                } else {
                    // different item
                    folderEditText.setVisibility(View.INVISIBLE);
                    setBooleans(true, false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setBooleans(false, false);
            }
        });
    }

    private void setBooleans(boolean oldF, boolean newF) {
        useOldFolder = oldF;
        useNewFolder = newF;
    }

    private void setUpButtons() {
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if an existing folder is being edited, only change the folder name
                if (g.getOnEditMode() == 2) {
                    g.getFolder(g.getTempFolderSelection()).setName(folderEditText.getText().toString());
                    g.setOnEditMode(0);
                    //reset to non-edit mode
                    Intent goToNextActivity = new Intent(getApplicationContext(), HistoryPage.class);
                    startActivity(goToNextActivity);
                } else {
                    int index = 0;

                    // check whether title field is blank, if so display error message
                    if (nameEditText.getText().toString().trim().length() == 0) {
                        Toast errorToast = Toast.makeText(getApplicationContext(),
                                R.string.title_warning, Toast.LENGTH_SHORT);
                        errorToast.show();
                    }

                    // check whether folder title field is blank, if so display error message
                    else if (useNewFolder && folderEditText.getText().toString().trim().length() == 0) {
                        Toast errorToast = Toast.makeText(getApplicationContext(),
                                R.string.folderName_warning, Toast.LENGTH_SHORT);
                        errorToast.show();
                    }

                    else if (folderSpinner.getSelectedItem() == null) {
                        // user did not select anything
                        Toast errorToast = Toast.makeText(getApplicationContext(),
                                R.string.selectFolder_warning, Toast.LENGTH_SHORT);
                        errorToast.show();
                    }

                    // no error messages
                    else {
                        if (useOldFolder) {
                            // user selected old folder

                            g.setTempFolderSelection(folderSpinner.getSelectedItemPosition());
                            index = g.getTempFolderSelection() - 1;
                        }
                        else {
                            // user selected create new folder

                            // create new folder and add to global class
                            g.addFolder(new Folder(folderEditText.getText().toString()));
                            index = 0; // add new folder to beginning
                        }

                        // add time to appropriate folder
                        Time time = new Time(nameEditText.getText().toString(), g.getTempDuration());
                        time.setStartTime(g.getTempStartTime());
                        g.getFolderList().get(index).addTime(time);
                        if(g.getOnEditMode() == 0) {
                            Intent goToNextActivity = new Intent(getApplicationContext(), HomeScreen.class);
                            startActivity(goToNextActivity);
                        }
                        if(g.getOnEditMode() == 1){
                            g.setOnEditMode(0);
                            Intent goToNextActivity = new Intent(getApplicationContext(), HistoryPage.class);
                            startActivity(goToNextActivity);
                        }
                        g.setTempDuration(0); // reset tempDuration
                    }
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

    private void displayToast(String text) {
        Toast errorToast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
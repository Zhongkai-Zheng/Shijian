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
    private GlobalClass g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_time_page);

        g = GlobalClass.getInstance();

        //if an existing time is being editted, delete the original time.
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
        String[] names = g.getFolderNames();

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
                //if an existing folder is being editted, only change the folder name
                if (g.getOnEditMode() == 2) {
                    g.getFolder(g.getTempFolderSelection()).setName(folderEditText.getText().toString());
                    g.setOnEditMode(0);
                    //reset to non-edit mode
                    Intent goToNextActivity = new Intent(getApplicationContext(), HistoryPage.class);
                    startActivity(goToNextActivity);
                } else {
                    int index = 0;

                    boolean useNewFolder = folderEditText.getText().toString().trim().length() != 0;
                    boolean useOldFolder = folderSpinner.getSelectedItem() != null;
                    boolean titleBlank = nameEditText.getText().toString().trim().length() == 0;

                    if ((useNewFolder && useOldFolder) || (!useNewFolder && !useOldFolder)) {
                        // user inputted something in both text box and spinner or did not input anything
                        Toast errorToast = Toast.makeText(getApplicationContext(),
                                R.string.folder_warning, Toast.LENGTH_SHORT);
                        errorToast.show();
                    } else if (titleBlank) {
                        Toast errorToast = Toast.makeText(getApplicationContext(),
                                R.string.title_warning, Toast.LENGTH_SHORT);
                        errorToast.show();
                    } else {
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

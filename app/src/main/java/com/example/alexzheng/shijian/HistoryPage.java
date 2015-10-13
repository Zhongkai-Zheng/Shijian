package com.example.alexzheng.shijian;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HistoryPage extends AppCompatActivity {

    private Button sortButton;
    private ListView folderListView;

    private boolean isSortName; // true if sorted by name, false if sorted by most recent
    private ArrayList<String> folderNames;
    private ArrayAdapter<String> adapter;
    private GlobalClass g;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        g = GlobalClass.getInstance();
        folderNames = new ArrayList<String>();
        setFolderNames();
        isSortName = false;

        folderListView = (ListView) findViewById(R.id.folder_list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, folderNames);
        folderListView.setAdapter(adapter);

        folderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
                g.setTempFolderSelection(position);
                Intent goToNextActivity = new Intent(getApplicationContext(), TimesPage.class);
                startActivity(goToNextActivity);
            }
        });
        folderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int position, long arg3) {
                // called when user clicks and holds on folder name
                g.setTempFolderSelection(position);
                onCreateMainDialog(savedInstanceState).show();
                return true;
            }
        });

        sortButton = (Button) findViewById(R.id.sort_button);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSortName) {
                    // already sorted alphabetically, reorder by most recent
                    setFolderNames();
                    sortButton.setText(R.string.sort_name);
                } else {
                    // already sorted by most recent, reorder alphabetically
                    Collections.sort(folderNames);
                    sortButton.setText(R.string.sort_recent);
                }

                adapter.notifyDataSetChanged();
                isSortName = !isSortName;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history_page, menu);
        return true;
    }

    // Dialog code found in android api
    // Alert Dialog for deleting selected folder
    public Dialog onCreateMainDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.like_to_do)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onCreateDialog(savedInstanceState).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.setNeutralButton(R.string.edit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                g.setOnEditMode(2);
                Intent goToNextActivity = new Intent(getApplicationContext(), SaveTimePage.class);
                startActivity(goToNextActivity);
            }
        });
        return builder.create();
    }

    //Alert Dialog for long click on selected folder
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        g.removeFolder(g.getTempFolderSelection());
                        finish();
                        startActivity(getIntent());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    private void setFolderNames() {
        String[] names = g.getFolderNames();
        folderNames.clear();
        for(int i = 0; i < names.length; i++){
            folderNames.add(names[i]);
        }
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

package com.example.alexzheng.shijian;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimesPage extends ListActivity {

    private TextView timeTitleTextView;
    private Button sortButton;
    private ListView timesListView;

    private boolean isSortName; // true if sorted by name, false if sorted by most recent
    private ArrayList<String> timeNames;
    private ArrayAdapter<String> adapter;
    private GlobalClass g;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times_page);

        g = GlobalClass.getInstance();
        timeNames = new ArrayList<String>();
        setTimeNames();
        isSortName = false; // default sort by most recent

        // set title of page to Folder Name + "Times"
        timeTitleTextView = (TextView) findViewById(R.id.time_title_textView);
        timeTitleTextView.setText(g.getFolder(g.getTempFolderSelection()).getName() + " Times");

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, timeNames); // default adapter containing all timeNames
        setListAdapter(adapter);

        timesListView = getListView();
        timesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int position, long arg3) {
                g.setTempTimeSelection(position);
                onCreateMainDialog(savedInstanceState).show();
                return true;
            }
        });
        if(timeNames.size() == 0){
            onCreateDialogTwo(savedInstanceState).show();
        }

        sortButton = (Button) findViewById(R.id.sort_button);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSortName) {
                    // already sorted alphabetically, reorder by most recent
                    setTimeNames();
                    sortButton.setText(R.string.sort_name);
                } else {
                    // already sorted by most recent, reorder alphabetically
                    Collections.sort(timeNames);
                    sortButton.setText(R.string.sort_recent);
                }

                adapter.notifyDataSetChanged();
                isSortName = !isSortName;
            }
        });
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                g.getFolder(g.getTempFolderSelection()).removeTime(g.getTempTimeSelection());
                finish();
                startActivity(getIntent());
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public Dialog onCreateDialogTwo(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.empty_delete_confirmation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        g.removeFolder(g.getTempFolderSelection());
                        Intent goToNextActivity = new Intent(getApplicationContext(), HistoryPage.class);
                        startActivity(goToNextActivity);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public Dialog onCreateMainDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
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
                g.setOnEditMode(1);
                g.setTempStartTime(g.getFolder(g.getTempFolderSelection()).getTime(g.getTempTimeSelection()).getStartTime());
                g.setTempDuration(g.getFolder(g.getTempFolderSelection()).getTime(g.getTempTimeSelection()).getDuration());
                Intent goToNextActivity = new Intent(getApplicationContext(), SaveTimePage.class);
                startActivity(goToNextActivity);
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_times_page, menu);
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

    public void setTimeNames(){
        // sets String displays for the times
        ArrayList<Time> times = g.getFolderList().get(g.getTempFolderSelection()).getTimeArray();
        timeNames.clear();
        for(int i = 0; i < times.size(); i++){
            timeNames.add(times.get(i).getName() + ": " + times.get(i).getDurationString() + "\n" +
                    arrayToString(times.get(i).getStartTime()));
        }
    }

    public String arrayToString(int[] x){
        String result = "";
        for(int i = 0; i < 3; i++){
            // year.month.day
            result += Integer.toString(x[i]);
            result += ".";
        }
        result = result.substring(0, result.length()-1);
        result += " ";
        for(int i = 3; i < 6; i++){
            // hour:minute:second
            result += Integer.toString(x[i]);
            result += ":";
        }
        result = result.substring(0, result.length()-1);
        return result;
    }
}

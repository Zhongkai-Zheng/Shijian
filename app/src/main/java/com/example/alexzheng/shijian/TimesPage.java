package com.example.alexzheng.shijian;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TimesPage extends ListActivity {

    private ListView timesListView;
    private String[] timeNames;
    private ArrayAdapter<String> adapter;
    private GlobalClass g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times_page);

        g = GlobalClass.getInstance();
        setTimeNames();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, timeNames);
        setListAdapter(adapter);

        timesListView = getListView();
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
        ArrayList<Time> times = g.getFolderList().get(g.getTempFolderSelection()).getTimeArray();
        timeNames = new String[times.size()];
        for(int i = 0; i < times.size(); i++){
            timeNames[i] = times.get(i).getName() + ": " + times.get(i).getDurationString() + " start time: " +
                    arrayToString(times.get(i).getStartTime());
        }
    }

    public String arrayToString(int[] x){
        String result = "";
        for(int i = 0; i < 3; i++){
            result += Integer.toString(x[i]);
            result += ".";
        }
        result = result.substring(0, result.length()-1);
        result += " ";
        for(int i = 3; i < 6; i++){
            result += Integer.toString(x[i]);
            result += ":";
        }
        result = result.substring(0, result.length()-1);
        return result;
    }
}

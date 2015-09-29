package com.example.alexzheng.shijian;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryPage extends ListActivity {

    private ListView folderListView;
    private static String[] folderNames;
    private ArrayAdapter<String> adapter;
    private String[] names = {"a", "b", "c"};
    private GlobalClass g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);

        g = GlobalClass.getInstance();
        setFolderNames();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, folderNames);
        setListAdapter(adapter);

        folderListView = getListView();
        folderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
                g.setTempFolderSelection(position);
                Intent goToNextActivity = new Intent(getApplicationContext(), TimesPage.class);
                startActivity(goToNextActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history_page, menu);
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

    public void setFolderNames(){
        folderNames = new String[g.getFolderList().size()];
        for(int i = 0; i < g.getFolderList().size(); i++){
            folderNames[i] = g.getFolderList().get(i).getName();
            Log.d("name", GlobalClass.getInstance().getFolderList().get(i).getName());
        }
    }
}

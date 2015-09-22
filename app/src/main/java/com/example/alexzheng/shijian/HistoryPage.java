package com.example.alexzheng.shijian;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryPage extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView listOfFolders;
    private String[] folderNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);
        listOfFolders = (ListView)findViewById(R.id.list_of_folders);
        this.setFolderNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, folderNames);
        listOfFolders.setAdapter(adapter);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        GlobalClass.getInstance().setTempFolderSelection((int) id);
        Intent goToNextActivity = new Intent(getApplicationContext(), TimesPage.class);
        startActivity(goToNextActivity);
        // Do something when a list item is clicked
    }

    public void setFolderNames(){
        for(int i = 0; i < GlobalClass.getInstance().getFolderList().size(); i++){
            folderNames[i] = GlobalClass.getInstance().getFolderList().get(i).getName();
        }
    }
}

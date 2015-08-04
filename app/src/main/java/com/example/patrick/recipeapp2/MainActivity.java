package com.example.patrick.recipeapp2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    final String API_KEY = "1ceebd1c1ae69bbeaa5b6a18aa987aab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void test() {
        ArrayList<String> ingredients = new ArrayList<String>(
                Arrays.asList("apple", "banana", "corn")
        );

        // will contain raw JSON response as a string
        String searchJsonStr = null;
        try {
            final String SEARCH_BASE_URL = "http://food2fork.com/api/search?";
            final String KEY_PARAM = "key";
            final String QUERY_PARAM = "q";

            Uri builtUri = Uri.parse(SEARCH_BASE_URL).buildUpon()
                    .appendQueryParameter(KEY_PARAM, API_KEY)
                    .appendQueryParameter(QUERY_PARAM, "")
                    .build();

            URL url = new URL(builtUri.toString());


        } catch (IOException e) {

        } finally {

        }
    }
}

package com.example.patrick.recipeapp2;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    final String API_KEY = "1ceebd1c1ae69bbeaa5b6a18aa987aab";
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    ArrayList<String> mIngredients;
    String mSearchJsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIngredients = new ArrayList<String>(
                Arrays.asList("apple", "banana", "corn")
        );
        mSearchJsonStr = null;

        ((TextView)findViewById(R.id.hello_world)).setText("apple");
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
            test();
            if (mSearchJsonStr!=null) {
                ((TextView)findViewById(R.id.hello_world)).setText("banana");
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void test() {
        FetchSearchResultsTask searchResultsTask = new FetchSearchResultsTask();
        searchResultsTask.execute();
    }

    public class FetchSearchResultsTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            final String test = "comment";

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // will contain raw JSON response as a string
            mSearchJsonStr = null;
            try {
                final String SEARCH_BASE_URL = "http://food2fork.com/api/search?";
                final String KEY_PARAM = "key";
                final String QUERY_PARAM = "q";

                String ingredientsStr = "";
                for (String s : mIngredients) {
                    ingredientsStr += s + "%20";
                }

                Uri builtUri = Uri.parse(SEARCH_BASE_URL).buildUpon()
                        .appendQueryParameter(KEY_PARAM, API_KEY)
                        .appendQueryParameter(QUERY_PARAM, ingredientsStr)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                mSearchJsonStr = buffer.toString();
                Log.v(LOG_TAG, mSearchJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }
    }
}

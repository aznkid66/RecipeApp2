package com.example.patrick.recipeapp2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    final String API_KEY_F2F = "1ceebd1c1ae69bbeaa5b6a18aa987aab"; // food2fork.com API key
    final String API_KEY_ROU = "55c1d014f6fceb6f0700003f"; // recipeon.us API key
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    protected MainDbHelper db;
    List<String> mIngredientsList;
    MyAdapter mAdapt;

    protected MainActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MainDbHelper(this);
        mIngredientsList = db.getAllIngredients();
        mAdapt = new MyAdapter(this, R.layout.fragment_main, mIngredientsList);
        ListView listTask = (ListView) findViewById(R.id.ingredients_list);
        listTask.setAdapter(mAdapt);
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
        } else if (id == R.id.action_refresh) {
            test();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addIngredientNow(View view) {
        EditText t = (EditText) findViewById(R.id.editText1);
        String s = t.getText().toString();
        if (s.equalsIgnoreCase("")) {
            Toast.makeText(this, "Enter ingredient name first!",
            Toast.LENGTH_LONG);
        } else {
            db.addIngredient(s);
            Log.d(LOG_TAG, s + " added");
            t.setText("");
            mAdapt.add(s);
            mAdapt.notifyDataSetChanged();
        }
    }

    public void removeIngredientNow(View view) {
        TextView t = (TextView) findViewById(R.id.ingredient);
        String s = t.getText().toString();
        db.removeIngredient(s);
        Log.d(LOG_TAG, s + " removed");
        mAdapt.remove(s);
        mAdapt.notifyDataSetChanged();
    }

    public void executeSearch(View view) {
        FetchSearchResultsTask searchResultsTask = new FetchSearchResultsTask();
        searchResultsTask.execute();
    }

    public void test() {

    }

    public class FetchSearchResultsTask extends AsyncTask<Void, Void, Void> {

        String mSearchJsonStr;

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
                for (String s : mIngredientsList) {
                    ingredientsStr += s + ",";
                }

                Uri builtUri = Uri.parse(SEARCH_BASE_URL).buildUpon()
                        .appendQueryParameter(KEY_PARAM, API_KEY_F2F)
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

        @Override
        protected void onPostExecute(Void result) {
//            if (mSearchJsonStr!=null) {
//                ((TextView)findViewById(R.id.hello_world)).setText(mSearchJsonStr);
//            }
            Intent intent = new Intent(getActivity(), SearchResultsActivity.class)
                    .putExtra(Intent.EXTRA_TEXT, mSearchJsonStr);
                    //.putExtra(Intent.EXTRA_TEXT, "{ \"count\": 1, \"recipes\": [{ \"publisher\": \"Allrecipes.com\", \"social_rank\": 99.81007979198002, \"f2f_url\": \"http://food2fork.com/F2F/recipes/view/29159\", \"publisher_url\": \"http://allrecipes.com\", \"title\": \"Slow-Cooker Chicken Tortilla Soup\", \"source_url\": \"http://allrecipes.com/Recipe/Slow-Cooker-Chicken-Tortilla-Soup/Detail.aspx\", \"page\":1}] }");
            startActivity(intent);
        }
    }

    private class MyAdapter extends ArrayAdapter<String> {
        Context context;
        List<String> ingredientsList = new ArrayList<String>();
        int layoutResourceId;
        public MyAdapter(Context context, int layoutResourceId,
                         List<String> objects) {
            super(context, layoutResourceId, objects);
            this.layoutResourceId = layoutResourceId;
            this.ingredientsList = objects;
            this.context = context;
        }
        /**
         * This method will DEFINe what the view inside the list view will
         * finally look like Here we are going to code that the checkbox state
         * is the status of task and check box text is the task name
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txt = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_main,
                        parent, false);
                txt = (TextView) convertView.findViewById(R.id.ingredient); 
                convertView.setTag(txt);
//                txt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        CheckBox cb = (CheckBox) v;
//                        Task changeTask = (Task) cb.getTag();
//                        changeTask.setStatus(cb.isChecked() == true ? 1 : 0);
//                        db.updateTask(changeTask);
//                        Toast.makeText(
//                                getApplicationContext(),
//                                "Clicked on Checkbox: " + cb.getText() + " is "
//                                        + cb.isChecked(), Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
            } else {
                txt = (TextView) convertView.getTag();
            }
            String current = ingredientsList.get(position);
            txt.setText(current);
            txt.setTag(current);
            return convertView;
        }
    }
}

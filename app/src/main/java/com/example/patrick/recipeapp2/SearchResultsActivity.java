package com.example.patrick.recipeapp2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchResultsActivity extends ActionBarActivity {
    private static final String LOG_TAG = SearchResultsActivity.class.getSimpleName();

    public String mJsonStr;
    protected SearchResultsDbHelper db;
    private List<SearchResultItem> mResultsList;
    private SearchResultsAdapter mAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    //.add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        mJsonStr = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        Toast.makeText(this, mJsonStr,
                Toast.LENGTH_LONG).show();
        db = new SearchResultsDbHelper(this);
        addFromJsonToDb(mJsonStr, db);

        mResultsList = db.getAllResults();
        mAdapt = new SearchResultsAdapter(this, R.layout.fragment_detail, mResultsList);
        ListView listTask = (ListView) findViewById(R.id.results_list);
        listTask.setAdapter(mAdapt);
    }

    private void addFromJsonToDb(String json, SearchResultsDbHelper db) {
        final String F2F_COUNT = "count";
        final String F2F_RECIPES = "recipes";
        final String F2F_TITLE = "title";
        final String F2F_SRC_URL = "source_url";
        final String F2F_RANK = "social_rank";

        db.deleteAllResults();
        try {
            JSONObject resultsJson = new JSONObject(json);
            int count = resultsJson.getInt(F2F_COUNT);
            JSONArray recipes = resultsJson.getJSONArray(F2F_RECIPES);
            for (int i = 0; i<count; i++) {
                JSONObject recipe = recipes.getJSONObject(i);
                SearchResultItem item = new SearchResultItem(
                        recipe.getString(F2F_TITLE),
                        recipe.getDouble(F2F_RANK),
                        recipe.getString(F2F_SRC_URL)
                );
                db.addResult(item);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error putting JSON data into database " + e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
    protected void onResume() {
        super.onResume();
        // this needs to be changed for recipe app
//        String location = Utility.getPreferredLocation( this );
//        // update the location in our second pane using the fragment manager
//        if (location != null && !location.equals(mLocation)) {
//            ForecastFragment ff = (ForecastFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_forecast);
//            if ( null != ff ) {
//                ff.onLocationChanged();
//            }
//            DetailFragment df = (DetailFragment)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
//            if ( null != df ) {
//                df.onLocationChanged(location);
//            }
//            mLocation = location;
//        }
    }

//    @Override
    public void onItemSelected(Uri contentUri) {
        // this is probably not needed
//        if (mTwoPane) {
//            // In two-pane mode, show the detail view in this activity by
//            // adding or replacing the detail fragment using a
//            // fragment transaction.
//            Bundle args = new Bundle();
//            args.putParcelable(DetailFragment.DETAIL_URI, contentUri);
//
//            DetailFragment fragment = new DetailFragment();
//            fragment.setArguments(args);
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.weather_detail_container, fragment, DETAILFRAGMENT_TAG)
//                    .commit();
//        } else {
            Intent intent = new Intent(this, SearchResultsActivity.class)
                    .setData(contentUri);
            startActivity(intent);
        //}
    }

    private class SearchResultsAdapter extends ArrayAdapter<SearchResultItem> {
        Context context;
        List<SearchResultItem> resultsList = new ArrayList<SearchResultItem>();
        int layoutResourceId;
        public SearchResultsAdapter(Context context, int layoutResourceId,
                         List<SearchResultItem> objects) {
            super(context, layoutResourceId, objects);
            this.layoutResourceId = layoutResourceId;
            this.resultsList = objects;
            this.context = context;
        }
        /**
         * This method will DEFINe what the view inside the list view will
         * finally look like Here we are going to code that the checkbox state
         * is the status of task and check box text is the task name
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView name = null;
            TextView rating = null;
            TextView link = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_detail,
                        parent, false);
                name = (TextView) convertView.findViewById(R.id.search_result_fragment_name);
                rating = (TextView) convertView.findViewById(R.id.search_result_fragment_rating);
                link = (TextView) convertView.findViewById(R.id.search_result_fragment_link);
                convertView.setTag(name);
            } else {
                name = (TextView) convertView.getTag();
                rating = (TextView) convertView.findViewById(R.id.search_result_fragment_rating);
                link = (TextView) convertView.findViewById(R.id.search_result_fragment_link);
            }
            SearchResultItem current = resultsList.get(position);
            name.setText(current.NAME);
            rating.setText(String.format("%.1f", current.RATING));
            link.setText(current.LINK);
            return convertView;
        }
    }

}

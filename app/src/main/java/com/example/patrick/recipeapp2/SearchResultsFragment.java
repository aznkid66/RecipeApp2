package com.example.patrick.recipeapp2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchResultsFragment extends Fragment {

    private static final String LOG_TAG = SearchResultsFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";

    private ArrayAdapter<String> mRecipesAdapter;
    private ListView mListView;
    private int mPosition = ListView.INVALID_POSITION;
    private boolean mUseTodayLayout;

    private static final String FORECAST_SHARE_HASHTAG = " #RecipeApp";

//    private ShareActionProvider mShareActionProvider;
//    private String mForecast;
//    private Uri mUri;

    private static final int DETAIL_LOADER = 0;


    public SearchResultsFragment() {

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Change this code so that user click redirects to
        // recipe website
       if (id == R.id.action_refresh) {
//            FetchWeatherTask weatherTask = new FetchWeatherTask();
//            weatherTask.execute("94043");
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        mRecipesAdapter = new RecipesAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        mListView = (ListView) rootView.findViewById(R.id.listview_forecast);
        mListView.setAdapter(mRecipesAdapter);
        // this is for the clicked open page
        // We'll call our MainActivity
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                // CursorAdapter returns a cursor at the correct position for getItem(), or null
//                // if it cannot seek to that position.
//                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
//                if (cursor != null) {
//                    String locationSetting = Utility.getPreferredLocation(getActivity());
//                    ((Callback) getActivity())
//                            .onItemSelected(WeatherContract.WeatherEntry.buildWeatherLocationWithDate(
//                                    locationSetting, cursor.getLong(COL_WEATHER_DATE)
//                            ));
//                }
//                mPosition = position;
//            }
//        });

        return rootView;
    }
}
package com.example.patrick.recipeapp2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.patrick.recipeapp2.data.RecipeContract.RecipeEntry;

public class SearchResultsFragment extends Fragment {

    private static final String LOG_TAG = SearchResultsFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";

    private RecipesAdapter mRecipesAdapter;

    private ListView mListView;
    private int mPosition = ListView.INVALID_POSITION;
    private boolean mUseTodayLayout;

    private static final String FORECAST_SHARE_HASHTAG = " #RecipeApp";

//    private ShareActionProvider mShareActionProvider;
//    private String mForecast;
//    private Uri mUri;

    private static final int DETAIL_LOADER = 0;

    private static final String[] DETAIL_COLUMNS = {
            RecipeEntry.TABLE_NAME + "." +
            RecipeEntry.COLUMN_NAME,
            RecipeEntry.COLUMN_LINK
    };

    // These indices are tied to DETAIL_COLUMNS.  If DETAIL_COLUMNS changes, these
    // must change.
    public static final int COL_RECIPE_NAME = 0;
    public static final int COL_RECIPE_LINK = 1;


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
        inflater.inflate(R.menu.menu_detail, menu);
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

        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        mListView = (ListView) rootView.findViewById(R.id.listview_recipes);
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
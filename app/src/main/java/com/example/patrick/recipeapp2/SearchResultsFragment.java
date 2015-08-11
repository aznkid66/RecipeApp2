package com.example.patrick.recipeapp2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SearchResultsFragment extends Fragment {

    private static final String LOG_TAG = SearchResultsFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";

    private SearchResultsAdapter mSearchResultsAdapter;
    private List<SearchResultsItem> mResultsList;
    ArrayAdapter<String> tempAdapter;
    private ListView mListView;
    private int mPosition = ListView.INVALID_POSITION;
//    private boolean mUseTodayLayout;

    private static final String SELECTED_KEY = "selected_position";

    private static final int RECIPES_LOADER = 0;

    // These indices are tied to FORECAST_COLUMNS.  If FORECAST_COLUMNS changes, these
    // must change.
    static final int COL_RECIPE_ID = 0;
    static final int COL_RECIPE_NAME = 1;

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

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_search_results, menu);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Change this code so that user click redirects to
        // recipe website
//       if (id == R.id.action_bar) { //search
//            FetchSearchResultsTask tempTask = new FetchSearchResultsTask();
//            tempTask.execute("pizza");
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.

        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);


        mResultsList = new SearchResultsDbHelper(getActivity()).getAllResults();
        if (!mResultsList.isEmpty()) {
            ((TextView) rootView.findViewById(R.id.empty_results_list)).setText("");
        }
        mSearchResultsAdapter = new SearchResultsAdapter(getActivity(), R.layout.list_item_search_results, mResultsList);
        // Get a reference to the ListView, and attach this adapter to it.
        mListView = (ListView) rootView.findViewById(R.id.listview_search_results);
        mListView.setAdapter(mSearchResultsAdapter);

        // this is for the clicked open page
        // We'll call our MainActivity
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                mPosition = position;
            }
        });

        // If there's instance state, mine it for useful information.
        // The end-goal here is that the user never knows that turning their device sideways
        // does crazy lifecycle related things.  It should feel like some stuff stretched out,
        // or magically appeared to take advantage of room, but data or place in the app was never
        // actually *lost*.
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

//        mRecipesAdapter.setUseTodayLayout(mUseTodayLayout);


        return rootView;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        getLoaderManager().initLoader(RECIPES_LOADER, null, this);
//        super.onActivityCreated(savedInstanceState);
//    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }
    // sort by popularity of the recipe
//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        // This is called when a new Loader needs to be created.  This
//        // fragment only uses one loader, so we don't care about checking the id.
//
//        // To only show current and future dates, filter the query to return weather only for
//        // dates after or including today.
//
//        // Sort order:  Ascending, by date.
//        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
//
//        String locationSetting = Utility.getPreferredLocation(getActivity());
//        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
//                locationSetting, System.currentTimeMillis());
//
//        return new CursorLoader(getActivity(),
//                weatherForLocationUri,
//                FORECAST_COLUMNS,
//                null,
//                null,
//                sortOrder);
//    }

//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        mRecipesAdapter.swapCursor(data);
//        if (mPosition != ListView.INVALID_POSITION) {
//            // If we don't need to restart the loader, and there's a desired position to restore
//            // to, do so now.
//            mListView.smoothScrollToPosition(mPosition);
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        mRecipesAdapter.swapCursor(null);
//    }
//
////    public void setUseTodayLayout(boolean useTodayLayout) {
////        mUseTodayLayout = useTodayLayout;
////        if (mRecipesAdapter != null) {
////            mRecipesAdapter.setUseTodayLayout(mUseTodayLayout);
////        }
////    }




}
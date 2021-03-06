package com.example.patrick.recipeapp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class SearchResultsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new SearchResultsFragment())
                    .commit();
        }

        SearchResultsFragment searchResultsFragment =  ((SearchResultsFragment)getSupportFragmentManager()
                .findFragmentById(R.id.listview_search_results));


    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.searchresultsactivity, menu);
//        return true;
//    }

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


//    @Override
//    protected void onResume() {
//        super.onResume();
//        // this needs to be changed for recipe app
////        String location = Utility.getPreferredLocation( this );
////        // update the location in our second pane using the fragment manager
////        if (location != null && !location.equals(mLocation)) {
////            ForecastFragment ff = (ForecastFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_forecast);
////            if ( null != ff ) {
////                ff.onLocationChanged();
////            }
////            DetailFragment df = (DetailFragment)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
////            if ( null != df ) {
////                df.onLocationChanged(location);
////            }
////            mLocation = location;
//        }
//    }

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



}

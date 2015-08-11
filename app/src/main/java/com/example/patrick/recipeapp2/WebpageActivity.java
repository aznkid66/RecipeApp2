package com.example.patrick.recipeapp2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class WebpageActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new SearchResultsFragment())
                    .commit();
        }

        SearchResultsFragment searchResultsFragment =  ((SearchResultsFragment)getSupportFragmentManager()
                .findFragmentById(R.id.listview_search_results));


    }
}

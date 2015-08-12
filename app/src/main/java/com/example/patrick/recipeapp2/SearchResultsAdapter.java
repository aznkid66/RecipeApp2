package com.example.patrick.recipeapp2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link SearchResultsAdapter} exposes a list of recipes
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class SearchResultsAdapter extends ArrayAdapter<SearchResultsItem> {
        Context context;
        List<SearchResultsItem> resultsList = new ArrayList<SearchResultsItem>();
        int layoutResourceId;
        public SearchResultsAdapter(Context context, int layoutResourceId,
                                    List<SearchResultsItem> objects) {
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
                convertView = inflater.inflate(R.layout.list_item_search_results,
                        parent, false);
                name = (TextView) convertView.findViewById(R.id.list_item_search_results_name);
                rating = (TextView) convertView.findViewById(R.id.list_item_search_results_rating);
                convertView.setTag(name);
            } else {
                name = (TextView) convertView.getTag();
                rating = (TextView) convertView.findViewById(R.id.list_item_search_results_rating);
            }
            SearchResultsItem current = resultsList.get(position);
            name.setText(current.NAME);
            rating.setText(String.format("%.1f", current.RATING));
            return convertView;
        }

}

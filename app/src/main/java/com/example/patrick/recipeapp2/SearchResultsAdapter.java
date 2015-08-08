package com.example.patrick.recipeapp2;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * {@link SearchResultsAdapter} exposes a list of recipes
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class SearchResultsAdapter extends CursorAdapter {

    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder {
        public final TextView nameView;

        public ViewHolder(View view) {
            nameView = (TextView) view.findViewById(R.id.list_item_recipes);

        }
    }

    public SearchResultsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_recipes, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        // Read recipe name from cursor
        String name = cursor.getString(SearchResultsFragment.COL_RECIPE_NAME);
        // Find TextView and set weather forecast on it
        viewHolder.nameView.setText(name);


    }
}

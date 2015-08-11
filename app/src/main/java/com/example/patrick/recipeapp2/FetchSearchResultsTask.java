/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.patrick.recipeapp2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.patrick.recipeapp2.data.RecipeContract.RecipeEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class FetchSearchResultsTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchSearchResultsTask.class.getSimpleName();

    private final Context mContext;
    private final URL mUrl;

    public FetchSearchResultsTask(Context context, URL url) {
        mContext = context;
        mUrl = url;
    }

    private boolean DEBUG = true;

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private void getSearchResultsFromJson(String searchResultsJsonStr)
            throws JSONException {

        // Now we have a String representing the complete forecast in JSON Format.
        // Fortunately parsing is easy:  constructor takes the JSON string and converts it
        // into an Object hierarchy for us.

        // These are the names of the JSON objects that need to be extracted.
//
//        // Weather information.  Each day's forecast info is an element of the "list" array.
        final String F2F_LIST = "recipes";

        final String F2F_TITLE = "title";
        final String F2F_SRC_URL = "source_url";

        try {
            JSONObject searchResultsJson = new JSONObject(searchResultsJsonStr);
            JSONArray recipeArray = searchResultsJson.getJSONArray(F2F_LIST);

            // Insert the new weather information into the database
            Vector<ContentValues> cVVector = new Vector<ContentValues>(recipeArray.length());

            for(int i = 0; i < recipeArray.length(); i++) {
                // These are the values that will be collected.
                String name;
                String link;

                // Get the JSON object representing the day
                JSONObject recipe = recipeArray.getJSONObject(i);

                name = recipe.getString(F2F_TITLE);
                link = recipe.getString(F2F_SRC_URL);

                ContentValues recipeValues = new ContentValues();

                recipeValues.put(RecipeEntry.COLUMN_NAME, name);
                //recipeValues.put(RecipeEntry.COLUMN_LINK, link);

                cVVector.add(recipeValues);
            }

            int inserted = 0;
            // add to database
            if ( cVVector.size() > 0 ) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                inserted = mContext.getContentResolver().bulkInsert(RecipeEntry.CONTENT_URI, cvArray);
            }

            Log.d(LOG_TAG, "SearchTask Complete. " + inserted + " Inserted");

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // will contain raw JSON response as a string
        String searchJsonStr = null;
        try {
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) mUrl.openConnection();
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
            searchJsonStr = buffer.toString();
            Log.v(LOG_TAG, searchJsonStr);
            SearchResultsDbHelper db = new SearchResultsDbHelper(mContext);
            db.addFromJsonToDb(searchJsonStr);

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
        Intent intent = new Intent(mContext, SearchResultsActivity.class);
        mContext.startActivity(intent);
    }
}
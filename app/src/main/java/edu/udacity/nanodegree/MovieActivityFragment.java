package edu.udacity.nanodegree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieActivityFragment extends Fragment {

//    private boolean ascDescFlag = false;
    private MovieListAdapter movieAdapter;

    public MovieActivityFragment() { setHasOptionsMenu(true); }
    public GridView gridView;
    public ProgressBar progressBar;

    public static final String MOVIE_SORT_ORDER_KEY = "MOVIE_SORT_ORDER";


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        String ascDescSuffix = null;
//        if(ascDescFlag){
//            ascDescFlag = false;
//            ascDescSuffix = "asc";
//        }else{
//            ascDescFlag = true;
//            ascDescSuffix = "desc";
//        }

        TinyDB tinydb = new TinyDB(getActivity().getApplicationContext());

        switch(id){
            case R.id.action_sort_bypopularity:
                new MovieAPISyncTask().execute("popular");
                tinydb.putString(MOVIE_SORT_ORDER_KEY,"popular");
//                new MovieAPISyncTask().execute("popularity."+ascDescSuffix);
                break;
            case R.id.action_sort_byrating:
                new MovieAPISyncTask().execute("top_rated");
                tinydb.putString(MOVIE_SORT_ORDER_KEY,"top_rated");
                break;
            default:
//            case R.id.action_sort_byoriginaltitle:
//                new MovieAPISyncTask().execute("");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        // Get a reference to the ListView, and attach this adapter to it.
        gridView = (GridView) rootView.findViewById(R.id.gridview_movies);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        movieAdapter = new MovieListAdapter(getActivity(), new ArrayList<MovieDetails>());

//        ArrayAdapter<String> movieAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, months);

        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MovieDetails movie = movieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("MovieDetails", movie);
                startActivity(intent);
            }
        });

        //TODO Add Pagination/Infinite Scroll View

        return rootView;
    }

    private void updateMovies() {
        MovieAPISyncTask movieTask = new MovieAPISyncTask();

        TinyDB tinydb = new TinyDB(getActivity().getApplicationContext());
        movieTask.execute(tinydb.getString(MOVIE_SORT_ORDER_KEY));
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        updateMovies();
    }

    public class MovieAPISyncTask extends AsyncTask<String, Void, ArrayList<MovieDetails>> {

        private final String API_PARAM = "api_key";
//        private final String LANG_PARAM = "language";
//        private final String SORT_PARAM = "sort_by";
        private final String LOG_TAG = MovieAPISyncTask.class.getSimpleName();

        @Override
        protected ArrayList<MovieDetails> doInBackground(String... params) {
            return getMoviesList(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<MovieDetails> result) {
            movieAdapter.clear();
            if (!result.isEmpty()) {
                movieAdapter.notifyDataSetChanged();
                for(MovieDetails movieObj : result) {
                    movieAdapter.add(movieObj);
                }
            }
        }

        private ArrayList<MovieDetails> getMoviesList(String movieSuffix){

            ArrayList<MovieDetails> movieDetailsList = new ArrayList<MovieDetails>();
            String MOVIE_URL;

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            if(!movieSuffix.isEmpty()){
                MOVIE_URL = BuildConfig.MOVIE_BASE_URL+"movie/"+movieSuffix;
            }else{
                MOVIE_URL = BuildConfig.MOVIE_BASE_URL+"discover/movie";
            }

            try {

                Uri builtUri = Uri.parse(MOVIE_URL).buildUpon()
//                        .appendQueryParameter(SORT_PARAM, sortOrder)
                        .appendQueryParameter(API_PARAM, BuildConfig.MOVIE_API_KEY)
                        .build();

//                .appendQueryParameter(LANG_PARAM, Locale.getDefault().getLanguage())
//                        .appendQueryParameter(SORT_PARAM, sortOrder)
                URL url = new URL(builtUri.toString());


                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestProperty("Accept",JSON_FORMAT );
//                urlConnection.setRequestProperty("Content-Type",JSON_FORMAT);
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
                    showErrorDialog(getString(R.string.null_error));
                    return null;
                }

                int code = urlConnection.getResponseCode();

                //JSON Body Message
                JSONObject movieJSONObj = new JSONObject(buffer.toString());

                if(code == 200){
                    int mPage = movieJSONObj.getInt("page");
                    JSONArray resultsJSONArray = movieJSONObj.getJSONArray("results");

                    for(int i = 0; i < resultsJSONArray.length(); i++) {
                        JSONObject movieObj = resultsJSONArray.getJSONObject(i);

                        MovieDetails movieDetailsObj = new MovieDetails(movieObj.getLong("id"),
                                movieObj.getString("title"),
                                movieObj.getString("original_title"),
                                movieObj.getString("poster_path"),
                                movieObj.getDouble("popularity"),
                                movieObj.getString("overview"),
                                movieObj.getString("release_date"),
                                movieObj.getBoolean("video"),
                                movieObj.getDouble("vote_average"),
                                movieObj.getLong("vote_count"));

                        movieDetailsList.add(movieDetailsObj);
                    }

                    //Success
                    return movieDetailsList;

                }else{
                    //error status code
                    int statusCode = movieJSONObj.getInt("status_code");
                    //Show Error Dialog
                    showErrorDialog(movieJSONObj.getString("status_message").toString());

                }

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
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

            progressBar.setVisibility(View.GONE);

            return null;
        }

        private void showErrorDialog(String msg) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(msg)
                    .setTitle(R.string.error)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do nothings
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
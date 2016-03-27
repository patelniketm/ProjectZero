package edu.udacity.nanodegree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();
    private int movieID = 0;
    private final String POSTER_URL_PREFIX = "http://image.tmdb.org/t/p/w185";
    public ProgressBar progressBar;

    public MovieDetailActivityFragment(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("MovieDetails")) {
            MovieDetails movie = (MovieDetails) intent.getParcelableExtra("MovieDetails");

            getActivity().setTitle(movie.getMovieTitle());

            ((TextView) rootView.findViewById(R.id.movie_name))
                    .setText(movie.getMovieTitle());
            ((TextView) rootView.findViewById(R.id.movie_overview))
                    .setText(movie.getOverview());
//            ((TextView) rootView.findViewById(R.id.movie_trailer))
//                    .setText(movie.isVideo()+"");
            ((TextView) rootView.findViewById(R.id.movie_release_date))
                    .setText(getString(R.string.release_date)+": "+movie.getReleaseDate());
            ((TextView) rootView.findViewById(R.id.movie_vote_average))
                    .setText(getString(R.string.rating)+": "+movie.getVote_average()+"/10");

            Picasso.with(getContext())
                    .load(POSTER_URL_PREFIX + movie.getPosterURL())
                    .placeholder(R.drawable.movie_thumbnail)
                    .into((ImageView) rootView.findViewById(R.id.movie_thumbnail));
        }

        return rootView;
    }

    //TODO Merge this call into single service call
//    public class MovieDAPISyncTask extends AsyncTask<String, Void, ArrayList<MovieDetails>> {
//
//        private final String API_PARAM = "api_key";
//        private final String LOG_TAG = MovieDAPISyncTask.class.getSimpleName();
//
//        @Override
//        protected ArrayList<MovieDetails> doInBackground(String... params) {
//            return getMoviesList(params[0]);
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<MovieDetails> result) {
//            movieAdapter.clear();
//            if (!result.isEmpty()) {
//                movieAdapter.notifyDataSetChanged();
//                for(MovieDetails movieObj : result) {
//                    movieAdapter.add(movieObj);
//                }
//            }
//        }
//
//        private ArrayList<MovieDetails> getMoviesList(String movieSuffix){
//
//            ArrayList<MovieDetails> movieDetailsList = new ArrayList<MovieDetails>();
//            String MOVIE_URL;
//
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//
//            try {
//
//                if(!movieSuffix.isEmpty()){
//                    MOVIE_URL = MOVIE_BASE_URL+"movie/"+movieSuffix;
//                }else{
//                    MOVIE_URL = MOVIE_BASE_URL+"discover/movie";
//                }
//
//                Uri builtUri = Uri.parse(MOVIE_URL).buildUpon()
////                        .appendQueryParameter(SORT_PARAM, sortOrder)
//                        .appendQueryParameter(API_PARAM, BuildConfig.MOVIE_API_KEY)
//                        .build();
//
////                .appendQueryParameter(LANG_PARAM, Locale.getDefault().getLanguage())
////                        .appendQueryParameter(SORT_PARAM, sortOrder)
//                URL url = new URL(builtUri.toString());
//
//
//                // Create the request to OpenWeatherMap, and open the connection
//                urlConnection = (HttpURLConnection) url.openConnection();
////                urlConnection.setRequestProperty("Accept",JSON_FORMAT );
////                urlConnection.setRequestProperty("Content-Type",JSON_FORMAT);
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                // Read the input stream into a String
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                    // But it does make debugging a *lot* easier if you print out the completed
//                    // buffer for debugging.
//                    buffer.append(line + "\n");
//                }
//
//                if (buffer.length() == 0) {
//                    // Stream was empty.  No point in parsing.
//                    showErrorDialog(getString(R.string.null_error));
//                    return null;
//                }
//
//                int code = urlConnection.getResponseCode();
//
//                //JSON Body Message
//                JSONObject movieJSONObj = new JSONObject(buffer.toString());
//
//                if(code == 200){
//                    int mPage = movieJSONObj.getInt("page");
//                    JSONArray resultsJSONArray = movieJSONObj.getJSONArray("results");
//
//                    for(int i = 0; i < resultsJSONArray.length(); i++) {
//                        JSONObject movieObj = resultsJSONArray.getJSONObject(i);
//
//                        MovieDetails movieDetailsObj = new MovieDetails(movieObj.getLong("id"),
//                                movieObj.getString("title"),
//                                movieObj.getString("original_title"),
//                                movieObj.getString("poster_path"),
//                                movieObj.getDouble("popularity"),
//                                movieObj.getString("overview"),
//                                movieObj.getString("release_date"),
//                                movieObj.getBoolean("video"),
//                                movieObj.getDouble("vote_average"),
//                                movieObj.getLong("vote_count"));
//
//                        movieDetailsList.add(movieDetailsObj);
//                    }
//
//                    //Success
//                    return movieDetailsList;
//
//                }else{
//                    //error status code
//                    int statusCode = movieJSONObj.getInt("status_code");
//                    //Show Error Dialog
//                    showErrorDialog(movieJSONObj.getString("status_message").toString());
//
//                }
//
//            } catch (IOException e) {
//                Log.e(LOG_TAG, "Error ", e);
//                // If the code didn't successfully get the weather data, there's no point in attemping
//                // to parse it.
//                return null;
//            } catch (JSONException e) {
//                Log.e(LOG_TAG, "Error ", e);
//                // If the code didn't successfully get the weather data, there's no point in attemping
//                // to parse it.
//                return null;
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e(LOG_TAG, "Error closing stream", e);
//                    }
//                }
//            }
//
//            progressBar.setVisibility(View.GONE);
//
//            return null;
//        }
//
//        private void showErrorDialog(String msg) {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage(msg)
//                    .setTitle(R.string.error)
//                    .setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            //do nothings
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//        }
//
//    }

}

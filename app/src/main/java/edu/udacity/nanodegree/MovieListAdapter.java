package edu.udacity.nanodegree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieListAdapter extends ArrayAdapter<MovieDetails> {
    private final String POSTER_URL_PREFIX = "http://image.tmdb.org/t/p/w185";

    public MovieListAdapter(Context context, ArrayList<MovieDetails> moviesDetails) {
        super(context, 0, moviesDetails);
    }

    /**
     * Provides a view for an AdapterView - GridView
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_movie_details, parent, false);
        }

        // Get the data item for this position
        MovieDetails movie = getItem(position);

        ImageView imgMoviePoster = (ImageView) convertView.findViewById(R.id.list_movie_thumbnail);

        if (!movie.getPosterURL().isEmpty()) {
            Picasso.with(getContext())
                    .load(POSTER_URL_PREFIX + movie.getPosterURL())
                    .placeholder(R.drawable.movie_thumbnail)
                    .fit().centerCrop()
                    .into(imgMoviePoster);
        }

//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        // Return the completed view to render on screen
        return convertView;
    }
}
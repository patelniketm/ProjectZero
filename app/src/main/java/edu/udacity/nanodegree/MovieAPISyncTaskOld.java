//package edu.udacity.nanodegree;
//
//
//import android.os.AsyncTask;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Locale;
//
//import info.movito.themoviedbapi.TmdbApi;
//import info.movito.themoviedbapi.TmdbMovies;
//import info.movito.themoviedbapi.model.MovieDb;
//import info.movito.themoviedbapi.model.core.MovieResultsPage;
//
//public class MovieAPISyncTaskOld extends AsyncTask<String, Void, List<MovieDetails>>{
//
//    @Override
//    protected MovieDetails doInBackground(String... params) {
//        return getMoviesList(params[0]);
//    }
//
//    private MovieDetails getMoviesList(String sortOrder) {
//
//        MovieResultsPage movieResultsPage = null;
//        List<MovieDetails> movieDetailsList = null;
//
//        try {
////            TmdbMovies movies = new TmdbApi(API_KEY).getMovies();
////            int mPage = 1;
////            switch (sortOrder) {
////                default:
////                case "ByPopularity":
////                    movieResultsPage = movies.getPopularMovieList(Locale.getDefault().getLanguage(), mPage);
////                    break;
////                case "ByRating":
////                    movieResultsPage = movies.getTopRatedMovies(Locale.getDefault().getLanguage(), mPage);
////                    break;
////                case "ByUpcoming":
////                    movieResultsPage = movies.getUpcoming(Locale.getDefault().getLanguage(), mPage);
////                    break;
////            }
////            TheMovieDbApi theMovieDbApi = new TheMovieDbApi("142401d1f5943320a91a1a58351d4aee");
////            TmdbResultsList tmdbResultsList = theMovieDbApi.getPopularMovieList(Locale.getDefault().getDisplayLanguage(),1);
//
////            Iterator itr = movieResultsPage.iterator();
////            while(itr.hasNext()) {
////                Object element = itr.next();
////                movieDetailsList.add(
////                        ((MovieDb) element).getId(),
////                        ((MovieDb) element).getTitle(),
////                        ((MovieDb) element).originalTitle,
////                        ((MovieDb) element).posterPath,
////                        ((MovieDb) element).popularity,
////                        ((MovieDb) element).overview,
////                        ((MovieDb) element).releaseDate,
////                        (boolean) ((MovieDb) element).videos,
////                        (float) ((MovieDb) element).voteAverage,
////                        (long) ((MovieDb) element).voteCount);
////
////                System.out.print(element + " ");
////            }
//
//
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//
//        return movieResultsPage;
//
////        SpotifyApi api = new SpotifyApi();
////        SpotifyService service = api.getService();
////        final ArtistsPager artistsPager = service.searchArtists(searchArtistText);
////        for (Artist artist : artistsPager.artists.items) {
////            int imagesListSize = artist.images.size();
////            String thumbnailUrl = imagesListSize > 0 ? artist.images.get(imagesListSize - 1).url : null;
////            artistDetailsList.add(new ArtistDetails(artist.name,
////                    artist.id, thumbnailUrl));
////        }
////        return artistDetailsList;
//
//    }
//
////    @Override
////    protected void onPostExecute(MovieResultsPage artistDetailsResultsList) {
////        if (!artistDetailsResultsList.isEmpty()) {
////            artistsResultsAdapter.notifyDataSetChanged();
////        } else {
////            displayToast(R.string.no_artist_found);
////        }
////    }
//}

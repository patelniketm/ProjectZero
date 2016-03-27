package edu.udacity.nanodegree;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDetails implements Parcelable {

    private long movieID;
    private String movieTitle;
    private String movieOrigTitle;
    private String posterURL;
    private String overview;
    private double popularity;
    private String releaseDate;
    private boolean video;
    private double vote_average;
    private long vote_count;

    public MovieDetails(long movieID, String movieTitle, String movieOrigTitle, String posterURL,
                        double popularity, String overview, String releaseDate, Boolean video, double vote_average, long vote_count) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.movieOrigTitle = movieOrigTitle;
        this.posterURL = posterURL;
        this.popularity = popularity;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.video = video;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
    }

    public long getMovieID() {
        return movieID;
    }

    public void setMovieID(long movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieOrigTitle() {
        return movieOrigTitle;
    }

    public void setMovieOrigTitle(String movieOrigTitle) {
        this.movieOrigTitle = movieOrigTitle;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public long getVote_count() {
        return vote_count;
    }

    public void setVote_count(long vote_count) {
        this.vote_count = vote_count;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.movieID);
        dest.writeString(this.movieTitle);
        dest.writeString(this.movieOrigTitle);
        dest.writeString(this.posterURL);
        dest.writeString(this.overview);
        dest.writeDouble(this.popularity);
        dest.writeString(this.releaseDate);
        dest.writeByte(video ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.vote_average);
        dest.writeLong(this.vote_count);
    }

    protected MovieDetails(Parcel in) {
        this.movieID = in.readLong();
        this.movieTitle = in.readString();
        this.movieOrigTitle = in.readString();
        this.posterURL = in.readString();
        this.overview = in.readString();
        this.popularity = in.readDouble();
        this.releaseDate = in.readString();
        this.video = in.readByte() != 0;
        this.vote_average = in.readDouble();
        this.vote_count = in.readLong();
    }

    public static final Parcelable.Creator<MovieDetails> CREATOR = new Parcelable.Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel source) {
            return new MovieDetails(source);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };
}

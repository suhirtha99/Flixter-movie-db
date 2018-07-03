package codepath.com.flixter.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Movie {

    //values from API
    String title;
    String overview;
    String posterPath;
    String backdropPath;
    Double voteAverage;

    //no arg, empty constructor - requirement for parcelable class
    public Movie() {}

    //constructor
    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
        voteAverage = object.getDouble("vote_average");

    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }
}

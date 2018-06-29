package codepath.com.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {
    String imageBaseUrl;


    String defaultPosterSize;

    //the default backdrop size
    String defaultBackdropSize;

    public Config(JSONObject object) throws JSONException{
        JSONObject images = object.getJSONObject("images");
        //get base image URL
        imageBaseUrl = images.getString("base_url");
        //get the poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        defaultPosterSize = posterSizeOptions.optString(3, "w342");
        //parse the backdrop sizes and use the option at index 1 or w780 as a fallback
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        defaultBackdropSize = backdropSizeOptions.optString(1, "w780");

    }

    //helper method for creating urls
    public String getImageUrl(String size, String path) {
        return String.format("%s%s%s", imageBaseUrl, size, path);
    }

    public String getDefaultBackdropSize() {
        return defaultBackdropSize;
    }

    public String getImageBaseUrl() {

        return imageBaseUrl;
    }

    public String getDefaultPosterSize() {
        return defaultPosterSize;
    }

}

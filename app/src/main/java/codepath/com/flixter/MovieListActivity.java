package codepath.com.flixter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codepath.com.flixter.models.Config;
import codepath.com.flixter.models.Movie;
import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    //constants
    //the base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";

    //the parameter name for the API key
    public final static String API_KEY_PARAM = "api_key";

    //API key constant
    public final String KEY = "92d4ee1705dcea2efe472dc8253bf047"; //TODO move this

    //tag for logging from this activity
    public final static String TAG = "MovieListActivity";


    //instance variables
    AsyncHttpClient client;

    ArrayList<Movie> movies;
    RecyclerView rvMovies;
    MovieAdapter adapter;
    Config config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        //comment
        //initialize the client
        client = new AsyncHttpClient();

        movies = new ArrayList<>();

        //initialize the adapter -- movies arraylist cannot be reinitialized after this point
        adapter = new MovieAdapter(movies);

        //resolve the recycle view and connect a layout manager after this point
        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

        //get the configuration on app creation
        getConfiguration();


    }

    //get the list of 'Now Playing' movies
    private void getNowPlaying() {

        //create the complete URL
        String url = API_BASE_URL + "/movie/now_playing";
        //set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, KEY);
        //execute a GET request using client, expecting JSON object response
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //load the results into movies list
                try {
                    JSONArray results = response.getJSONArray("results");

                    //iterate through results to create Movie objects
                    for (int i = 0; i < results.length(); i++) {
                        Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);

                        //notify adapter that a row has been added
                        adapter.notifyItemInserted(movies.size() - 1);

                    }

                    Log.i(TAG, String.format("Loaded %s movies", movies.size()));

                } catch (JSONException e) {
                    logError("Failed to parse NowPlayingMovies", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable t) {
                logError("Failed to get data from NowPlaying", t, true);
            }
        });

    }


    private void getConfiguration() {
        //create the complete URL
        String url = API_BASE_URL + "/configuration";
        //set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, KEY);
        //execute a GET request using client, expecting JSON object response
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    config = new Config(response);

                    Log.i(TAG, String.format("Loaded configuration with ImageBaseUrl: %s \n and PosterSize: %s",
                            config.getImageBaseUrl(),
                            config.getDefaultPosterSize()));
                    adapter.setConfig(config);
                    getNowPlaying();

                } catch (JSONException e) {
                    logError("Failed to parse configuration", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed configuration access", throwable, true);
            }
        });



    }

    //handle errors, logs and alerts user
    private void logError(String message, Throwable t, boolean alertUser) {
        Log.e(TAG, message, t); //log the error

        if (alertUser) {
            //shoe a long toast with the error message
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

    }
}

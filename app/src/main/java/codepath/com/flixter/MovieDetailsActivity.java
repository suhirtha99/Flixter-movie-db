package codepath.com.flixter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.parceler.Parcels;

import codepath.com.flixter.models.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    //movie to display
    Movie movie;

    //view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPoster;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar)  findViewById(R.id.rbVoteAverage);
        ivPoster = (ImageView) findViewById(R.id.ivPoster);

        //unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        //set the movie title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ivPoster.setImageDrawable(movie.);

        //vote average is 0-10, convert to 0-5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage/2.0f);


    }
}

package codepath.com.flixter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;

import codepath.com.flixter.models.Config;
import codepath.com.flixter.models.GlideApp;
import codepath.com.flixter.models.Movie;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    //list of movies
    ArrayList<Movie>  movies;

    Config config;

    Context context;

    public void setConfig(Config config) {
        this.config = config;
    }

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    //creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get the context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        //return a new ViewHolder
        return new ViewHolder (movieView);

    }


    //binds an inflated view to a new item
    public void onBindViewHolder(ViewHolder holder, int i) {
        //get the movie data at the specified position
        Movie movie = movies.get(i);
        //populate the view with the movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        //determine the current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;



        // build url for poster image
        String imageUrl = config.getImageUrl(config.getDefaultPosterSize(), movie.getPosterPath());

        if (isPortrait) {
            imageUrl = config.getImageUrl(config.getDefaultPosterSize(), movie.getPosterPath());
        } else {
            //load the backdrop image
            imageUrl = config.getImageUrl(config.getDefaultBackdropSize(), movie.getBackdropPath());
        }

        int placeholderId = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? holder.ivPosterImage : holder.ivBackdropImage;


        //load image using Glide
        GlideApp.with(context)
                .load(imageUrl)
                .transform(new RoundedCornersTransformation(30, 0))
                .placeholder(placeholderId)
                .error(placeholderId)
                .into(imageView);
    }

    //returns the total number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivBackdropImage;

        @Override
        public void onClick(View view) {
            //get item position
            int position = getAdapterPosition();

            //make sure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                //get the movie at the position, this doesn't work if the class is static
                Movie movie = movies.get(position);
                //create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                //serialize the ovie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                //show the activity
                context.startActivity(intent);
            }
        }




        public ViewHolder(View itemView) {
            super(itemView);
            // lookup view objects by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterView);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivBackdropImage = (ImageView) itemView.findViewById(R.id.ivBackdropimage);
            itemView.setOnClickListener(this);



        }
    }


}

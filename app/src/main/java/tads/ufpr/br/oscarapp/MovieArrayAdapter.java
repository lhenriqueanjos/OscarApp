package tads.ufpr.br.oscarapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import tads.ufpr.br.oscarapp.model.Movie;

/**
 * Created by starlord on 23/06/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private int resource;
    private Context context;

    private List<Movie> movies;

    public MovieArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Movie> movies) {
        super(context, resource, movies);

        this.resource = resource;
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);
        TextView movieNameTxt = (TextView) rowView.findViewById(R.id.movieNameTxt);
        TextView movieCategoryTxt = (TextView) rowView.findViewById(R.id.movieCategoryTxt);
        ImageView movieCoverImg = (ImageView) rowView.findViewById(R.id.movieCoverImg);

        movieNameTxt.setText(movies.get(position).getName());
        movieCategoryTxt.setText(movies.get(position).getCategory());
        new DownloadImageTask(movieCoverImg).execute(movies.get(position).getImageUrl());

        return rowView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

package tads.ufpr.br.oscarapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import tads.ufpr.br.oscarapp.model.Movie;
import tads.ufpr.br.oscarapp.model.User;

/**
 * Created by lucas on 25/06/2017.
 */

public class MovieDetailActivity extends AppCompatActivity {

    Movie     movie;
    TextView  movieName;
    ImageView movieImage;
    TextView  movieCategory;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        movie = (Movie)intent.getSerializableExtra("movie");
        user = (User)intent.getSerializableExtra("user");

        movieName = (TextView) findViewById(R.id.textViewName);
        movieImage = (ImageView) findViewById(R.id.movieImg);
        movieCategory = (TextView) findViewById(R.id.textViewCategory);

        movieName.setText(movie.getName());
        movieCategory.setText(movie.getCategory());

        new DownloadImageTask((ImageView) findViewById(R.id.movieImg))
                .execute(movie.getImageUrl());


    }

    public void onClickMovieDetail(View view){

        try {
            user.setMovieId(movie.getId());
            Intent it = new Intent(getBaseContext(), ShortcutsActivity.class);
            it.putExtra("movieVote", (Serializable) user);
            it.putExtra("movieConfirm", (Serializable) movie);
            startActivity(it);
        }catch (Exception e)
        {
            e.getMessage();
        }
    }
}

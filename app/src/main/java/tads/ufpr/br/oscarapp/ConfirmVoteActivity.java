package tads.ufpr.br.oscarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import java.util.List;
import java.util.Map;

import tads.ufpr.br.oscarapp.model.Director;
import tads.ufpr.br.oscarapp.model.Movie;
import tads.ufpr.br.oscarapp.model.User;

/**
 * Created by lucas on 25/06/2017.
 */

public class ConfirmVoteActivity extends AppCompatActivity {

    Director director;
    TextView directorName;
    Movie movie;
    TextView movieName;
    User user;
    int dirId;
    int movieId;
    private List<Director> directors;
    private List<Movie> movies;

    private static String url = "http://192.168.1.3:8080/vote";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Intent intent = getIntent();
//        director = (Director) intent.getSerializableExtra("director");
//        movie = (Movie) intent.getSerializableExtra("movie");
        user = (User)intent.getSerializableExtra("user");
        directors = (List<Director>) intent.getSerializableExtra("directors");
        movies = (List<Movie>) intent.getSerializableExtra("movies");
        dirId = (int) (long) user.getDirectorId();
        dirId = dirId -1 ;
        movieId = (int) (long) user.getMovieId();
        movieId = movieId -1;
        director = directors.get(dirId);
        movie = movies.get(movieId);
        directorName = (TextView) findViewById(R.id.textViewDirectorName);
        directorName.setText(director.getName());
        movieName = (TextView) findViewById(R.id.textViewMovieName);
        movieName.setText(movie.getName());

    }

    public void onClickConfirm(View view){
        Map<String, String> voteMap = new HashMap<>();
        voteMap.put("email", user.getEmail());
        voteMap.put("movieId", user.getMovieId().toString());
        voteMap.put("directorId", user.getDirectorId().toString());
        RequestQueue queue = Volley.newRequestQueue(ConfirmVoteActivity.this);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT,url, new JSONObject(voteMap),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.d("Response:%n %s", response.getString("email"));
                            Log.d("Response:%n %s", response.getString("movieId"));
                            Log.d("Response:%n %s", response.getString("directorId"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: ", error.getMessage());
            }

        });
        queue.add(req);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {

                    Toast.makeText(ConfirmVoteActivity.this, "Voto computado com sucesso!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getBaseContext(), ShortcutsActivity.class);
                    intent.putExtra("userVote", (Serializable) user);
                    startActivity(intent);
            }
        });
    }


}

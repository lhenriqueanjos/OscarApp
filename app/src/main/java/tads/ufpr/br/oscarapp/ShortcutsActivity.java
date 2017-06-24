package tads.ufpr.br.oscarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tads.ufpr.br.oscarapp.model.Movie;

public class ShortcutsActivity extends AppCompatActivity {

    private static final String TAG = "SHORTCUTS_ACTIVITY";

    ProgressBar progressBar;
    LinearLayout content;
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcuts);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String moviesUrl = "https://dl.dropboxusercontent.com/u/40990541/filme.json";
        String directorsUrl = "https://dl.dropboxusercontent.com/u/40990541/diretor.json";

        // Request a string response from the provided URL.
        StringRequest moviesRequest = new StringRequest(Request.Method.GET, moviesUrl,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJson = new JSONObject(response);
                    JSONArray moviesJson = responseJson.getJSONArray("filme");
                    int i;

                    movies = new ArrayList<>(moviesJson.length());

                    for (i = 0; i < moviesJson.length(); i++) {
                        JSONObject movieJson = (JSONObject) moviesJson.get(i);
                        Movie movie = new Movie();
                        movie.setName(movieJson.getString("nome"));
                        movie.setCategory(movieJson.getString("genero"));
                        movie.setId(movieJson.getLong("id"));
                        movie.setImageUrl(movieJson.getString("foto"));

                        movies.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "erro na request de filmes");
            }
        });

        StringRequest directorsRequest = new StringRequest(Request.Method.GET, directorsUrl,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response.toString());
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "erro na request de diretores");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(moviesRequest);
        queue.add(directorsRequest);

        progressBar = (ProgressBar) findViewById(R.id.shortcutsProgressBar);
        content = (LinearLayout) findViewById(R.id.shortcutsContent);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                progressBar.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listMoviesItem:
                Log.d(TAG, "listar filmes");
                Intent intent = new Intent(this, Movies1Activity.class);
                intent.putExtra("movies", (Serializable) movies);
                startActivity(intent);
                return true;
            case R.id.listDirectorsItem:
                Log.d(TAG, "listar diretores");
                return true;
            case R.id.confirmItem:
                Log.d(TAG, "confirmar voto");
                return true;
            case R.id.exiItem:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

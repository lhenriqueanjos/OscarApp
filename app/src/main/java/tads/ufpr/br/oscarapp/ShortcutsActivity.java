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
import android.widget.Toast;

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

import tads.ufpr.br.oscarapp.model.Director;
import tads.ufpr.br.oscarapp.model.Movie;
import tads.ufpr.br.oscarapp.model.User;

public class ShortcutsActivity extends AppCompatActivity {

    private static final String TAG = "SHORTCUTS_ACTIVITY";

    ProgressBar progressBar;
    LinearLayout content;
    List<Movie> movies;
    List<Director> directors;
    TextView msgLogin;
    User user;
    Movie movie;
    Director director;
    String text;
    String text2;
    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcuts);
        Intent i = getIntent();
        RequestQueue queue = Volley.newRequestQueue(this);
        String directorsUrl = "https://dl.dropboxusercontent.com/u/40990541/diretor.json";

        movie = (Movie) i.getSerializableExtra("movieConfirm");
        director = (Director) i.getSerializableExtra("directorConfirm");

        if (user == null) {
            user = (User) i.getSerializableExtra("userVote");
            Toast.makeText(this, "veio da confirmação", Toast.LENGTH_SHORT).show();
        }

        if (user == null) {
            user = (User) i.getSerializableExtra("movieVote");
            Toast.makeText(this, "veio da movie", Toast.LENGTH_SHORT).show();
        }

        if (user == null) {
            user = (User) i.getSerializableExtra("directorVote");
            Toast.makeText(this, "veio da director", Toast.LENGTH_SHORT).show();
        }

        if (user == null) {
            user = (User) i.getSerializableExtra("User");
            Toast.makeText(this, "veio da login", Toast.LENGTH_SHORT).show();
        }

        text = "Bem vindo(a)! ";
        text2 = "";
        if (user.getDirectorId() == null && user.getMovieId() == null)
            text2 = "Você ainda não votou";
        msgLogin = (TextView) findViewById(R.id.VoteTxt);
        msgLogin.setText(text + user.getUserName() + System.getProperty("line.separator") + text2);

        // Instantiate the RequestQueue.
        final String moviesUrl = "https://dl.dropboxusercontent.com/u/40990541/filme.json";

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
        queue.add(moviesRequest);


        StringRequest directorsRequest = new StringRequest(Request.Method.GET, directorsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            JSONArray directorsJson = responseJson.getJSONArray("diretor");
                            int i;

                            directors = new ArrayList<>(directorsJson.length());

                            for (i = 0; i < directorsJson.length(); i++) {
                                JSONObject directorJson = (JSONObject) directorsJson.get(i);
                                Director director = new Director();
                                director.setName(directorJson.getString("nome"));
                                director.setId(directorJson.getLong("id"));

                                directors.add(director);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "erro na request de diretores");
            }
        });

        // Add the request to the RequestQueue.

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
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(2).setEnabled(false);

        if (user.getDirectorId() != null && user.getDirectorId() != 0
                && user.getMovieId() != null && user.getMovieId() != 0)
            menu.getItem(2).setEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listMoviesItem:
                Log.d(TAG, "listar filmes");
                Intent moviesIntent = new Intent(this, MoviesActivity.class);
                moviesIntent.putExtra("movies", (Serializable) movies);
                moviesIntent.putExtra("user", (Serializable) user);

                startActivity(moviesIntent);
                return true;
            case R.id.listDirectorsItem:
                Intent directorsIntent = new Intent(this, DirectorsActivity.class);
                directorsIntent.putExtra("directors", (Serializable) directors);
                directorsIntent.putExtra("user", (Serializable) user);
                startActivity(directorsIntent);
                return true;
            case R.id.confirmItem:
                Log.d(TAG, "confirmar voto");
                Intent confirm = new Intent(this, ConfirmVoteActivity.class);
                confirm.putExtra("directors", (Serializable) directors);
                confirm.putExtra("user", (Serializable) user);
                confirm.putExtra("movies", (Serializable) movies);
                startActivity(confirm);
                return true;
            case R.id.exiItem:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

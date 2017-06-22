package tads.ufpr.br.oscarapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ShortcutsActivity extends AppCompatActivity {

    private static final String TAG = "SHORTCUTS_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcuts);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String moviesUrl = "https://dl.dropboxusercontent.com/u/40990541/filme.json";

        // Request a string response from the provided URL.
        StringRequest moviesRequest = new StringRequest(Request.Method.GET, moviesUrl,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "erro na request de filmes");
                }
            });

        // Add the request to the RequestQueue.
        queue.add(moviesRequest );
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

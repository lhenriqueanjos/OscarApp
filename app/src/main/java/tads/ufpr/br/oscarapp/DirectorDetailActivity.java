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

import tads.ufpr.br.oscarapp.model.Director;
import tads.ufpr.br.oscarapp.model.Movie;
import tads.ufpr.br.oscarapp.model.User;

/**
 * Created by lucas on 25/06/2017.
 */

public class DirectorDetailActivity extends AppCompatActivity {

    Director  director;
    TextView  directorName;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_detail);
        Intent intent = getIntent();
        director = (Director) intent.getSerializableExtra("director");
        user = (User)intent.getSerializableExtra("user");

        directorName = (TextView) findViewById(R.id.textViewDirectorName);

        directorName.setText(director.getName());

    }

    public void onClickDirectorDetail(View view){

        try {
            user.setDirectorId(director.getId());
            Intent it = new Intent(getBaseContext(), ShortcutsActivity.class);
            it.putExtra("directorVote", (Serializable) user);
            it.putExtra("directorConfirm", (Serializable) director);
            startActivity(it);
        }catch (Exception e)
        {
            e.getMessage();
        }
    }
}

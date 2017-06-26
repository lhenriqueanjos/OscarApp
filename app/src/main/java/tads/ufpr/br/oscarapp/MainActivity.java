package tads.ufpr.br.oscarapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;

import tads.ufpr.br.oscarapp.model.Director;
import tads.ufpr.br.oscarapp.model.Movie;
import tads.ufpr.br.oscarapp.model.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";
    private static String url = "http://192.168.25.237:8080/login";
    private EditText emailTxt;
    private EditText passwordTxt;
    User user_login = new User();
    User user_db = new User();
    String loginOK;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTxt = (EditText) findViewById(R.id.emailTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        loginOK = null;
        progressDialog = new ProgressDialog(this);


    }

    public void onClickLogin(View view) {
        Log.d(TAG, "Login");
        progressDialog.setMessage("Logando, aguarde");
        progressDialog.show();
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("email", emailTxt.getText().toString());
        loginMap.put("password", passwordTxt.getText().toString());
        user_login.setPassword(passwordTxt.getText().toString());
        user_login.setEmail(emailTxt.getText().toString());

        /*LoginTask task = new LoginTask();
        task.execute(loginMap); */

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(loginMap),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.d("Response:%n %s", response.getString("email"));
                            Log.d("Response:%n %s", response.getString("password"));
                            if(response.getString("email").equals(user_login.getEmail())
                                    && response.getString("password").equals(user_login.getPassword()))
                            {
                                loginOK = "1";
                                long id = 0;
                                user_login.setUserName(response.getString("name"));
                                user_login.setDirectorId(response.getLong("directorId"));
                                user_login.setMovieId(response.getLong("movieId"));
                                if (user_login.getMovieId() == null)
                                    user_login.setMovieId(id);
                                if (user_login.getDirectorId() == null)
                                    user_login.setDirectorId(id);
                            }
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
                progressDialog.hide();
                if (loginOK != "1") {
                    Toast.makeText(MainActivity.this, "Login/Senha inválidos", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginOK = null;

                    Intent intent = new Intent(getBaseContext(), ShortcutsActivity.class);
                    intent.putExtra("User", user_login);
                    startActivity(intent);
                }

            }
        });


    }
}

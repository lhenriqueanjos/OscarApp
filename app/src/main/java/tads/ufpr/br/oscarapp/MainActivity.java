package tads.ufpr.br.oscarapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import java.util.Map;

import tads.ufpr.br.oscarapp.model.Director;
import tads.ufpr.br.oscarapp.model.Movie;
import tads.ufpr.br.oscarapp.model.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";
    private static String url = "http://192.168.1.6:3000/users";
    private EditText emailTxt;
    private EditText passwordTxt;
    User user_login = new User();
    User user_db = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTxt = (EditText) findViewById(R.id.emailTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);


    }

    public void onClickLogin(View view) {
        Log.d(TAG, "Login");

        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("email", emailTxt.getText().toString());
        loginMap.put("password", passwordTxt.getText().toString());
        user_login.setPassword(passwordTxt.getText().toString());
        user_login.setEmail(emailTxt.getText().toString());

        LoginTask task = new LoginTask();
        task.execute(loginMap);
    }

    private class LoginTask extends AsyncTask<Map<String, String>, Void, User> {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String email;
        String password;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected User doInBackground(Map<String, String>... maps) {

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            Log.d(TAG, maps[0].keySet().toString());
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(maps[0]),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("Response:%n %s", response.getString("email"));
                                Log.d("Response:%n %s", response.getString("password"));
                               email = response.getString("email");
                                password = response.getString("password");
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

// add the request object to the queue to be executed
            //ApplicationController.getInstance().addToRequestQueue(req);
            user_db.setEmail(email);
            user_db.setPassword(password);
            requestQueue.add(req);
            return  user_db;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (!(user_login.equals(user))) {
                Toast.makeText(MainActivity.this, "Login/Senha inválidos", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, user_db.getEmail(), Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(getBaseContext(), ShortcutsActivity.class);
                startActivity(intent);
            }
        }
// Usar o body retornado pelo servidor...
    }
}

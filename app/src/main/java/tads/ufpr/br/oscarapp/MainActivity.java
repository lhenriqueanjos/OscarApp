package tads.ufpr.br.oscarapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import tads.ufpr.br.oscarapp.model.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";

    private EditText emailTxt;
    private EditText passwordTxt;

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

        LoginTask task = new LoginTask();
        task.execute(loginMap);
    }

    private class LoginTask extends AsyncTask<Map<String, String>, Void, User> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected User doInBackground(Map<String, String>... maps) {
            Log.d(TAG, maps[0].keySet().toString());
            return null;
        }
    }
}

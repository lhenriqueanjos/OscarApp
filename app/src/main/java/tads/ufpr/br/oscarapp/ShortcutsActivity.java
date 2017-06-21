package tads.ufpr.br.oscarapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ShortcutsActivity extends AppCompatActivity {

    private static final String TAG = "SHORTCUTS_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcuts);
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

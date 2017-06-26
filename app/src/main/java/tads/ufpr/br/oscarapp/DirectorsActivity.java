package tads.ufpr.br.oscarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import tads.ufpr.br.oscarapp.model.Director;
import tads.ufpr.br.oscarapp.model.Movie;
import tads.ufpr.br.oscarapp.model.User;

public class DirectorsActivity extends AppCompatActivity {

    private ListView listView;
    private List<Director> directors;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directors);

        Intent intent = getIntent();
        directors = (List<Director>) intent.getSerializableExtra("directors");
        user = (User) intent.getSerializableExtra("user");

        listView = (ListView) findViewById(R.id.directorsListView);
        listView.setAdapter(new DirectorArrayAdapter(this, R.layout.directors_list, directors));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get selected items
                Director selectedValue = (Director) listView.getAdapter().getItem(i);
                Intent intent = new Intent(getBaseContext(), DirectorDetailActivity.class);
                intent.putExtra("director", (Serializable) selectedValue);
                intent.putExtra("user", (Serializable) user);
                startActivity(intent);

            }
        });
    }
}

package tads.ufpr.br.oscarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import tads.ufpr.br.oscarapp.model.Movie;

import static android.widget.Toast.makeText;

public class MoviesActivity extends AppCompatActivity {

    private ListView listView;
    private List<Movie> movies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movies);

        Intent intent = getIntent();
        movies = (List<Movie>) intent.getSerializableExtra("movies");

        listView = (ListView) findViewById(R.id.moviesListView);
        listView.setAdapter(new MovieArrayAdapter(this, R.layout.movies_list, movies));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get selected items
                Movie selectedValue = (Movie) listView.getAdapter().getItem(i);
                Toast.makeText(getParent(), selectedValue.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

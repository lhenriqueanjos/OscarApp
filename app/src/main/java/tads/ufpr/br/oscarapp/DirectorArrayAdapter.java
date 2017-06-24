package tads.ufpr.br.oscarapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tads.ufpr.br.oscarapp.model.Director;
import tads.ufpr.br.oscarapp.model.Movie;

/**
 * Created by starlord on 23/06/17.
 */

public class DirectorArrayAdapter extends ArrayAdapter<Director> {

    private int resource;
    private Context context;

    private List<Director> directors;

    public DirectorArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Director> directors) {
        super(context, resource, directors);

        this.resource = resource;
        this.context = context;
        this.directors = directors;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);
        TextView directorNameTxt = (TextView) rowView.findViewById(R.id.directorNameTxt);

        directorNameTxt.setText(directors.get(position).getName());

        return rowView;
    }
}

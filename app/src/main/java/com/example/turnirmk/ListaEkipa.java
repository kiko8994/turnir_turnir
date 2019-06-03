package com.example.turnirmk;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListaEkipa extends ArrayAdapter<Ekipe> {
    private Activity context;
    private List<Ekipe> Lista_Ekipa;

    public ListaEkipa(Activity context,List<Ekipe> Lista_Ekipa){
        super(context, R.layout.list_view_ekipe, Lista_Ekipa);
        this.context = context;
        this.Lista_Ekipa = Lista_Ekipa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewEkipe = inflater.inflate(R.layout.list_view_ekipe, null , true);

        TextView textEkipe = (TextView) listViewEkipe.findViewById(R.id.imeEkipe);

        Ekipe ekipe = Lista_Ekipa.get(position);
        textEkipe.setText(ekipe.getImeEkipe());

        return listViewEkipe;
    }
}

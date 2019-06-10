package com.example.turnirmk;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListaUtakmica extends ArrayAdapter<Utakmice> {
    private Activity context;
    private List<Utakmice> Lista_Utakmica;

    public ListaUtakmica(Activity context, List<Utakmice> Lista_Utakmica){
        super(context, R.layout.list_view_ekipe, Lista_Utakmica);
        this.context = context;
        this.Lista_Utakmica = Lista_Utakmica;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewEkipe = inflater.inflate(R.layout.list_view_ekipe, null , true);

        TextView textEkipe = (TextView) listViewEkipe.findViewById(R.id.imeEkipe);

        Utakmice tekme = Lista_Utakmica.get(position);
        textEkipe.setText(tekme.getTekma()+"  "+tekme.getRezultat());

        return listViewEkipe;
    }
}

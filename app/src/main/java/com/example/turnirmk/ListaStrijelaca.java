package com.example.turnirmk;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListaStrijelaca extends ArrayAdapter<Strijelac> {
    private Activity context;
    private List<Strijelac> Lista_Strijelaca;

    public ListaStrijelaca(Activity context, List<Strijelac> Lista_Strijelaca){
        super(context, R.layout.list_view_strijelci, Lista_Strijelaca);
        this.context = context;
        this.Lista_Strijelaca = Lista_Strijelaca;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewStrijelaca = inflater.inflate(R.layout.list_view_strijelci, null , true);

        TextView textStrijelca = (TextView) listViewStrijelaca.findViewById(R.id.textImeStrijelca);
        TextView brojGolova = (TextView) listViewStrijelaca.findViewById(R.id.textImeStrijelca);

        Strijelac strijelac = Lista_Strijelaca.get(position);
        textStrijelca.setText(strijelac.getImeStrijelca()+"("+strijelac.getMomcad()+")");
        brojGolova.setText(strijelac.getBrojGolova());

        return listViewStrijelaca;
    }
}

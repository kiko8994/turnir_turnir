package com.example.turnirmk;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class ListaStrijelacaMultiple extends ArrayAdapter<Strijelac> {
    private Activity context;
    private List<Strijelac> Lista_Strijelaca;

    public ListaStrijelacaMultiple(Activity context, List<Strijelac> Lista_Strijelaca){
        super(context, R.layout.list_view_strijelci_multiple, Lista_Strijelaca);
        this.context = context;
        this.Lista_Strijelaca = Lista_Strijelaca;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewStrijelaca = inflater.inflate(R.layout.list_view_strijelci_multiple, null , true);

        CheckedTextView textStrijelcaMultiple = (CheckedTextView) listViewStrijelaca.findViewById(R.id.textStrijelacMultiple);


        Strijelac strijelac = Lista_Strijelaca.get(position);
        textStrijelcaMultiple.setText(strijelac.getImeStrijelca());


        return listViewStrijelaca;
    }
}

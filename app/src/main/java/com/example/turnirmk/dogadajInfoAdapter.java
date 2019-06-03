package com.example.turnirmk;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class dogadajInfoAdapter extends ArrayAdapter<Dogadaj> {
    private Activity context;
    private List<Dogadaj> ListaDogadaja;

    public dogadajInfoAdapter(Activity context,List<Dogadaj> ListaDogadaja){
        super(context, R.layout.list_view,ListaDogadaja);
        this.context = context;
        this.ListaDogadaja=ListaDogadaja;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.list_view, null , true);

        TextView imeDogadaja = (TextView) listView.findViewById(R.id.imeDogadaja);
        TextView kontakt = (TextView) listView.findViewById(R.id.kontakt);
        TextView datum = (TextView) listView.findViewById(R.id.lokacija);

        Dogadaj dogadaj = ListaDogadaja.get(position);
        imeDogadaja.setText(dogadaj.getImeDogadaja());
        kontakt.setText(dogadaj.getKontakt());
        datum.setText(dogadaj.getDatum());

        return listView;
    }
}

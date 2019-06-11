package com.example.turnirmk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tab1Fragment extends Fragment {
    private static String TAG = "Tab1Fragment";
    private static final String ARG_PARAM1 = "param1";
    public static ArrayAdapter<String> listaAdapterStrijelac2;
    public static ArrayAdapter<String> listaAdapterStrijelac1;
    private String mParam1;
    List<String> strijelciPrveEkipe;
    List<String> strijelciDrugeEkipe;
    List<String> listStrijelciPrveEkipe;
    List<String> listStrijelciDrugeEkipe;
    List<String> strijelciSvi;
    DatabaseReference databasePronadiStrijelce = FirebaseDatabase.getInstance().getReference("strijelci");
    public static int golovi;

    public static Tab1Fragment newInstance(String param1) {
        Tab1Fragment fragment = new Tab1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    DatabaseReference utakmiceDatabase;
    List<Utakmice> tekme;
    ListView listViewUtakmice;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        tekme = new ArrayList<>();

        mParam1 = getArguments().getString(ARG_PARAM1);
        utakmiceDatabase = FirebaseDatabase.getInstance().getReference("utakmice").child(mParam1);
        listViewUtakmice = (ListView)view.findViewById(R.id.listTekme1);
        utakmiceDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tekme.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    Utakmice utakmice = snap.getValue(Utakmice.class);
                    tekme.add(utakmice);
                }

                ListaUtakmica listaAdapter = new ListaUtakmica(getActivity(),tekme);
                listViewUtakmice.setAdapter(listaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewUtakmice.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Utakmice utakmice = tekme.get(i);
                showDialog(utakmice.getTekma(),utakmice.getId(),utakmice.getRezultat());

                return false;
            }
        });

        return view;
    }

    private void showDialog(final String utakmica, final String utakmiceId, final String rezultat){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog, null);

        final EditText rezEkipaPrva = (EditText)dialogView.findViewById(R.id.rezEkipa1);
        final EditText rezEkipaDruga = (EditText)dialogView.findViewById(R.id.rezEkipa2);
        final TextView prvaEkipa = (TextView)dialogView.findViewById(R.id.ekipa1);
        final TextView drugaEkipa = (TextView)dialogView.findViewById(R.id.ekipa2);
        final Button spremiPromjene = (Button)dialogView.findViewById(R.id.spremi);
        final ListView listaPrveEkipe = (ListView) dialogView.findViewById(R.id.listEkipa1);
        final ListView listaDrugeEkipe = (ListView) dialogView.findViewById(R.id.listEkipa2);
        strijelciPrveEkipe = new ArrayList<>();
        strijelciDrugeEkipe = new ArrayList<>();
        listStrijelciPrveEkipe = new ArrayList<>();
        listStrijelciDrugeEkipe = new ArrayList<>();
        strijelciSvi = new ArrayList<>();
        final String[] temp=utakmica.split(" ");
        final String[] ekipe=temp[2].split("-");

        if(!rezultat.isEmpty()){
            final String[] rezTemp = rezultat.split(":");
            rezEkipaPrva.setText(rezTemp[0]);
            rezEkipaDruga.setText(rezTemp[1]);
        }

        prvaEkipa.setText(ekipe[0]);
        drugaEkipa.setText(ekipe[1]);



        databasePronadiStrijelce.child(mParam1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strijelciPrveEkipe.clear();
                strijelciDrugeEkipe.clear();
                strijelciSvi.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Strijelac strijelac = data.getValue(Strijelac.class);
                    if(ekipe[0].equals(strijelac.getMomcad())) {
                        strijelciPrveEkipe.add(strijelac.getImeStrijelca()+" "+strijelac.getMomcad()+" "+strijelac.getBrojGolova()+" "+strijelac.getIdStrijelca());
                    }
                    if(ekipe[1].equals(strijelac.getMomcad())) {
                        strijelciDrugeEkipe.add(strijelac.getImeStrijelca()+" "+strijelac.getMomcad()+" "+strijelac.getBrojGolova()+" "+strijelac.getIdStrijelca());
                    }
                }
                listaAdapterStrijelac1 = new ArrayAdapter(
                        getContext(),
                        android.R.layout.simple_list_item_multiple_choice,
                        strijelciPrveEkipe
                );
                listaPrveEkipe.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listaPrveEkipe.setAdapter(listaAdapterStrijelac1);
                listaAdapterStrijelac2 = new ArrayAdapter(
                        getContext(),
                        android.R.layout.simple_list_item_multiple_choice,
                        strijelciDrugeEkipe
                );
                listaDrugeEkipe.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listaDrugeEkipe.setAdapter(listaAdapterStrijelac2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        spremiPromjene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rezEkipeJedan = rezEkipaPrva.getText().toString().trim();
                String rezEkipeDva = rezEkipaDruga.getText().toString().trim();

                int itemCount1 = listaPrveEkipe.getCount();
                SparseBooleanArray checkedItemPositions1 = listaPrveEkipe.getCheckedItemPositions();
                for (int i = itemCount1-1; i >= 0; i--) {
                    if (checkedItemPositions1.get(i)) {
                        strijelciSvi.add(strijelciPrveEkipe.get(i));
                    }
                }
                checkedItemPositions1.clear();

                int itemCount2 = listaDrugeEkipe.getCount();
                SparseBooleanArray checkedItemPositions2 = listaDrugeEkipe.getCheckedItemPositions();
                for (int i = itemCount2-1; i >= 0; i--) {
                    if (checkedItemPositions2.get(i)) {
                        strijelciSvi.add(strijelciDrugeEkipe.get(i));

                    }
                }
                checkedItemPositions2.clear();

                if(TextUtils.isEmpty(rezEkipeJedan)){
                    rezEkipaPrva.setError("Rezultat potrebno unijeti!");
                    return;
                }
                if(TextUtils.isEmpty(rezEkipeDva)){
                    rezEkipaDruga.setError("Rezultat potrebno unijeti!");
                    return;
                }
                String reza = rezEkipeJedan+":"+rezEkipeDva;
                updateUtakmice(utakmiceId, utakmica, reza);
                alertDialog.dismiss();
            }
        });

    }
    private boolean updateUtakmice(String id, String name, String rezultat){
        DatabaseReference databaseDohvatiTekmu = FirebaseDatabase.getInstance().getReference("utakmice").child(mParam1).child(id);
        DatabaseReference databaseDohvatiStrijelca = FirebaseDatabase.getInstance().getReference("strijelci").child(mParam1);
        Utakmice utakmice = new Utakmice(id, name, rezultat);
        databaseDohvatiTekmu.setValue(utakmice);

        for(int i=0;i<strijelciSvi.size();i++){
            String[] ime_gol_id=strijelciSvi.get(i).split(" ");
            String imeStrijelca = ime_gol_id[0];
            String momcadStrijelca = ime_gol_id[1];
            golovi = Integer.parseInt(ime_gol_id[2]);
            String idStrijelca = ime_gol_id[3];


            Strijelac strijelac = new Strijelac(imeStrijelca,momcadStrijelca,idStrijelca,golovi+1);
            databaseDohvatiStrijelca.child(idStrijelca).setValue(strijelac);

        }

        Toast.makeText(getActivity(),"Promijenjen rezultat i strijelci!",Toast.LENGTH_LONG).show();
        return true;
    }
}

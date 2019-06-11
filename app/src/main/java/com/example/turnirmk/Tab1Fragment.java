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
    public static ListaStrijelacaMultiple listaAdapterStrijelac2;
    public static ListaStrijelacaMultiple listaAdapterStrijelac1;
    private String mParam1;
    List<Strijelac> strijelciPrveEkipe;
    List<Strijelac> strijelciDrugeEkipe;
    List<Strijelac> strijelciSvi;
    List<String> sveGrupe;
    List<String> GrupaB;
    public static String ekipaKojojDajemoBodove="";
    public static String ekipaKojojDajemoBodove1="";
    public static String ekipaKojojDajemoBodove2="";
    DatabaseReference databasePronadiStrijelce = FirebaseDatabase.getInstance().getReference("strijelci");
    DatabaseReference databasePronadiGrupe = FirebaseDatabase.getInstance().getReference("grupe");
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
        strijelciSvi = new ArrayList<>();
        sveGrupe = new ArrayList<>();
        GrupaB = new ArrayList<>();
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
                        strijelciPrveEkipe.add(strijelac);
                    }
                    if(ekipe[1].equals(strijelac.getMomcad())) {
                        strijelciDrugeEkipe.add(strijelac);
                    }
                }
                listaAdapterStrijelac1 = new ListaStrijelacaMultiple(getActivity(),strijelciPrveEkipe);
                listaPrveEkipe.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listaPrveEkipe.setAdapter(listaAdapterStrijelac1);
                listaAdapterStrijelac2 = new ListaStrijelacaMultiple(getActivity(),strijelciDrugeEkipe);
                listaDrugeEkipe.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listaDrugeEkipe.setAdapter(listaAdapterStrijelac2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databasePronadiGrupe.child(mParam1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sveGrupe.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    Skupine skupine = data.getValue(Skupine.class);
                    sveGrupe.add(skupine.getEkipaJedan()+"-"+skupine.getEkipaDva()+"-"+skupine.getEkipaTri()+"-"+skupine.getEkipaCetiri()+"-"+skupine.getIdGrupe());
                }

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
        DatabaseReference databaseDohvatiGrupu = FirebaseDatabase.getInstance().getReference("grupe").child(mParam1);

        //Utakmice
        Utakmice utakmice = new Utakmice(id, name, rezultat);
        databaseDohvatiTekmu.setValue(utakmice);
        String[] rezaPolje=rezultat.split(":");
        String[] ime=name.split(" ");
        String[] ekipe=ime[2].split("-");
        if(Integer.parseInt(rezaPolje[0])>Integer.parseInt(rezaPolje[1])){
            ekipaKojojDajemoBodove = ekipe[0];
        }
        else if(Integer.parseInt(rezaPolje[0])<Integer.parseInt(rezaPolje[1])){
            ekipaKojojDajemoBodove = ekipe[1];
        }
        else {
            ekipaKojojDajemoBodove1 = ekipe[0];
            ekipaKojojDajemoBodove2 = ekipe[1];
        }
        //GRUPE
        for (int j=0;j<sveGrupe.size();j++) {
            String[] momcadi=sveGrupe.get(j).split("-");
            String id_grupe = momcadi[4];
            String[] momcad1 = momcadi[0].split(" ");
            String[] momcad2 = momcadi[1].split(" ");
            String[] momcad3 = momcadi[2].split(" ");
            String[] momcad4 = momcadi[3].split(" ");
            if(ekipaKojojDajemoBodove1.equals(momcad1[0]) && ekipaKojojDajemoBodove2.equals(momcad2[0])){
                int a=Integer.parseInt(momcad1[1])+1;
                int b=Integer.parseInt(momcad2[1])+1;
                String mijenjamo_bodove1 = momcad1[0]+" "+a;
                String mijenjamo_bodove2 = momcad2[0]+" "+b;
                Skupine skupine = new Skupine(mijenjamo_bodove1,mijenjamo_bodove2,momcadi[2],momcadi[3],id_grupe);
                databaseDohvatiGrupu.child(id_grupe).setValue(skupine);
            }
            if(ekipaKojojDajemoBodove1.equals(momcad1[0]) && ekipaKojojDajemoBodove2.equals(momcad3[0])){
                int a=Integer.parseInt(momcad1[1])+1;
                int b=Integer.parseInt(momcad3[1])+1;
                String mijenjamo_bodove1 = momcad1[0]+" "+a;
                String mijenjamo_bodove2 = momcad3[0]+" "+b;
                Skupine skupine = new Skupine(mijenjamo_bodove1,momcadi[1],mijenjamo_bodove2,momcadi[3],id_grupe);
                databaseDohvatiGrupu.child(id_grupe).setValue(skupine);
            }
            if(ekipaKojojDajemoBodove1.equals(momcad1[0]) && ekipaKojojDajemoBodove2.equals(momcad4[0])){
                int a=Integer.parseInt(momcad1[1])+1;
                int b=Integer.parseInt(momcad4[1])+1;
                String mijenjamo_bodove1 = momcad1[0]+" "+a;
                String mijenjamo_bodove2 = momcad4[0]+" "+b;
                Skupine skupine = new Skupine(mijenjamo_bodove1,momcadi[1],momcadi[2],mijenjamo_bodove2,id_grupe);
                databaseDohvatiGrupu.child(id_grupe).setValue(skupine);
            }
            if(ekipaKojojDajemoBodove1.equals(momcad2[0]) && ekipaKojojDajemoBodove2.equals(momcad3[0])){
                int a=Integer.parseInt(momcad2[1])+1;
                int b=Integer.parseInt(momcad3[1])+1;
                String mijenjamo_bodove1 = momcad2[0]+" "+a;
                String mijenjamo_bodove2 = momcad3[0]+" "+b;
                Skupine skupine = new Skupine(momcadi[0],mijenjamo_bodove1,mijenjamo_bodove2,momcadi[3],id_grupe);
                databaseDohvatiGrupu.child(id_grupe).setValue(skupine);
            }
            if(ekipaKojojDajemoBodove1.equals(momcad2[0]) && ekipaKojojDajemoBodove2.equals(momcad4[0])){
                int a=Integer.parseInt(momcad2[1])+1;
                int b=Integer.parseInt(momcad4[1])+1;
                String mijenjamo_bodove1 = momcad2[0]+" "+a;
                String mijenjamo_bodove2 = momcad4[0]+" "+b;
                Skupine skupine = new Skupine(momcadi[0],mijenjamo_bodove1,momcadi[2],mijenjamo_bodove2,id_grupe);
                databaseDohvatiGrupu.child(id_grupe).setValue(skupine);
            }
            if(ekipaKojojDajemoBodove1.equals(momcad3[0]) && ekipaKojojDajemoBodove2.equals(momcad4[0])){
                int a=Integer.parseInt(momcad3[1])+1;
                int b=Integer.parseInt(momcad4[1])+1;
                String mijenjamo_bodove1 = momcad3[0]+" "+a;
                String mijenjamo_bodove2 = momcad4[0]+" "+b;
                Skupine skupine = new Skupine(momcadi[0],momcadi[1],mijenjamo_bodove1,mijenjamo_bodove2,id_grupe);
                databaseDohvatiGrupu.child(id_grupe).setValue(skupine);
            }
            if(ekipaKojojDajemoBodove.equals(momcad1[0])){
                int a=Integer.parseInt(momcad1[1])+3;
                String mijenjamo_bodove = momcad1[0]+" "+a;
                Skupine skupine = new Skupine(mijenjamo_bodove,momcadi[1],momcadi[2],momcadi[3],id_grupe);
                databaseDohvatiGrupu.child(id_grupe).setValue(skupine);
            }
            if(ekipaKojojDajemoBodove.equals(momcad2[0])){
                int a=Integer.parseInt(momcad2[1])+3;
                String mijenjamo_bodove = momcad2[0]+" "+a;
                Skupine skupine = new Skupine(momcadi[0],mijenjamo_bodove,momcadi[2],momcadi[3],id_grupe);
                databaseDohvatiGrupu.child(id_grupe).setValue(skupine);
            }
            if(ekipaKojojDajemoBodove.equals(momcad3[0])){
                int a=Integer.parseInt(momcad3[1])+3;
                String mijenjamo_bodove = momcad3[0]+" "+a;
                Skupine skupine = new Skupine(momcadi[0],momcadi[1],mijenjamo_bodove,momcadi[3],id_grupe);
                databaseDohvatiGrupu.child(id_grupe).setValue(skupine);
            }
            if(ekipaKojojDajemoBodove.equals(momcad4[0])){
                int a=Integer.parseInt(momcad4[1])+3;
                String mijenjamo_bodove = momcad4[0]+" "+a;
                Skupine skupine = new Skupine(momcadi[0],momcadi[1],momcadi[2],mijenjamo_bodove,id_grupe);
                databaseDohvatiGrupu.child(id_grupe).setValue(skupine);
            }

        }
        ekipaKojojDajemoBodove="";
        ekipaKojojDajemoBodove1="";
        ekipaKojojDajemoBodove2="";
        //STRIJELCI

        for(int i=0;i<strijelciSvi.size();i++){
            Strijelac strijelac = new Strijelac(strijelciSvi.get(i).getImeStrijelca(),
                    strijelciSvi.get(i).getMomcad(),
                    strijelciSvi.get(i).getIdStrijelca(),
                    strijelciSvi.get(i).getBrojGolova()+1);
            databaseDohvatiStrijelca.child(strijelciSvi.get(i).getIdStrijelca()).setValue(strijelac);

        }

        Toast.makeText(getActivity(),"Promijenjen rezultat i strijelci!",Toast.LENGTH_LONG).show();
        return true;
    }
}
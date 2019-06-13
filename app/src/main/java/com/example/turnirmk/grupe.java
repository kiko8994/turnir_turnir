package com.example.turnirmk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class grupe extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    TextView textView4;
    TextView textView3;
    TextView textUtakmice;

    List<Ekipe> ekipe;
    List<Ekipe> A;
    List<Ekipe> B;
    List<Ekipe> C;
    List<Ekipe> D;
    List<String> tekme = new ArrayList<>();

    ListView grupaA;
    ListView grupaB;
    ListView grupaC;
    ListView grupaD;
    ListView utakmice;

    FloatingActionButton dodajTekme;

    public static int sat, minuta;

    DatabaseReference grupeReference;
    DatabaseReference utakmiceRef;
    DatabaseReference grupeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupe);

        textView = (TextView) findViewById(R.id.textView2);
        textView2 = (TextView) findViewById(R.id.textView3);
        textView3 = (TextView) findViewById(R.id.textView4);
        textView4 = (TextView) findViewById(R.id.textView5);
        textUtakmice = (TextView) findViewById(R.id.textUtakmice);

        grupaA =(ListView) findViewById(R.id.grupaA);
        grupaB =(ListView) findViewById(R.id.grupaB);
        grupaC =(ListView) findViewById(R.id.grupaC);
        grupaD =(ListView) findViewById(R.id.grupaD);
        utakmice = (ListView) findViewById(R.id.listUtakmice);
        dodajTekme = (FloatingActionButton) findViewById(R.id.dodajUtakmice);

        String tmp = getIntent().getStringExtra("GRUPE");
        String dat = getIntent().getStringExtra("DATE");

        final String t = dat;
        String[] part = t.split(" ");

        sat = Integer.parseInt(part[3]);
        minuta = Integer.parseInt(part[4]);


        ekipe = new ArrayList<>();
        grupeReference = FirebaseDatabase.getInstance().getReference("ekipe").child(tmp);
        utakmiceRef = FirebaseDatabase.getInstance().getReference("utakmice").child(tmp);
        grupeRef = FirebaseDatabase.getInstance().getReference("grupe").child(tmp);

        dodajTekme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

    }
    @Override
    protected void onStart() {

        super.onStart();
        grupeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot grupeSnapshot : dataSnapshot.getChildren()){
                    Ekipe ekipa = grupeSnapshot.getValue(Ekipe.class);
                    ekipe.add(ekipa);
                }
                int a = ekipe.size();
                Collections.shuffle(ekipe);

                if (a==8){
                    textView.setText("GRUPA A");
                    A = ekipe.subList(0,4);
                    ListaEkipa listaEkipaAdapter = new ListaEkipa(grupe.this, A);
                    grupaA.setAdapter(listaEkipaAdapter);
                    B = ekipe.subList(4, 8);
                    textView2.setText("GRUPA B");
                    ListaEkipa listaEkipaAdapter2 = new ListaEkipa(grupe.this, B);
                    grupaB.setAdapter(listaEkipaAdapter2);
                    int j;
                    for (int i=0; i<3; i++) {
                        j=0;
                        if(i==1){j=2;}
                        if(i==2){j=3;}
                        while (j<4) {
                            if(j!=i) {
                                if(minuta>=10) {
                                    tekme.add(sat + ":" + minuta + "  " + A.get(i).getImeEkipe() + "-" + A.get(j).getImeEkipe());
                                }
                                else{
                                    tekme.add(sat + ":0" + minuta + "  " + A.get(i).getImeEkipe() + "-" + A.get(j).getImeEkipe());
                                }
                                minuta+=30;
                                if(minuta>=60){
                                    sat+=1;
                                    minuta-=60;
                                }

                                if(sat>=24){
                                    sat=0;
                                }
                            }
                            j=j+1;
                        }
                    }
                    for (int i=0; i<3; i++) {
                        j=0;
                        if(i==1){j=2;}
                        if(i==2){j=3;}
                        while (j<4) {
                            if(j!=i) {
                                if(minuta>=10) {
                                    tekme.add(sat + ":" + minuta + "  " + B.get(i).getImeEkipe() + "-" + B.get(j).getImeEkipe());
                                }
                                else{
                                    tekme.add(sat + ":0" + minuta + "  " + B.get(i).getImeEkipe() + "-" + B.get(j).getImeEkipe());
                                }
                                minuta+=30;
                                if(minuta>=60){
                                    sat+=1;
                                    minuta-=60;
                                }

                                if(sat>=24){
                                    sat=0;
                                }
                            }
                            j=j+1;
                        }
                    }

                ArrayAdapter arrayAdapter = new ArrayAdapter(grupe.this, android.R.layout.simple_list_item_1, tekme);
                utakmice.setAdapter(arrayAdapter);
                }

                if (a==16){
                    textView.setText("GRUPA A");
                    A = ekipe.subList(0,4);
                    ListaEkipa listaEkipaAdapter = new ListaEkipa(grupe.this, A);
                    grupaA.setAdapter(listaEkipaAdapter);
                    B = ekipe.subList(4, 8);
                    textView2.setText("GRUPA B");
                    ListaEkipa listaEkipaAdapter2 = new ListaEkipa(grupe.this, B);
                    grupaB.setAdapter(listaEkipaAdapter2);
                    C = ekipe.subList(8,12);
                    textView3.setText("GRUPA C");
                    ListaEkipa listaEkipaAdapter3 = new ListaEkipa(grupe.this, C);
                    grupaC.setAdapter(listaEkipaAdapter3);
                    D = ekipe.subList(12,16);
                    textView4.setText("GRUPA D");
                    ListaEkipa listaEkipaAdapter4 = new ListaEkipa(grupe.this, D);
                    grupaD.setAdapter(listaEkipaAdapter4);
                    int j;
                    for (int i=0; i<3; i++) {
                        j=0;
                        if(i==1){j=2;}
                        if(i==2){j=3;}
                        while (j<4) {
                            if(j!=i) {
                                if(minuta>=10) {
                                    tekme.add(sat + ":" + minuta + "  " + A.get(i).getImeEkipe() + "-" + A.get(j).getImeEkipe());
                                }
                                else{
                                    tekme.add(sat + ":0" + minuta + "  " + A.get(i).getImeEkipe() + "-" + A.get(j).getImeEkipe());
                                }
                                minuta+=30;
                                if(minuta>=60){
                                    sat+=1;
                                    minuta-=60;
                                }

                                if(sat>=24){
                                    sat=0;
                                }
                            }
                            j=j+1;
                        }
                    }
                    for (int i=0; i<3; i++) {
                        j=0;
                        if(i==1){j=2;}
                        if(i==2){j=3;}
                        while (j<4) {
                            if(j!=i) {
                                if(minuta>=10) {
                                    tekme.add(sat + ":" + minuta + "  " + B.get(i).getImeEkipe() + "-" + B.get(j).getImeEkipe());
                                }
                                else{
                                    tekme.add(sat + ":0" + minuta + "  " + B.get(i).getImeEkipe() + "-" + B.get(j).getImeEkipe());
                                }
                                minuta+=30;
                                if(minuta>=60){
                                    sat+=1;
                                    minuta-=60;
                                }

                                if(sat>=24){
                                    sat=0;
                                }
                            }
                            j=j+1;
                        }
                    }
                    for (int i=0; i<3; i++) {
                        j=0;
                        if(i==1){j=2;}
                        if(i==2){j=3;}
                        while (j<4) {
                            if(j!=i) {
                                if(minuta>=10) {
                                    tekme.add(sat + ":" + minuta + "  " + C.get(i).getImeEkipe() + "-" + C.get(j).getImeEkipe());
                                }
                                else{
                                    tekme.add(sat + ":0" + minuta + "  " + C.get(i).getImeEkipe() + "-" + C.get(j).getImeEkipe());
                                }
                                minuta+=30;
                                if(minuta>=60){
                                    sat+=1;
                                    minuta-=60;
                                }

                                if(sat>=24){
                                    sat=0;
                                }
                            }
                            j=j+1;
                        }
                    }
                    for (int i=0; i<3; i++) {
                        j=0;
                        if(i==1){j=2;}
                        if(i==2){j=3;}
                        while (j<4) {
                            if(j!=i) {
                                if(minuta>=10) {
                                    tekme.add(sat + ":" + minuta + "  " + D.get(i).getImeEkipe() + "-" + D.get(j).getImeEkipe());
                                }
                                else{
                                    tekme.add(sat + ":0" + minuta + "  " + D.get(i).getImeEkipe() + "-" + D.get(j).getImeEkipe());
                                }
                                minuta+=30;
                                if(minuta>=60){
                                    sat+=1;
                                    minuta-=60;
                                }

                                if(sat>=24){
                                    sat=0;
                                }
                            }
                            j=j+1;
                        }
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter(grupe.this, android.R.layout.simple_list_item_1, tekme);
                    utakmice.setAdapter(arrayAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void save(){
        for(int i=0; i<tekme.size();i++){
            String id = utakmiceRef.push().getKey();
            Utakmice utakmice = new Utakmice(id, tekme.get(i),"");
            utakmiceRef.child(id).setValue(utakmice);
            Toast.makeText(this, "Uspjesno ste napravili događaj!",Toast.LENGTH_SHORT).show();
        }

        Skupine skupine = new Skupine(A.get(0).getImeEkipe()+" 0",A.get(1).getImeEkipe()+" 0",
                A.get(2).getImeEkipe()+" 0",A.get(3).getImeEkipe()+" 0", "grupaA");
        grupeRef.child("grupaA").setValue(skupine);
        Skupine skupine2 = new Skupine(B.get(0).getImeEkipe()+" 0",B.get(1).getImeEkipe()+" 0",
                B.get(2).getImeEkipe()+" 0",B.get(3).getImeEkipe()+" 0","grupaB");
        grupeRef.child("grupaB").setValue(skupine2);
        if(ekipe.size()==16){
            Skupine skupine3 = new Skupine(C.get(0).getImeEkipe()+" 0",C.get(1).getImeEkipe()+" 0",
                    C.get(2).getImeEkipe()+" 0",C.get(3).getImeEkipe()+" 0","grupaC");
            grupeRef.child("grupaC").setValue(skupine3);
            Skupine skupine4 = new Skupine(D.get(0).getImeEkipe()+" 0",D.get(1).getImeEkipe()+" 0",
                    D.get(2).getImeEkipe()+" 0",D.get(3).getImeEkipe()+" 0","grupaD");
            grupeRef.child("grupaD").setValue(skupine4);
        }
    }

}

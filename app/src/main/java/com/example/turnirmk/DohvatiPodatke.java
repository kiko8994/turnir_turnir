package com.example.turnirmk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DohvatiPodatke extends AppCompatActivity {

    public static final String IME_DOGADAJA = "imedogadaja";
    public static final String ID_DOGADAJA = "iddogadaja";
    public static final String DATUM = "datum";
    private ListView listView;
    DatabaseReference databaseReference;
    List<Dogadaj>ListaDogadaja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dohvati_podatke);

        listView = findViewById(R.id.list_view);
        databaseReference = FirebaseDatabase.getInstance().getReference("dogadaj");
        ListaDogadaja = new ArrayList<>();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Dogadaj dogadaj = ListaDogadaja.get(i);

                showDialog(dogadaj.getImeDogadaja(),dogadaj.getDatum());
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dogadaj dogadaj = ListaDogadaja.get(i);

                Intent intent = new Intent(getApplicationContext(), DodajEkipu.class);

                intent.putExtra(ID_DOGADAJA, dogadaj.getIdDogadaja());
                intent.putExtra(IME_DOGADAJA, dogadaj.getImeDogadaja());
                intent.putExtra(DATUM, dogadaj.getDatum());

                startActivity(intent);
            }
        });

    }

    private void showDialog(String imeDogadaja, String datum){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog, null);

        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("Prijava ekipe :"+imeDogadaja+ " "+datum);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }


    protected void onStart(){
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ListaDogadaja.clear();
                for (DataSnapshot dogadajSnapshot : dataSnapshot.getChildren()){
                    Dogadaj dogadaj = dogadajSnapshot.getValue(Dogadaj.class);
                    ListaDogadaja.add(dogadaj);
                }
                dogadajInfoAdapter InfoAdapter = new dogadajInfoAdapter(DohvatiPodatke.this, ListaDogadaja);
                listView.setAdapter(InfoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

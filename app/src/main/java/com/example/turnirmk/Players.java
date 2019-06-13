package com.example.turnirmk;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Players extends AppCompatActivity {

    ListView listViewPlayers;
    EditText editTextSearch;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> sviIgraci = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        listViewPlayers = (ListView) findViewById(R.id.listViewPlayers);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        setTitle("Popis igrača");
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (Players.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference players = rootRef.child("profil");
        players.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String imeIgraca = childSnapshot.child("ime").getValue().toString();
                    String prezimeIgraca = childSnapshot.child("prezime").getValue().toString();
                    sviIgraci.add(imeIgraca + " " + prezimeIgraca);
                }
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, sviIgraci);
                listViewPlayers.setAdapter(arrayAdapter);

                listViewPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedPlayer = (String) listViewPlayers.getItemAtPosition(position);
                        Log.d("igrac", selectedPlayer);

                        final String[] player = selectedPlayer.split(" ");

                        final DatabaseReference podaci = rootRef.child("profil");

                        podaci.orderByChild("ime").equalTo(player[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot childsnapshot: dataSnapshot.getChildren()) {
                                        String prezime = childsnapshot.child("prezime").getValue().toString();
                                        Log.d("prezime", prezime);
                                        Log.d("prezime2", player[1]);

                                        if (prezime.equals(player[1])) {
                                            Log.d("dalijeušlo", "true");
                                            String imeIgraca = childsnapshot.child("ime").getValue().toString();
                                            String prezimeIgraca = childsnapshot.child("prezime").getValue().toString();
                                            String kontakt = childsnapshot.child("kontakt").getValue().toString();
                                            String ekipa = childsnapshot.child("ekipa").getValue().toString();

                                            final CharSequence[] items = {"Ime: " + imeIgraca + " " + prezimeIgraca, "Kontakt: " + kontakt, "Ekipa: " + ekipa};
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Players.this);
                                            builder.setTitle("Podaci o igraču");
                                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int item) {
                                                    Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            AlertDialog alert = builder.create();
                                            alert.show();
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

package com.example.turnirmk;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Players extends AppCompatActivity {

    ListView listViewPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        listViewPlayers = (ListView) findViewById(R.id.listViewPlayers);

        final ArrayList<String> sviIgraci = new ArrayList<String>();
        final ArrayList<String> alluserName = new ArrayList<String>();

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference players = rootRef.child("profil");
        players.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String imeIgraca = childSnapshot.child("ime").getValue().toString();
                    String prezimeIgraca = childSnapshot.child("prezime").getValue().toString();
                    String userName = childSnapshot.child("username").getValue().toString();
                    sviIgraci.add(imeIgraca + " " + prezimeIgraca);
                    alluserName.add(userName);
                }
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, sviIgraci);
                listViewPlayers.setAdapter(arrayAdapter);

                listViewPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedPlayers = alluserName.get(position);

                        DatabaseReference podaci = rootRef.child("profil").child(selectedPlayers);

                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String imeIgraca = dataSnapshot.child("ime").getValue().toString();
                                    String prezimeIgraca = dataSnapshot.child("prezime").getValue().toString();
                                    String kontakt = dataSnapshot.child("kontakt").getValue().toString();
                                    String ekipa = dataSnapshot.child("ekipa").getValue().toString();

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

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };
                        podaci.addListenerForSingleValueEvent(eventListener);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

package com.example.turnirmk;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MyTournament extends AppCompatActivity {

    ListView listViewMyTour;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tournament);

        listViewMyTour = (ListView) findViewById(R.id.listViewMyTour);

        final List<String> mojiTurniri = new ArrayList<String>();

        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        String email = currentUser.getEmail();
        String[] username = email.split("@");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dogadaj = rootRef.child("dogadaj");
        dogadaj.orderByChild("kontakt").equalTo(username[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        String imeDogadaja = childSnapshot.child("imeDogadaja").getValue().toString();
                        mojiTurniri.add(imeDogadaja);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, mojiTurniri);
                    listViewMyTour.setAdapter(arrayAdapter);

                    listViewMyTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String value = listViewMyTour.getItemAtPosition(position).toString();
                            Intent intent = new Intent(MyTournament.this, TeamOnMyTournament.class);
                            intent.putExtra("key", value);
                            startActivity(intent);
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Niste otvorili ni jedan turnir!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
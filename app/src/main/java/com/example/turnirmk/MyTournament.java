package com.example.turnirmk;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MyTournament extends AppCompatActivity {

    TextView textViewMyTournament;
    private FirebaseAuth mAuth;
    final List<String> mojiTurniri = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tournament);

        textViewMyTournament = (TextView) findViewById(R.id.textViewMyTournament);

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
                        for (int i=0; i < mojiTurniri.size(); i++) {
                            textViewMyTournament.setText(textViewMyTournament.getText() + mojiTurniri.get(i) + " , ");
                        }
                        mojiTurniri.clear();
                    }
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

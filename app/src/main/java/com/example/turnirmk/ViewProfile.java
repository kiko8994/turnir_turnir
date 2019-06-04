package com.example.turnirmk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewProfile extends AppCompatActivity {

    TextView textViewIme, textViewPrezime, textViewKontakt, textViewUsername, textViewEkipa;
    Button buttonEditProfile;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        String email = currentUser.getEmail();
        String[] display_username = email.split("@");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("profil").child(display_username[0]);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("ime").getValue().toString();
                    textViewIme.setText(name);
                    String surname = dataSnapshot.child("prezime").getValue().toString();
                    textViewPrezime.setText(surname);
                    String contacts = dataSnapshot.child("kontakt").getValue().toString();
                    textViewKontakt.setText(contacts);
                    String username = dataSnapshot.child("username").getValue().toString();
                    textViewUsername.setText(username);
                    String team = dataSnapshot.child("ekipa").getValue().toString();
                    textViewEkipa.setText(team);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        textViewIme = (TextView) findViewById(R.id.textViewIme);
        textViewPrezime = (TextView) findViewById(R.id.textViewPrezime);
        textViewKontakt = (TextView) findViewById(R.id.textViewKontakt);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEkipa = (TextView) findViewById(R.id.textViewEkipa);

        buttonEditProfile = (Button) findViewById(R.id.buttonEditProfile);

        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfile.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}

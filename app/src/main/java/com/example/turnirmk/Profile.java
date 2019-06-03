package com.example.turnirmk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity{

    private FirebaseAuth mAuth;
    EditText editTextIme, editTextPrezime, editTextKontakt, editTextEmail2;
    Button buttonSpremi;

    DatabaseReference databaseProfile;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String display_email = currentUser.getEmail();
            editTextEmail2.setText(display_email);
        } else {
            editTextEmail2.setText("BLABLABLABLABLA");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseProfile = FirebaseDatabase.getInstance().getReference("profil");

        editTextIme = (EditText) findViewById(R.id.editTextIme);
        editTextPrezime = (EditText) findViewById(R.id.editTextPrezime);
        editTextKontakt = (EditText) findViewById(R.id.editTextBroj);
        editTextEmail2 = (EditText) findViewById(R.id.editTextEmail2);

        buttonSpremi = (Button) findViewById(R.id.buttonSpremi);

        buttonSpremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spremi();
            }
        });
    }

    protected void spremi() {
        String ime = editTextIme.getText().toString().trim();
        String prezime = editTextPrezime.getText().toString().trim();
        String kontakt = editTextKontakt.getText().toString().trim();
        String email2 = editTextEmail2.getText().toString().trim();

        if (ime.isEmpty()) {
            editTextIme.setError("Ime mora biti upisano");
            editTextIme.requestFocus();
            return;
        }
        else if (prezime.isEmpty()) {
            editTextPrezime.setError("Prezime mora biti upisano");
            editTextPrezime.requestFocus();
            return;
        }
        else if (kontakt.isEmpty()) {
            editTextKontakt.setError("Kontakt mora biti upisan");
            editTextKontakt.requestFocus();
            return;
        }
        else if (kontakt.length() < 9) {
            editTextKontakt.setError("Kontakt mora imati barem 9 znamenki");
            editTextKontakt.requestFocus();
            return;
        }

        else {
            String id = databaseProfile.push().getKey();
            Profil profil = new Profil(ime, prezime, kontakt, email2, id);

            databaseProfile.child(id).setValue(profil);
            Intent intent = new Intent(getBaseContext(), MenuActivity.class);
            startActivity(intent);

            Toast.makeText(this,"Uspješno spremljeno", Toast.LENGTH_LONG).show();
        }
    }
}

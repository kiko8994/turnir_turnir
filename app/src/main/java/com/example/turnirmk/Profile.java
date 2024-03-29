package com.example.turnirmk;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Profile extends AppCompatActivity{

    private FirebaseAuth mAuth;
    EditText editTextIme, editTextPrezime, editTextKontakt, editTextUsername, editTextEkipa;
    Button buttonSpremi;

    DatabaseReference databaseProfile;
    ProgressBar progressBar4;

    @Override
    public void onStart() {
        super.onStart();
        setTitle("Izmijeni profil");
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        String display_email = currentUser.getEmail();
        String[] display_username = display_email.split("@");
        if (currentUser != null) {
            editTextUsername.setText(display_username[0]);
            editTextUsername.setEnabled(false);
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
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEkipa = (EditText) findViewById(R.id.editTextEkipa);

        buttonSpremi = (Button) findViewById(R.id.buttonSpremi);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);

        NavigationView nvPlay =(NavigationView) findViewById(R.id.nvPlay);

        buttonSpremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextIme.onEditorAction(EditorInfo.IME_ACTION_DONE);
                editTextPrezime.onEditorAction(EditorInfo.IME_ACTION_DONE);
                editTextKontakt.onEditorAction(EditorInfo.IME_ACTION_DONE);
                editTextEkipa.onEditorAction(EditorInfo.IME_ACTION_DONE);
                progressBar4.setVisibility(View.VISIBLE);
                spremi();
            }
        });
    }

    protected void spremi() {
        String ime = editTextIme.getText().toString().trim();
        String prezime = editTextPrezime.getText().toString().trim();
        String kontakt = editTextKontakt.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String ekipa = editTextEkipa.getText().toString().trim();

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

        else if (ekipa.isEmpty()) {
            editTextEkipa.setError("Ekipa mora biti upisana");
            editTextEkipa.requestFocus();
            return;
        }

        else {
            Profil profil = new Profil(ime, prezime, kontakt, username, ekipa);

            databaseProfile.child(username).setValue(profil);
            databaseProfile.push();
            Intent intent = new Intent(getBaseContext(), MenuActivity.class);
            startActivity(intent);

            Toast.makeText(this,"Uspješno spremljeno", Toast.LENGTH_LONG).show();
        }
    }
}

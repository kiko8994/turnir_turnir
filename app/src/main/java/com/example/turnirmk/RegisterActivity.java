package com.example.turnirmk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText editTextEMail, editTextLozinka;

    private FirebaseAuth mAuth;

    private DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEMail = findViewById(R.id.editTextEMail);
        editTextLozinka = findViewById(R.id.editTextLozinka);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.buttonPotvrdi).setOnClickListener(this);
    }

    private void registerUser() {
        final String email = editTextEMail.getText().toString().trim();
        final String lozinka = editTextLozinka.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEMail.setError("Email mora biti upisan");
            editTextEMail.requestFocus();
            return;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEMail.setError("Email mora biti valjan");
            editTextEMail.requestFocus();
            return;
        }

        else if (lozinka.isEmpty()) {
            editTextLozinka.setError("Lozinka mora biti upisana");
            editTextLozinka.requestFocus();
            return;
        }

        else if (lozinka.length()<6) {
            editTextLozinka.setError("Lozinka mora sadržavati najmanje 6 znakova");
            editTextLozinka.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, lozinka).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Uspješno dodan korisnik", Toast.LENGTH_LONG).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "Ova e-mail adresa je već registrirana", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPotvrdi:
                editTextEMail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                editTextLozinka.onEditorAction(EditorInfo.IME_ACTION_DONE);
                registerUser();
                break;
        }
    }
}

package com.example.turnirmk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;

    EditText editTextEMail, editTextLozinka;
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        editTextEMail = findViewById(R.id.editTextEMail);
        editTextLozinka = findViewById(R.id.editTextLozinka);
        progressBar2 = findViewById(R.id.progressBar2);

        findViewById(R.id.buttonRegistracija).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
    }

    private void userLogin() {
        final String email = editTextEMail.getText().toString().trim();
        String lozinka = editTextLozinka.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEMail.setError("Email mora biti upisan");
            editTextEMail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEMail.setError("Email mora biti valjan");
            editTextEMail.requestFocus();
            return;
        }

        if (lozinka.isEmpty()) {
            editTextLozinka.setError("Lozinka mora biti upisana");
            editTextLozinka.requestFocus();
            return;
        }

        if (lozinka.length() < 6) {
            editTextLozinka.setError("Lozinka mora sadržavati najmanje 6 znakova");
            editTextLozinka.requestFocus();
            return;
        }

        progressBar2.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, lozinka).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar2.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Uspješna prijava", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegistracija:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.buttonLogin:
                editTextEMail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                editTextLozinka.onEditorAction(EditorInfo.IME_ACTION_DONE);
                userLogin();
                break;
        }
    }
}


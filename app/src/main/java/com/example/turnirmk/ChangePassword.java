package com.example.turnirmk;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    EditText editTextmail, editTextOldPassword, editTextNewPassword;
    Button buttonChange;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Promjena lozinke");
        editTextmail = (EditText) findViewById(R.id.editTextmail);
        editTextOldPassword = (EditText) findViewById(R.id.editTextOldPassword);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        buttonChange = (Button) findViewById(R.id.buttonChange);

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAndUpdate();
            }
        });
    }

    private void CheckAndUpdate() {
        String email = editTextmail.getText().toString().trim();
        String oldPassword = editTextOldPassword.getText().toString().trim();
        final String newPassword = editTextNewPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextmail.setError("Email mora biti upisan");
            editTextmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextmail.setError("Email mora biti valjan");
            editTextmail.requestFocus();
            return;
        }

        if (oldPassword.isEmpty()) {
            editTextOldPassword.setError("Password mora biti upisan");
            editTextOldPassword.requestFocus();
            return;
        }

        if (newPassword.isEmpty()) {
            editTextNewPassword.setError("Password mora biti upisan");
            editTextNewPassword.requestFocus();
            return;
        }

        if (newPassword.length() < 6) {
            editTextNewPassword.setError("Password mora sadržavati barem 6 znamenki");
            editTextNewPassword.requestFocus();
            return;
        }

        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
        currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    currentUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Uspješno izmijenjena lozinka!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Nemogu izmijeniti lozinku!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Kriva kombinacija email-a i password-a!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

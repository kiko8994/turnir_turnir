package com.example.turnirmk;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {
    private FirebaseAuth mAuth;
    @Override
    public void onCreate() {
        super.onCreate();;

        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(Home.this, MenuActivity.class));
        }
    }
}

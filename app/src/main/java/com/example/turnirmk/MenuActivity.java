package com.example.turnirmk;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth mAuth;
    DatabaseReference databaseDogadaj;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        TextView textViewWelcome = (TextView) findViewById(R.id.textViewWelcome);
        if (currentUser != null) {
            String email = currentUser.getEmail();
            String[] display = email.split("@");
            textViewWelcome.setText("Dobrodošao " + display[0] + "!");
        } else {
            textViewWelcome.setText("BLABLABLABLABLA");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nv =(NavigationView) findViewById(R.id.nv1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_profile:
                        Intent intent = new Intent(MenuActivity.this, ViewProfile.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_addtour:
                        Intent intent2 = new Intent(MenuActivity.this, AddTournament.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_viewtour:
                        Intent intent3 = new Intent(MenuActivity.this, DohvatiPodatke.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_mytour:
                        Intent intent4 = new Intent(MenuActivity.this, MyTournament.class);
                        startActivity(intent4);
                        break;

                    case R.id.nav_Logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent1 = new Intent(MenuActivity.this, MainActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ((mToggle.onOptionsItemSelected(item))){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

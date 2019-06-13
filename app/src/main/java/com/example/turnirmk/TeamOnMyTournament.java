package com.example.turnirmk;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TeamOnMyTournament extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ListView listViewTeamOnMyTour;
    Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_on_my_tournament);

        listViewTeamOnMyTour = (ListView) findViewById(R.id.listViewTeamOnMyTour);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        final ArrayList<String> ekipeNaTurniru = new ArrayList<String>();
        final ArrayList<String> voditeljiEkipa = new ArrayList<String>();
        final ArrayList<String> keyList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        final String imeDogadaja = extras.getString("key");
        setTitle("Ekipe na turniru "+imeDogadaja);
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dogadaj = rootRef.child("dogadaj");
        dogadaj.orderByChild("imeDogadaja").equalTo(imeDogadaja).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (final DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        String idDogadaja = childSnapshot.child("idDogadaja").getValue().toString();

                        final DatabaseReference ekipe = rootRef.child("ekipe").child(idDogadaja);
                        ekipe.orderByChild("idTurnira").equalTo(idDogadaja).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot cS: dataSnapshot.getChildren()) {
                                        String imeEkipe = cS.child("imeEkipe").getValue().toString();
                                        String voditelj = cS.child("voditelj").getValue().toString();
                                        cS.child("imeEkipe").getKey();
                                        ekipeNaTurniru.add(imeEkipe);
                                        voditeljiEkipa.add(voditelj);
                                        keyList.add(cS.getKey());
                                    }
                                    final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, ekipeNaTurniru);
                                    listViewTeamOnMyTour.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                                    listViewTeamOnMyTour.setAdapter(arrayAdapter1);

                                    buttonDelete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int itemCount = listViewTeamOnMyTour.getCount();
                                            SparseBooleanArray checkedItemPositions = listViewTeamOnMyTour.getCheckedItemPositions();

                                            for (int i = itemCount-1; i >= 0; i--) {
                                                if (checkedItemPositions.get(i)) {
                                                    arrayAdapter1.remove(ekipeNaTurniru.get(i));
                                                    ekipe.child(keyList.get(i)).removeValue();
                                                }
                                            }
                                            checkedItemPositions.clear();
                                            arrayAdapter1.notifyDataSetChanged();
                                        }
                                    });

                                    listViewTeamOnMyTour.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                        @Override
                                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                            String value1 = voditeljiEkipa.get(position);

                                            DatabaseReference userNameRef = rootRef.child("profil").child(value1);
                                            ValueEventListener eventListener = new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        String name = dataSnapshot.child("ime").getValue().toString();
                                                        String surname = dataSnapshot.child("prezime").getValue().toString();
                                                        String contacts = dataSnapshot.child("kontakt").getValue().toString();

                                                        final CharSequence[] items = {"Voditelj: " + name + " " + surname, "Kontakt: " + contacts};
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(TeamOnMyTournament.this);
                                                        builder.setTitle("Podaci o voditelju ekipe");
                                                        builder.setItems(items, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int item) {
                                                                Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        AlertDialog alert = builder.create();
                                                        alert.show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            };
                                            userNameRef.addListenerForSingleValueEvent(eventListener);

                                            return true;
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

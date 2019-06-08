package com.example.turnirmk;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
        final ArrayList<String> keyList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        final String imeDogadaja = extras.getString("key");

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
                                        cS.child("imeEkipe").getKey();
                                        ekipeNaTurniru.add(imeEkipe);
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

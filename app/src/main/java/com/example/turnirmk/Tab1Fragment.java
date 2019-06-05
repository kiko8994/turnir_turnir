package com.example.turnirmk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tab1Fragment extends Fragment {
    private static String TAG = "Tab1Fragment";
    TextView textView;

    DatabaseReference utakmiceDatabase;
    List<String> tekme;
    ListView listViewUtakmice;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        String id = "-Lgcen1MQOVOUwjA_KJu";
        //String strtext = getArguments().getString("edttext");
        utakmiceDatabase = FirebaseDatabase.getInstance().getReference("utakmice").child(id);
        tekme = new ArrayList<>();
        listViewUtakmice = (ListView)view.findViewById(R.id.listTekme1);
        utakmiceDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    Utakmice utakmice = snap.getValue(Utakmice.class);
                    tekme.add(utakmice.getTekma());
                }

                ArrayAdapter<String> listaAdapter = new ArrayAdapter(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        tekme
                );
                listViewUtakmice.setAdapter(listaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}

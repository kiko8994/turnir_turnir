package com.example.turnirmk;

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

public class Tab2Fragment extends Fragment {
    private static String TAG = "Tab2Fragment";
    ListView grupaA;
    ListView grupaB;
    ListView grupaD;
    ListView grupaC;
    TextView c;
    TextView d;
    List<String> grpA;
    List<String> grp;
    List<String> grpB;
    List<String> grpC;
    List<String> grpD;
    DatabaseReference grupeDatabase;
    private static final String ARG_PARAM1 = "param1";
    private String id;


    public static Tab2Fragment newInstance(String param1) {
        Tab2Fragment fragment = new Tab2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab2_fragment,container,false);
        id = getArguments().getString(ARG_PARAM1);

        c = (TextView) view.findViewById(R.id.textC);
        d = (TextView) view.findViewById(R.id.textD);
        grupaA = (ListView) view.findViewById(R.id.listGrupaA);
        grupaB = (ListView) view.findViewById(R.id.listGrupaB);
        grupaC = (ListView) view.findViewById(R.id.listGrupaC);
        grupaD = (ListView) view.findViewById(R.id.listGrupaD);

        grpA = new ArrayList<>();
        grpB = new ArrayList<>();
        grpC = new ArrayList<>();
        grpD = new ArrayList<>();
        grp = new ArrayList<>();
        grupeDatabase = FirebaseDatabase.getInstance().getReference("grupe").child(id);

        grupeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                grp.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    Skupine skupine = snap.getValue(Skupine.class);
                    grp.add(skupine.getEkipaJedan());
                    grp.add(skupine.getEkipaDva());
                    grp.add(skupine.getEkipaTri());
                    grp.add(skupine.getEkipaCetiri());
                }
                int a=grp.size();
                if(a==16){
                    grpC = grp.subList(8,12);
                    grpD = grp.subList(12,16);
                    c.setText("GRUPA C");
                    d.setText("GRUPA D");
                    ArrayAdapter<String> listaAdapterC = new ArrayAdapter(
                            getContext(),
                            android.R.layout.simple_list_item_1,
                            grpC
                    );
                    grupaC.setAdapter(listaAdapterC);

                    ArrayAdapter<String> listaAdapterD = new ArrayAdapter(
                            getContext(),
                            android.R.layout.simple_list_item_1,
                            grpD
                    );
                    grupaD.setAdapter(listaAdapterD);
                }
                grpA = grp.subList(0,4);
                grpB = grp.subList(4,8);
                ArrayAdapter<String> listaAdapter = new ArrayAdapter(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        grpA
                );
                grupaA.setAdapter(listaAdapter);
                ArrayAdapter<String> listaAdapter1 = new ArrayAdapter(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        grpB
                );
                grupaB.setAdapter(listaAdapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}

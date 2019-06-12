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
import java.util.Collections;
import java.util.List;

public class Tab3Fragment extends Fragment {
    private static String TAG = "Tab3Fragment";
    private String mParam1;
    private static final String ARG_PARAM1 = "param1";
    TextView textView;
    List<Strijelac> svi_strijelci;

    public static Tab3Fragment newInstance(String param1) {
        Tab3Fragment fragment = new Tab3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab3_fragment,container,false);
        mParam1 = getArguments().getString(ARG_PARAM1);
        svi_strijelci = new ArrayList<>();
        DatabaseReference strijelciDatabase = FirebaseDatabase.getInstance().getReference("strijelci").child(mParam1);
        final ListView list_view_strijelci = (ListView)view.findViewById(R.id.listNajboljiStrijelci);
        textView = (TextView)view.findViewById(R.id.textNajboljiStrijelci);
        textView.setText("Najbolji Strijelci");
        strijelciDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                svi_strijelci.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    Strijelac strijelac = snap.getValue(Strijelac.class);
                    svi_strijelci.add(strijelac);
                }
                Collections.sort(svi_strijelci, new Sortbyroll() {
                    @Override
                    public int compare(Strijelac a, Strijelac b) {
                        return super.compare(b, a);
                    }
                });
                ListaStrijelaca listaAdapter = new ListaStrijelaca(getActivity(),svi_strijelci);
                list_view_strijelci.setAdapter(listaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}

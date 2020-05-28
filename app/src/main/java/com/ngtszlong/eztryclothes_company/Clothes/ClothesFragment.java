package com.ngtszlong.eztryclothes_company.Clothes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ngtszlong.eztryclothes_company.R;

import java.util.ArrayList;

public class ClothesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ClothesListAdapter clothesListAdapter;
    private ArrayList<ClothesList> clothesListArrayList;

    private FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clothes, container, false);
        getActivity().setTitle(getText(R.string.YourClothesList));
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        recyclerView = view.findViewById(R.id.rv_itemlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mFirebaseDatabase.getReference().child("Clothes");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferences sp = getActivity().getSharedPreferences("Setting", 0);
                clothesListArrayList = new ArrayList<ClothesList>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ClothesList l = dataSnapshot1.getValue(ClothesList.class);
                    if (firebaseUser.getUid().equals(l.getUid())) {
                        clothesListArrayList.add(l);
                    }
                }
                clothesListAdapter = new ClothesListAdapter(getContext(), clothesListArrayList);
                recyclerView.setAdapter(clothesListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRef.keepSynced(true);
        return view;
    }
}

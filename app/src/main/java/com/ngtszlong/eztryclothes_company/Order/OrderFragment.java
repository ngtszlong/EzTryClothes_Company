package com.ngtszlong.eztryclothes_company.Order;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ngtszlong.eztryclothes_company.R;

import java.util.ArrayList;

public class OrderFragment extends Fragment {
    public static ArrayList<Order> orderArrayList;
    OrderAdapter orderAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        getActivity().setTitle(getText(R.string.CustomerPurchaseOrder));

        recyclerView = view.findViewById(R.id.rv_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("PurchaseOrder");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderArrayList = new ArrayList<Order>();
                SharedPreferences sp = getActivity().getSharedPreferences( "Setting", 0 );
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){
                        for (DataSnapshot dataSnapshot3: dataSnapshot2.getChildren()) {
                            Order order = dataSnapshot3.getValue(Order.class);
                            if (order.getCompanyuid().equals(firebaseUser.getUid())){
                                orderArrayList.add(order);
                            }
                        }
                    }
                }
                orderAdapter = new OrderAdapter(getContext(), orderArrayList);
                recyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}

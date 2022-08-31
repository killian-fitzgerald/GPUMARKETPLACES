package com.example.gpu;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    RecyclerView recyclerView;
    List<data> list1;
    EditText editText;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    Elec_Recycler elec_recycler;


    public BlankFragment() {
        //empty
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView=v.findViewById(R.id.elecrecycler);
        editText=v.findViewById(R.id.search);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Items");
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        list1=new ArrayList<>();
       elec_recycler= new Elec_Recycler(list1,getContext());
        recyclerView.setAdapter(elec_recycler);

        databaseReference= FirebaseDatabase.getInstance().getReference("GPU");
        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    data data = dataSnapshot1.getValue(data.class);
                    list1.add(data);
                }

                elec_recycler.notifyDataSetChanged();
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());

            }
        });
        return v;

    }

    private void filter(String text) {

        ArrayList<data> filterlist = new ArrayList<>();
        for (data item:list1 ){

            if (item.getName().toLowerCase().contains(text.toLowerCase())){

                filterlist.add(item);


            }
        }

        elec_recycler.filteredlist(filterlist);

    }


}

package com.example.gpu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Review_Gpu extends AppCompatActivity {
    RecyclerView recyclerView;
    List<data> list1;
    EditText editText;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    Review_Elec_Recycler gpu_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_gpu);
        recyclerView=findViewById(R.id.reviewelectronics);
        editText=findViewById(R.id.search);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items");
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        list1=new ArrayList<>();
        gpu_recycler= new Review_Elec_Recycler(list1,this);

        recyclerView.setAdapter(gpu_recycler);


        databaseReference= FirebaseDatabase.getInstance().getReference("GPU");
        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    data data = dataSnapshot1.getValue(data.class);
                    data.setKey(dataSnapshot1.getKey());
                    list1.add(data);
                }

                gpu_recycler.notifyDataSetChanged();
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });
    }
}

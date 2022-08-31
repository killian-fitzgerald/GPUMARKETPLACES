package com.example.gpu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCart extends AppCompatActivity {

    RecyclerView recyclerView;
    List<cartdata> cartdataList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        getSupportActionBar().setTitle("My Cart");
        final ProgressDialog progressDialog = new ProgressDialog(MyCart.this);
        progressDialog.setMessage("Loading Items");
        recyclerView=findViewById(R.id.cartrecycler);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final String userkey=currentFirebaseUser.getUid();
        cartdataList=new ArrayList<>();
        final Cart_Adapter cart_adapter = new Cart_Adapter(cartdataList,this);

        recyclerView.setAdapter(cart_adapter);
        databaseReference= FirebaseDatabase.getInstance().getReference("make").child(userkey).child("mycart");
        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartdataList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    cartdata cartdata = dataSnapshot1.getValue(cartdata.class);
                    cartdata.setKey(dataSnapshot1.getKey());
                    cartdataList.add(cartdata);
                }

                cart_adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }
}

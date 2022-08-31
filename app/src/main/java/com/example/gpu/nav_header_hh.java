package com.example.gpu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class nav_header_hh extends AppCompatActivity {
    TextView textView;
    FirebaseDatabase firebaseDatabase1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_header_hh);
        textView=findViewById(R.id.user);
        textView.setText("hellooo");




        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.user);
        navUsername.setText("Your Text Here");



        firebaseDatabase1= FirebaseDatabase.getInstance();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final String userkey=currentFirebaseUser.getUid();
        firebaseDatabase1.getReference("make").child(userkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("fullNaame").getValue().toString();
                 textView.setText(name);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}

package com.example.gpu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    EditText names,address,contact;
    TextView textView;

    Button btn;
    FirebaseDatabase firebaseDatabase1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        names=findViewById(R.id.full);
        btn=findViewById(R.id.regbtn);
        address=findViewById(R.id.ad);
        contact=findViewById(R.id.contact);
        textView=findViewById(R.id.email);
        firebaseDatabase1= FirebaseDatabase.getInstance();

        getSupportActionBar().setTitle("Profile");

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final String userkey=currentFirebaseUser.getUid();


        firebaseDatabase1.getReference("make").child(userkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String addre=   dataSnapshot.child("address").getValue().toString();
                String cont = dataSnapshot.child("cell").getValue().toString();
                String name = dataSnapshot.child("fullNaame").getValue().toString();
                String mail = dataSnapshot.child("Email").getValue().toString();
                names.setText(name);
                textView.setText(mail);
                address.setText(addre);
                contact.setText(cont);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                final String userkey=currentFirebaseUser.getUid();

                FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                DatabaseReference databaseReference =firebaseDatabase.getReference("make");
                String nam =names.getText().toString();
                String cont=contact.getText().toString();
                String a =address.getText().toString();
                databaseReference.child(userkey).child("fullNaame").setValue(nam);
                databaseReference.child(userkey).child("cell").setValue(cont);
                databaseReference.child(userkey).child("address").setValue(a);

            }
        });




    }
}

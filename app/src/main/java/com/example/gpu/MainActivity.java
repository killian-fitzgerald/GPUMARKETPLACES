package com.example.gpu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText txt_email, txt_pass;
    TextView admi, not,forgot,b;
    FirebaseAuth firebaseAuth;
    DatabaseReference reff;
    ProgressDialog progressDialog;

    Button signin,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth= FirebaseAuth.getInstance();
        b=findViewById(R.id.b2);
        txt_email=findViewById(R.id.id1);
        not=findViewById(R.id.notadmin);
        txt_pass=findViewById(R.id.ed2);
        login=findViewById(R.id.login);
        admi=findViewById(R.id.admin);
        signin=findViewById(R.id.b1);
        login.setVisibility(View.INVISIBLE);
        not.setVisibility(View.INVISIBLE);
        forgot=findViewById(R.id.forget);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),forget_password.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Email =txt_email.getText().toString().trim();
                String pass =txt_pass.getText().toString().trim();
                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(MainActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Loging In");

                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(Email, pass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    startActivity(new Intent(getApplicationContext(),Welcome.class));
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    finish();
                                    txt_email.setText("");
                                    txt_pass.setText("");


                                } else {
                                    progressDialog.dismiss();

                                    Toast.makeText(MainActivity.this, "Login Fail or User not Found", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        admi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.VISIBLE);
                signin.setVisibility(View.INVISIBLE);
                admi.setVisibility(View.INVISIBLE);
                not.setVisibility(View.VISIBLE);
            }
        });

        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login.setVisibility(View.INVISIBLE);
                signin.setVisibility(View.VISIBLE);
                admi.setVisibility(View.VISIBLE);
                not.setVisibility(View.INVISIBLE);

            }
        });




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),signup.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                info();

            }
        });


    }

    public void info() {
        reff = FirebaseDatabase.getInstance().getReference().child("Admins");
        final String adEmail = txt_email.getText().toString().trim();
        final String adPass = txt_pass.getText().toString().trim();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String dbmail = dataSnapshot.child("Email").getValue().toString();
                String dbpass = dataSnapshot.child("Password").getValue().toString();

                if (adEmail.equals(dbmail) && adPass.equals(dbpass)) {
                    if(TextUtils.isEmpty(adEmail)){
                        Toast.makeText(MainActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextUtils.isEmpty(adPass)){
                        Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setTitle("Loging In");
                    progressDialog.show();

                    startActivity(new Intent(getApplicationContext(), Admin_dashboard.class));
                    txt_email.setText("");
                    txt_pass.setText("");
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }}

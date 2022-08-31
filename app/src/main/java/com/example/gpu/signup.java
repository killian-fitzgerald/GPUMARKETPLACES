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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    EditText txtEmail , txtpassword, txtcofirmpass , fullname, username,address,cell;
    Button btn;
    TextView sign;
    RadioButton radioButtonmale , radioButtonfemale;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String gender="";
    Pattern nummber= Pattern.compile(  ".{11,}"  );
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +        //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtEmail= findViewById(R.id.email);
        txtpassword=findViewById(R.id.pass);
        txtcofirmpass=findViewById(R.id.con);
        cell=findViewById(R.id.mbl);
        address=findViewById(R.id.addresss);
        fullname=findViewById(R.id.full);
        radioButtonfemale=findViewById(R.id.female);
        radioButtonmale=findViewById(R.id.male);
        sign=findViewById(R.id.here);
        btn=findViewById(R.id.regbtn);

        databaseReference= FirebaseDatabase.getInstance().getReference("make");
        firebaseAuth= FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullnamed = fullname.getText().toString();
                final String email = txtEmail.getText().toString();
                final String cellno= cell.getText().toString();
                final String add=address.getText().toString();
                String pass=txtpassword.getText().toString();
                String conf= txtcofirmpass.getText().toString();



                if(radioButtonmale.isChecked()){
                    gender="Male";
                }
                if ((radioButtonfemale.isChecked())){
                    gender="Female";
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(signup.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(signup.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(conf)){
                    Toast.makeText(signup.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!nummber.matcher(cellno).matches()){
                    Toast.makeText(signup.this, "Please provide correct number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!PASSWORD_PATTERN.matcher(pass).matches()){
                    Toast.makeText(signup.this, "Required 6 digits combination of Alphabets(one Upper Case) and Numbers ", Toast.LENGTH_SHORT).show();
                    return;
                }



                if(pass.equals(conf)&& PASSWORD_PATTERN.matcher(pass).matches()){
                    final ProgressDialog progressDialog = new ProgressDialog(signup.this);
                    progressDialog.setTitle("Please Wait ");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // create the user info object
                                        make inform =new make(
                                                fullnamed,
                                                cellno,
                                                email,
                                                gender,
                                                add);
                                   // save the all user info in the firebase
                                        FirebaseDatabase.getInstance().getReference("make").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(inform).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(signup.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                                // open the welcom screen after sucessfull signup
                                                startActivity(new Intent(getApplicationContext(),Welcome.class));
                                                fullname.setText("");
                                                txtEmail.setText("");
                                                txtcofirmpass.setText("");
                                                txtpassword.setText("");
                                                finish();


                                            }
                                        });



                                    } else {
                                        Toast.makeText(signup.this, "Authentication Failed", Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });

                }}
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open the login screen
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }




}


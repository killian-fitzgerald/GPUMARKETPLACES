package com.example.gpu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Order_detail_acivity extends AppCompatActivity {
    Spinner spinner;
    ImageView imageView;
    TextView name,price,description,detailss ;
    String  n,m,img;
    Picasso picss;
    String imageipload;
    String key = "";
    Button shopping,cart,button;
    Intent intent;
    String [] quantity;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_acivity);


        cart=findViewById(R.id.addtocart);
        button=findViewById(R.id.delete);
        spinner=findViewById(R.id.spinner);
        imageView=findViewById(R.id.picofclothitem);
        name=findViewById(R.id.nameofclothtime);
        detailss=findViewById(R.id.priceafterquantity);
        price=findViewById(R.id.priceofclothitems);
        description=findViewById(R.id.descriptionofclothitem);
        quantity=getResources().getStringArray(R.array.itemquantity);
        shopping=findViewById(R.id.continueshoppingcloth);
        ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_spinner_item,quantity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                n = quantity[position];
                Integer valu= Integer.parseInt(n);
                Integer value= Integer.parseInt(price.getText().toString());
                Integer v =  valu * value;
                detailss.setText(Integer.toString(v));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});



        final Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            description.setText(bundle.getString("description"));
            img =  bundle.getString("image");
            price.setText(bundle.getString("price"));
            name.setText(bundle.getString("name"));
            key=bundle.getString("keyvalue");
            Picasso.with(context).load(img).into(imageView);
            m=bundle.getString("quantity");
            Integer value =    Integer.parseInt(m);
            Integer valuee=value-1;
            spinner.setSelection(valuee);
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            final String userkey=currentFirebaseUser.getUid();


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference firebaseDatabase1 = FirebaseDatabase.getInstance().getReference("make").child(userkey).child("myorders");
                    firebaseDatabase1.child(key).removeValue();
                    startActivity(new Intent(getApplicationContext(),My_order.class));
                    Toast.makeText(context, "Order Record Deleted", Toast.LENGTH_SHORT).show();
                }
            });


            shopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Order_detail_acivity.this,Order_Activity.class);
                    intent.putExtra("price",price.getText());
                    intent.putExtra("name",name.getText());
                    intent.putExtra("key",key);
                    intent.putExtra("pic",getIntent().getStringExtra("image"));
                    intent.putExtra("total",detailss.getText());
                    intent.putExtra("des",description.getText());
                    intent.putExtra("quantity",n);
                    startActivity(intent);
                }
            });



        }
}}

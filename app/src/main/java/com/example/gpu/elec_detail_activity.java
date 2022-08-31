package com.example.gpu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class elec_detail_activity extends AppCompatActivity {

    Spinner spinner;
    ImageView imageView;
    TextView name,price,description,detail ;
    String  n,img;
    Picasso picss;
    String imageipload;
    String key = "";
    String [] quantity;
    Button shop,cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elec_detail_activity);

        shop=findViewById(R.id.continueshopping);
        cart=findViewById(R.id.addtocart);

        spinner=findViewById(R.id.spinner);
        imageView=findViewById(R.id.picofelecitem);
        name=findViewById(R.id.nameofelectime);
        detail=findViewById(R.id.priceafterquantity);
        price=findViewById(R.id.priceofelecitems);
        description=findViewById(R.id.descriptionofelecitem);
        quantity=getResources().getStringArray(R.array.itemquantity);
        ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_spinner_item,quantity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                n = quantity[position];
                Double valu= Double.parseDouble(n);
                Double value= Double.parseDouble(price.getText().toString());
                Double v =  valu * value;
                detail.setText(Double.toString(v));

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
            Picasso.with(elec_detail_activity.this).load(img).into(imageView);

            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//                    final String userkey=currentFirebaseUser.getUid();
                    imageipload=bundle.getString("image");
                    if (currentFirebaseUser==null){

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }else

                    information();
                    Toast.makeText(elec_detail_activity.this, "Item Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });


            shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//                    final String userkey=currentFirebaseUser.getUid();
                    if(currentFirebaseUser==null){
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    }else {
                        Intent intent = new Intent(elec_detail_activity.this,Order_Activity.class);
                   intent.putExtra("price",price.getText().toString());
                    intent.putExtra("name",name.getText().toString());
                        intent.putExtra("key",key);
                    intent.putExtra("imageoforder",img);
                   intent.putExtra("pic",getIntent().getStringExtra("image"));
                   intent.putExtra("des",description.getText().toString());
                    intent.putExtra("quantity",n);
                        intent.putExtra("total",detail.getText().toString());
                    startActivity(intent);
                }}
            });


        }

    }

    public void information(){
        final ProgressDialog progressDialog = new ProgressDialog(elec_detail_activity.this);
        progressDialog.setTitle("Uploading Item");
        progressDialog.show();
        cartdata cartdata= new cartdata(
                imageipload,
                name.getText().toString(),
                n,
                price.getText().toString(),
                detail.getText().toString(),
                description.getText().toString(),
                getCurrentDataTime()
        );

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final String userkey=currentFirebaseUser.getUid();


        DatabaseReference dref= FirebaseDatabase.getInstance().getReference("make").child(userkey).child("mycart");
        String s=dref.push().getKey();
        dref.child(s).setValue(cartdata);
        progressDialog.dismiss();

}
  public String getCurrentDataTime(){
      Date today = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
      String dateToStr = format.format(today);
      return dateToStr;
  }
}

package com.example.gpu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class detailactivity extends AppCompatActivity {

    Spinner spinner;
    ImageView imageView;
    TextView name,price,description,detail ;
   String  n,img;
    String imageipload;
    Picasso picss;
    Button shop,cart;

    String [] quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailactivity);

        spinner=findViewById(R.id.spinner);
        imageView=findViewById(R.id.picofitem);

        cart=findViewById(R.id.addtocart);

        name=findViewById(R.id.nameoftime);
        shop=findViewById(R.id.continueshopping);
        detail=findViewById(R.id.priceafterquantity);
        price=findViewById(R.id.priceofitems);
        description=findViewById(R.id.descriptionofitem);
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
            Picasso.with(detailactivity.this).load(img).into(imageView);


        }

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
              imageipload = bundle.getString("image");
              // if user is not login first have to login
              if (currentFirebaseUser==null){
                  startActivity(new Intent(getApplicationContext(),MainActivity.class));
              }else
                  // call the function to add item to the cart
              information();
                Toast.makeText(detailactivity.this, "Item Added to Cart", Toast.LENGTH_SHORT).show();

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

// open the order screen and pass the data using intent
                Intent intent = new Intent(detailactivity.this,Order_Activity.class);
                intent.putExtra("price",price.getText());
                intent.putExtra("name",name.getText());
                Drawable drawable=imageView.getDrawable();
                Bitmap bitmap= ((BitmapDrawable)drawable).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                intent.putExtra("imageoforder",img);
                intent.putExtra("pic",b);
                intent.putExtra("des",description.getText());
                intent.putExtra("quantity",n);
                startActivity(intent);}
            }
        });


    }

    public void information(){
        final ProgressDialog progressDialog = new ProgressDialog(detailactivity.this);
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

          // update the cart info
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

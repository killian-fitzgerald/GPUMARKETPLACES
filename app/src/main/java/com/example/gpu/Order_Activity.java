package com.example.gpu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order_Activity extends AppCompatActivity {


     TextView name,price,quantity,orderquantity,orderprice,cell,detailpricetotal;
     ImageView orderimage;
    FirebaseDatabase firebaseDatabase1;
    Bitmap bmp;
    String desc,temp,te;
     EditText address;
     String key = "";
     Button placeorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_);
        orderimage=findViewById(R.id.orderimage);
        placeorder=findViewById(R.id.placeyourorder);

        name=findViewById(R.id.ordername);
        price=findViewById(R.id.orderprice);
        quantity=findViewById(R.id.orderquantity);
        orderquantity=findViewById(R.id.detailorderquantity);
        orderprice=findViewById(R.id.detailorderprice);
        address=findViewById(R.id.adrressorder);
        detailpricetotal=findViewById(R.id.detailordertotalprice);
        cell=findViewById(R.id.cellno);
        firebaseDatabase1= FirebaseDatabase.getInstance();




        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final String userkey=currentFirebaseUser.getUid();


        firebaseDatabase1.getReference("make").child(userkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cellnumber = dataSnapshot.child("cell").getValue().toString();
                String Adres = dataSnapshot.child("address").getValue().toString();
                cell.setText(cellnumber);
                address.setText(Adres);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder= new  AlertDialog.Builder( Order_Activity.this);
                builder.setMessage("Confirm Your Order  ?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                information();
                                Intent intent = new Intent(Order_Activity.this,Order_slip.class);
                                intent.putExtra("name",name.getText().toString());
                                intent.putExtra("price",price.getText().toString());
                                intent.putExtra("quantity",quantity.getText().toString());
                                intent.putExtra("total",detailpricetotal.getText().toString());
                                startActivity(intent);


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();


                            }
                        });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });







    }

    @Override
    protected void onStart() {
        price.setText(getIntent().getStringExtra("price"));
        Picasso.with(this).load(getIntent().getStringExtra("pic")).into(orderimage);
        name.setText(getIntent().getStringExtra("name"));
        desc=getIntent().getStringExtra("des");
        key=getIntent().getStringExtra("key");
        te=getIntent().getStringExtra("imageoforder");
        quantity.setText(getIntent().getStringExtra("quantity"));
        orderquantity.setText(getIntent().getStringExtra("quantity"));

        Integer valu= Integer.parseInt(quantity.getText().toString());
        Integer value= Integer.parseInt(price.getText().toString());
        Integer v =  valu * value;
        detailpricetotal.setText(Integer.toString(v));
        orderprice.setText(Integer.toString(v));
        super.onStart();
    }

    public void information(){

        cartdata cartdata= new cartdata(
                te,
                name.getText().toString(),
                quantity.getText().toString(),
                price.getText().toString(),
                detailpricetotal.getText().toString(),
               desc,
                getCurrentDataTime()
        );

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final String userkey=currentFirebaseUser.getUid();


        DatabaseReference dref= FirebaseDatabase.getInstance().getReference("make").child(userkey).child("myorders");
        String s=dref.push().getKey();
        dref.child(s).setValue(cartdata);

}
    public String getCurrentDataTime(){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String dateToStr = format.format(today);
        return dateToStr;
    }
}

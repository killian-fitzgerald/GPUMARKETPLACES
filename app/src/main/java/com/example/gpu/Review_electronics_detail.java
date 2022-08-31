package com.example.gpu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Review_electronics_detail extends AppCompatActivity {
    ImageView don;
    EditText description,price,model,discount;
    Button button , btn;
    String img,key;
    String u ="i";
    Uri uri;
    Integer n=1;

    String Imageurl;
    Picasso picss;
    Spinner myspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_electronics_detail);
        discount=findViewById(R.id.discount);
        don=findViewById(R.id.imagennnn);
        description=findViewById(R.id.description);
        price=findViewById(R.id.price);
        button=findViewById(R.id.updateitem);
        model=findViewById(R.id.model);
        btn= findViewById(R.id.deleteitem);
        final Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            description.setText(bundle.getString("description"));
            img =  bundle.getString("image");
            price.setText(bundle.getString("price"));
            model.setText(bundle.getString("name"));
            discount.setText(bundle.getString("discount"));
            Picasso.with(Review_electronics_detail.this).load(img).into(don);
            key= bundle.getString("key");

        }
        don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photopicker = new Intent(Intent.ACTION_PICK);
                photopicker.setType("image/*");
                startActivityForResult(photopicker,1);

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("GPU");
                databaseReference.child(key).removeValue();
                Toast.makeText(Review_electronics_detail.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Review_Gpu.class));

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Imageurl==null){
                    data();
                }
                else if (Imageurl!=null){

                    image();

                }

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK ) {
            uri= data.getData();
            don.setImageURI(uri);
            Imageurl=u;
        }
        else {
            Toast.makeText(Review_electronics_detail.this, "Image not Picked", Toast.LENGTH_SHORT).show();
        }

    }

    public void image(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("pics").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlimage=uriTask.getResult();
                Imageurl=urlimage.toString();
                upload();


            }

        });}

    public void upload(){
        final ProgressDialog progressDialog=new ProgressDialog(Review_electronics_detail.this);
        progressDialog.setMessage("Updating");
        progressDialog.show();
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =firebaseDatabase.getReference("GPU");
        String name = model.getText().toString();
        String des=description.getText().toString();
        String dis=discount.getText().toString();
        String pri=price.getText().toString();
        databaseReference.child(key).child("description").setValue(des);
        databaseReference.child(key).child("price").setValue(pri);
        databaseReference.child(key).child("name").setValue(name);
        databaseReference.child(key).child("image").setValue(Imageurl);
        databaseReference.child(key).child("discount").setValue(dis);
        Toast.makeText(Review_electronics_detail.this, "Item Updated", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();

    }
    public void data(){
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =firebaseDatabase.getReference("GPU");
        String name = model.getText().toString();
        String des=description.getText().toString();
        String dis=discount.getText().toString();
        String pri=price.getText().toString();
        databaseReference.child(key).child("description").setValue(des);
        databaseReference.child(key).child("price").setValue(pri);
        databaseReference.child(key).child("name").setValue(name);
        databaseReference.child(key).child("discount").setValue(dis);
        Toast.makeText(Review_electronics_detail.this, "Item Updated", Toast.LENGTH_SHORT).show();

    }
}

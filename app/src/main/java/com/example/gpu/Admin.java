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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class Admin extends AppCompatActivity {

    ImageView don;
    EditText description,price,model,discount;
    Button button , btn;

    String n;
    Uri uri;
    String Imageurl;
    String[] name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportActionBar().setTitle("Admin");

        discount=findViewById(R.id.discount);
        don=findViewById(R.id.imagennnn);
        description=findViewById(R.id.description);
        price=findViewById(R.id.price);
        button=findViewById(R.id.select);
        model=findViewById(R.id.model);
        btn= findViewById(R.id.upload);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call the function of upload image
                uploadimage();
            }
        });





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery for image selection
                Intent photopicker = new Intent(Intent.ACTION_PICK);
                photopicker.setType("image/*");
                startActivityForResult(photopicker,1);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK ) {
            uri= data.getData();
            // set the uri to the image
            don.setImageURI(uri);
        }
        else {
            Toast.makeText(Admin.this, "Image not Picked", Toast.LENGTH_SHORT).show();
        }

}

    public void uploadimage(){
        final ProgressDialog progressDialog = new ProgressDialog(Admin.this);
        progressDialog.setTitle("Uploading Item");
        progressDialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("pics").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlimage=uriTask.getResult();
                Imageurl=urlimage.toString();
                information();
                Toast.makeText(Admin.this, "Item Uploaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });}



    public void information(){
       data data=new data(

                Imageurl,
               description.getText().toString(),
               price.getText().toString(),
                model.getText().toString(),
               discount.getText().toString()

        );


        DatabaseReference dref= FirebaseDatabase.getInstance().getReference("GPU");
        String s=dref.push().getKey();
        dref.child(s).setValue(data);


}}

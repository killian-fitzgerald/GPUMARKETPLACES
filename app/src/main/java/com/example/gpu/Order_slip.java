package com.example.gpu;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.gpu.NotificationService.MyNotificationPublisher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class Order_slip extends AppCompatActivity {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    Button button;
    ImageView imageView;
    TextView slipname,orderprice,quantityoforder,totalpriceof,cell,adress;
    FirebaseDatabase firebaseDatabase1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_slip);
        button=findViewById(R.id.saveyourslip);
        imageView=findViewById(R.id.happy);
        slipname=findViewById(R.id.slipname);
        orderprice=findViewById(R.id.orderprice);
        cell=findViewById(R.id.cell);
        adress=findViewById(R.id.adress);
        quantityoforder=findViewById(R.id.quantityoforder);
        totalpriceof=findViewById(R.id.totalpriceof);
        getSupportActionBar().setTitle("Order Slip");
        final Bundle bundle = getIntent().getExtras();
        Intent mIntent = getIntent();
        if(bundle != null){
            slipname.setText(bundle.getString("name"));
            orderprice.setText(bundle.getString("price"));
            quantityoforder.setText(bundle.getString("quantity"));
            totalpriceof.setText(bundle.getString("total"));
        }

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final String userkey=currentFirebaseUser.getUid();
        firebaseDatabase1=FirebaseDatabase.getInstance();

        firebaseDatabase1.getReference("make").child(userkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cellnumber = dataSnapshot.child("cell").getValue().toString();
                String Adres = dataSnapshot.child("address").getValue().toString();
                cell.setText(cellnumber);
                adress.setText(Adres);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RelativeLayout relativeLayout = findViewById(R.id.main);
                relativeLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        // call the screen shot function
                        Bitmap b = takeScreenShot(relativeLayout);
                        try {
                            if(b!=null){
                                // call the notification function
                                scheduleNotification(getNotification( "Thank for using our app your order will be delivered" ) , 5000 ) ;
                                 // call the save screen function
                                SaveSreenShoot(b);
                                Toast.makeText(Order_slip.this,"Check your \n slip in internal storage",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

            }
        });


    }

    private Bitmap takeScreenShot(View v) {
        Bitmap screen = null;
        try {
            int width = v.getMeasuredWidth();
            int hight = v.getMeasuredHeight();
            screen = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(screen);
            v.draw(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screen;
    }

    private void SaveSreenShoot(Bitmap b) {
        ByteArrayOutputStream bao = null;
        File file = null;
        try {
            bao= new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG,40,bao);
            file=new File(Environment.getExternalStorageDirectory()+File.separator+"Patient Slip.png");
            file.createNewFile();
            FileOutputStream f=new FileOutputStream(file);
            f.write(bao.toByteArray());
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scheduleNotification (Notification notification , int delay) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + delay ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }

    private Notification getNotification (String content) {
        Intent notificationIntent = new Intent(this , MainActivity. class ) ;
        PendingIntent resultIntent = PendingIntent. getActivity (this , 0 , notificationIntent , 0 ) ;
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Scheduled Notification" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        builder.setContentIntent(resultIntent) ;
        return builder.build() ;

    }
    }

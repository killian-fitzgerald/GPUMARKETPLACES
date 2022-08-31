package com.example.gpu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Welcome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseDatabase firebaseDatabase1;


    BottomNavigationView b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        b=findViewById(R.id.bottomnav);
        toolbar=findViewById(R.id.toolbar);
        NavigationView navigationView= findViewById(R.id.navigation);


        View headerView = navigationView.getHeaderView(0);
        final TextView navUsername = (TextView) headerView.findViewById(R.id.user);

        firebaseDatabase1= FirebaseDatabase.getInstance();

        FirebaseUser currentFirebaseUser3 = FirebaseAuth.getInstance().getCurrentUser() ;
      if (currentFirebaseUser3==null){
          navUsername.setText("Not Signed In");
      }
      else {
          final String userkey = currentFirebaseUser3.getUid();


          firebaseDatabase1.getReference("make").child(userkey).addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                  String name = dataSnapshot.child("fullNaame").getValue().toString();
                  navUsername.setText(name);


              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });
      }

        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawe_layout);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new main()).commit();



        b.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment selectedFragment = null;

                switch (menuItem.getItemId()){
                    case R.id.action_home:

                        selectedFragment = new main();
                        break;
                    case R.id.action_elec:
                     selectedFragment = new BlankFragment();
                        break;
                    case R.id.mycarttttttt:
                        startActivity(new Intent(getApplicationContext(),MyCart.class));
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectedFragment).commit();
                return true;
            }
        });
    }




    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }
        else {

            super.onBackPressed();


        }}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
               startActivity(new Intent(getApplicationContext(),About.class));
                break;
            case R.id.mycarttttttt:
                FirebaseUser currentFirebaseUser2 = FirebaseAuth.getInstance().getCurrentUser() ;
                if(currentFirebaseUser2==null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else
                startActivity(new Intent(getApplicationContext(),MyCart.class));
                break;
            case R.id.myorder:
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                if(currentFirebaseUser==null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else
                startActivity(new Intent(getApplicationContext(),My_order.class));
                break;
            case R.id.lohouttt:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
            case R.id.profile:
                FirebaseUser currentFirebaseUser1 = FirebaseAuth.getInstance().getCurrentUser() ;

                if(currentFirebaseUser1==null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else
                    startActivity(new Intent(getApplicationContext(),profile.class));

                break;

        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_shoes) {
            FirebaseUser currentFirebaseUser1 = FirebaseAuth.getInstance().getCurrentUser() ;
            if(currentFirebaseUser1==null){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show();
            }else
                startActivity(new Intent(getApplicationContext(),MyCart.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

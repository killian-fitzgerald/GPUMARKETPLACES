package com.example.gpu;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class main extends Fragment {

    MaterialToolbar toolbar;
    TextView textView;
    TabLayout tabLayout;
    ViewPager viewPager;
    RecyclerView recyclerView;
    List<data> list, list1;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;


    public main() {
        //empty
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        textView = v.findViewById(R.id.category);
        bottomNavigationView = v.findViewById(R.id.bottomnav);
        toolbar = v.findViewById(R.id.toolbar);
        viewPager = v.findViewById(R.id.viewPager);

        Slider_Adapter viewPagerAdapter = new Slider_Adapter(getContext());

        viewPager.setAdapter(viewPagerAdapter);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Items");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new mytimertask(), 4000, 4000);






        recyclerView=v.findViewById(R.id.mainrecycler);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getContext(),2);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        list=new ArrayList<>();
        final Main_Recycler main_recycler= new Main_Recycler(list,getContext());
        recyclerView.setAdapter(main_recycler);
        databaseReference= FirebaseDatabase.getInstance().getReference("GPU");
        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    data data = dataSnapshot1.getValue(data.class);
                    list.add(data);
                }main_recycler.notifyDataSetChanged();
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
        return v;
    }

    public class mytimertask extends TimerTask{

        @Override
        public void run() {

            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem()==0){
                        viewPager.setCurrentItem(1);
                    }else if (viewPager.getCurrentItem()==1 ){
                        viewPager.setCurrentItem(2);
                    }else viewPager.setCurrentItem(0);

                }
            });

        }
    }

}

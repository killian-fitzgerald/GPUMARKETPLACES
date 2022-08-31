package com.example.gpu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Elec_Recycler extends RecyclerView.Adapter<electronics>{
    List<data> electronicslist;
    private int lastposition = -1;
    Context context;

    public Elec_Recycler(List<data> electronicslist, Context context) {
        this.electronicslist = electronicslist;
        this.context = context;
    }

    @NonNull
    @Override
    public electronics onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new electronics(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull final electronics holder, int position) {

        holder.name.setText(electronicslist.get(position).name);
        holder.price.setText(electronicslist.get(position).price);
        holder.description.setText(electronicslist.get(position).description);
        Picasso.with(context).load(electronicslist.get(position).getImage()).into(holder.imageView);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,elec_detail_activity.class);
                intent.putExtra("image",electronicslist.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("name",electronicslist.get(holder.getAdapterPosition()).getName());
                intent.putExtra("price",electronicslist.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("description",electronicslist.get(holder.getAdapterPosition()).getDescription());
                context.startActivity(intent);


            }
        });
        setAnimations(holder.itemView,position);


    }


    public void setAnimations(View viewtoanimate, int position){
        if(position>lastposition){

            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.0f);
            scaleAnimation.setDuration(1500);
            viewtoanimate.startAnimation(scaleAnimation);
            lastposition=position;

        }




    }

    @Override
    public int getItemCount() {
        return electronicslist.size() ;
    }

    public void filteredlist(ArrayList<data> filterlist) {

        electronicslist=filterlist;
        notifyDataSetChanged();
    }
}

class electronics extends RecyclerView.ViewHolder {
    TextView price,description,name;
    ImageView imageView;
    CardView cardView;
    public electronics(@NonNull View itemView) {
        super(itemView);

        price=itemView.findViewById(R.id.itemprice);
        description=itemView.findViewById(R.id.itemdescription);
        name=itemView.findViewById(R.id.itemname);
        imageView=itemView.findViewById(R.id.shoespic);
        cardView=itemView.findViewById(R.id.mycardview);

    }
}

package com.example.gpu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Main_Recycler extends RecyclerView.Adapter<electronicsmain>{
    List<data> electronicslist;
    Context context;

    public Main_Recycler(List<data> electronicslist, Context context) {
        this.electronicslist = electronicslist;
        this.context = context;
    }

    @NonNull
    @Override
    public electronicsmain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(parent.getContext()).inflate(R.layout.maindesign,parent,false);
        return new electronicsmain(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull final electronicsmain holder, int position) {


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

    }

    @Override
    public int getItemCount() {
        return electronicslist.size() ;
    }
}

class electronicsmain extends RecyclerView.ViewHolder {
    TextView price,description,name;
    ImageView imageView;
    CardView cardView;
    public electronicsmain(@NonNull View itemView) {
        super(itemView);

        price=itemView.findViewById(R.id.itemprice);
        description=itemView.findViewById(R.id.itemdescription);
        name=itemView.findViewById(R.id.itemname);
        imageView=itemView.findViewById(R.id.shoespic);
        cardView=itemView.findViewById(R.id.mycardview1);
    }
}

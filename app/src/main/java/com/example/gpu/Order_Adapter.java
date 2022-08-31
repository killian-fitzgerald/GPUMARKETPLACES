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

public class Order_Adapter extends RecyclerView.Adapter<oder> {

    List<cartdata> cartdataList;
    Context context;

    public Order_Adapter(List<cartdata> cartdataList, Context context) {
        this.cartdataList = cartdataList;
        this.context = context;
    }

    @NonNull
    @Override
    public oder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartdesign,parent,false);
        return new oder(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull final oder holder, int position) {

        Picasso.with(context).load(cartdataList.get(position).getImagecart()).into(holder.imageView);
        holder.cartname.setText(cartdataList.get(position).namecart);
        holder.cartquantity.setText(cartdataList.get(position).quantitycart);
        holder.carttotalprice.setText(cartdataList.get(position).totalpricecart);
        holder.cartdescription.setText(cartdataList.get(position).description);
        holder.cartprice.setText(cartdataList.get(position).pricecart);
        holder.datetime.setText(cartdataList.get(position).datetime);

        holder.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,Order_detail_acivity.class);
                intent.putExtra("quantity",cartdataList.get(holder.getAdapterPosition()).getQuantitycart());
                intent.putExtra("image",cartdataList.get(holder.getAdapterPosition()).getImagecart());
                intent.putExtra("name",cartdataList.get(holder.getAdapterPosition()).getNamecart());
                intent.putExtra("price",cartdataList.get(holder.getAdapterPosition()).getPricecart());
                intent.putExtra("keyvalue",cartdataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("description",cartdataList.get(holder.getAdapterPosition()).getDescription());
                context.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return cartdataList.size();
    }
}

class oder extends RecyclerView.ViewHolder {
   CardView cardView1;
   ImageView imageView;
   TextView cartname,cartprice,cartdescription,carttotalprice,cartquantity,datetime;

    public oder(@NonNull View itemView) {
        super(itemView);
        cartname=itemView.findViewById(R.id.cartname);
        datetime=itemView.findViewById(R.id.datetime);
        imageView=itemView.findViewById(R.id.cartimage);
        cartprice=itemView.findViewById(R.id.cartprice);
        cartdescription=itemView.findViewById(R.id.cartdescription);
        carttotalprice=itemView.findViewById(R.id.carttotalprice);
        cartquantity=itemView.findViewById(R.id.cartquantity);
        cardView1=itemView.findViewById(R.id.cartcardview);
    }
}
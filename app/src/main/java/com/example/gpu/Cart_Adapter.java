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

public class Cart_Adapter extends RecyclerView.Adapter<cart> {

    List<cartdata> cartdataList;
    Context context;

    public Cart_Adapter(List<cartdata> cartdataList, Context context) {
        this.cartdataList = cartdataList;
        this.context = context;
    }

    @NonNull
    @Override
    public cart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartdesign,parent,false);
        return new cart(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull final cart holder, int position) {



        Picasso.with(context).load(cartdataList.get(position).getImagecart()).into(holder.imageView);
        holder.cartname.setText(cartdataList.get(position).namecart);
       holder.cartquantity.setText(cartdataList.get(position).quantitycart);
       holder.carttotalprice.setText(cartdataList.get(position).totalpricecart);
       holder.cartdescription.setText(cartdataList.get(position).description);
       holder.cartprice.setText(cartdataList.get(position).pricecart);

       holder.cardView1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(context,cart_detail_activity.class);
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
 class cart extends RecyclerView.ViewHolder {
    CardView cardView1;
    ImageView imageView;
    TextView cartname,cartprice,cartdescription,carttotalprice,cartquantity;

    public cart(@NonNull View itemView) {
        super(itemView);
        cartname=itemView.findViewById(R.id.cartname);
        imageView=itemView.findViewById(R.id.cartimage);
        cartprice=itemView.findViewById(R.id.cartprice);
        cartdescription=itemView.findViewById(R.id.cartdescription);
        carttotalprice=itemView.findViewById(R.id.carttotalprice);
        cartquantity=itemView.findViewById(R.id.cartquantity);
        cardView1=itemView.findViewById(R.id.cartcardview);
    }
}
package com.ngtszlong.eztryclothes_company.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngtszlong.eztryclothes_company.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Order> orderArrayList;
    double Total = 0;

    public OrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orderdetail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderArrayList.get(position);
        if (!order.getImage().equals("")) {
            Picasso.get().load(order.getImage()).into(holder.img_order_image);
        }
        holder.txt_order_name.setText(order.getName());
        holder.txt_order_itemprice.setText(order.getPrice());
        holder.quantity.setText(order.getQuantity());
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_order_image;
        TextView txt_order_name;
        TextView txt_order_itemprice;
        TextView date;
        TextView quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_order_image = itemView.findViewById(R.id.img_order_image);
            txt_order_name = itemView.findViewById(R.id.txt_order_name);
            txt_order_itemprice = itemView.findViewById(R.id.txt_order_itemprice);
            date = itemView.findViewById(R.id.txt_date);
            quantity = itemView.findViewById(R.id.txt_order_quantity);
        }
    }
}


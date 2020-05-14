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

import org.w3c.dom.Text;

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
        holder.address.setText(order.getAddress());
        holder.txt_order_no.setText("Order"+order.getStr());
        holder.txt_order_name.setText(order.getName());
        holder.txt_order_itemprice.setText(order.getPrice());
        holder.quantity.setText(order.getQuantity());
        holder.name.setText(order.getCustomername());
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_order_image;
        TextView txt_order_name;
        TextView txt_order_itemprice;
        TextView txt_order_no;
        TextView date;
        TextView quantity;
        TextView address;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_order_no = itemView.findViewById(R.id.txt_order_no);
            img_order_image = itemView.findViewById(R.id.img_order_image);
            txt_order_name = itemView.findViewById(R.id.txt_order_name);
            txt_order_itemprice = itemView.findViewById(R.id.txt_order_itemprice);
            address = itemView.findViewById(R.id.txt_order_address);
            date = itemView.findViewById(R.id.txt_date);
            quantity = itemView.findViewById(R.id.txt_order_quantity);
            name = itemView.findViewById(R.id.txt_cus_name);
        }
    }
}


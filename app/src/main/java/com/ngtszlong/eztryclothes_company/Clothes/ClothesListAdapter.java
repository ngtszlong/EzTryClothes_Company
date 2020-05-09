package com.ngtszlong.eztryclothes_company.Clothes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ngtszlong.eztryclothes_company.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClothesListAdapter extends RecyclerView.Adapter<ClothesListAdapter.ViewHolder> {
    Context context;
    ArrayList<ClothesList>clothesListArrayList;

    public ClothesListAdapter(Context context, ArrayList<ClothesList> clothesListArrayList) {
        this.context = context;
        this.clothesListArrayList = clothesListArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClothesList clothesList = clothesListArrayList.get(position);
        holder.textView.setText(clothesList.getName_Eng());
        Picasso.get().load(clothesList.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return clothesListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_cloth_img);
            textView = itemView.findViewById(R.id.txt_cloth_name);
        }
    }
}

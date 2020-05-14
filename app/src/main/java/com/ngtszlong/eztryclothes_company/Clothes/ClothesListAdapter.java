package com.ngtszlong.eztryclothes_company.Clothes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ClothesList clothesList = clothesListArrayList.get(position);
        holder.textView.setText(clothesList.getName_Eng());
        if (!clothesList.getImage().equals("")){
            Picasso.get().load(clothesList.getImage()).into(holder.imageView);
        }
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, EditClothesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("no", clothesList.getNo());
                context.getApplicationContext().startActivity(intent);
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                FirebaseUser user = fAuth.getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Clothes").child(clothesList.getNo());
                reference.removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return clothesListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        Button btn_edit;
        ImageButton imageButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_cloth_img);
            textView = itemView.findViewById(R.id.txt_cloth_name);
            btn_edit = itemView.findViewById(R.id.btn_cloth_edit);
            imageButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}

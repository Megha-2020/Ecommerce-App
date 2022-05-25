package com.example.ecommerceapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.Activities.ShowAllActivity;
import com.example.ecommerceapp.Models.CategoryModel;
import com.example.ecommerceapp.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List< CategoryModel > list;

    public CategoryAdapter(Context context, List< CategoryModel > list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.cartImg);
        holder.cartName.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cartImg;
        TextView  cartName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImg = itemView.findViewById(R.id.cat_img);
            cartName = itemView.findViewById(R.id.cat_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowAllActivity.class);
                    intent.putExtra("type",list.get(getAdapterPosition()).getType());
                    context.startActivity(intent);
                }
            });
        }
    }
}

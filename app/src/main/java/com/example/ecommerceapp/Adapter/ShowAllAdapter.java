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
import com.example.ecommerceapp.Activities.DetailedActivity;
import com.example.ecommerceapp.Models.ShowAllModel;
import com.example.ecommerceapp.R;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ViewHolder> {

    Context context;
    List<ShowAllModel> showAllModelList;

    public ShowAllAdapter(Context context, List< ShowAllModel > showAllModelList) {
        this.context = context;
        this.showAllModelList = showAllModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(showAllModelList.get(position).getImg_url()).into(holder.mItemImage);
        holder.mName.setText(showAllModelList.get(position).getName());
        holder.mCost.setText("Rs "+showAllModelList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return showAllModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mItemImage;
        private TextView mCost;
        private TextView mName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mItemImage = itemView.findViewById(R.id.item_image);
            mCost = itemView.findViewById(R.id.item_cost);
            mName = itemView.findViewById(R.id.item_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailedActivity.class);
                    i.putExtra("detailed",showAllModelList.get(getAdapterPosition()));
                    context.startActivity(i);
                }
            });
        }
    }
}

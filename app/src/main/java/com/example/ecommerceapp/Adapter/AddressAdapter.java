package com.example.ecommerceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Models.AddressModel;
import com.example.ecommerceapp.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    Context context;
    List< AddressModel > list;
    SelectedAddress selectedAddress;

    private  RadioButton selectedRadioBtn;

    public AddressAdapter(Context context, List< AddressModel > list, SelectedAddress selectedAddress) {
        this.context = context;
        this.list = list;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.address.setText(list.get(position).getUserAddress());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView address;
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (AddressModel address:list){
                        address.setSelected(false);
                    }

                    list.get(getAdapterPosition()).setSelected(true);
                    if (selectedRadioBtn!=null){
                        selectedRadioBtn.setChecked(false);
                    }
                    selectedRadioBtn = (RadioButton) v;
                    selectedRadioBtn.setChecked(true);
                    selectedAddress.setAddress(list.get(getAdapterPosition()).getUserAddress());
                }
            });
        }
    }

    public interface  SelectedAddress {
        void setAddress(String address);
    }
}

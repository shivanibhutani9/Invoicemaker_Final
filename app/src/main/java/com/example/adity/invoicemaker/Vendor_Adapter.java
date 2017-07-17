package com.example.adity.invoicemaker;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shivani on 13/7/17.
 */

public class Vendor_Adapter extends RecyclerView.Adapter<Vendor_Adapter.ViewHolder> {
    public  Context mContext;
    private onItemTouchListener onItemTouchListener;
    ArrayList<Vendor_Details.ObjectVendor> objects;
    Vendor_Adapter(Context mContext, ArrayList<Vendor_Details.ObjectVendor> objects, onItemTouchListener listener
    ){
        this.mContext=mContext;
        this.objects=objects;
        this.onItemTouchListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(mContext).inflate(R.layout.vendor_item_details, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Vendor_Details.ObjectVendor obj=objects.get(position);

        holder.name.setText(obj.v_name);

        holder.email.setText(obj.v_email);

        holder.gstin.setText(obj.v_gstin);

        holder.pan.setText(obj.v_pan);

    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setClickListener(onItemTouchListener itemClickListener) {
        this.onItemTouchListener = itemClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name,gstin,pan,email;
        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.v_name);
           email=(TextView)itemView.findViewById(R.id.v_email);
           gstin=(TextView)itemView.findViewById(R.id.v_gstin);
           pan=(TextView)itemView.findViewById(R.id.v_pan);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemTouchListener.onClick(view,getAdapterPosition());
        }
    }
}

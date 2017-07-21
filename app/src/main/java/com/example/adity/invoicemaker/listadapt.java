package com.example.adity.invoicemaker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adity on 7/13/2017.
 */


public class listadapt extends RecyclerView.Adapter<listadapt.ViewHolder> {
    public  Context mContext;
    ArrayList<String[]> objects;
    String type;
    listadapt(Context mContext, ArrayList<String[]> objects,String type){
        this.mContext=mContext;
        this.objects=objects;
        this.type=type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(mContext).inflate(R.layout.additemlist, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(type.contains("Intra")||type.contains("Receipt")||type.contains("Payment")||type.contains("Debit")||type.contains("Credit")) {
            String[] a = objects.get(position);

            holder.description.setText(a[0]);

            holder.hsn.setText(a[1]);

            holder.Unit_cost.setText(a[4]);

            holder.quantity.setText(a[5]);

            holder.amt.setText(a[6]);
        }
        else if(type.contains("Inter")||type.contains("Export")) {
            String[] a = objects.get(position);

            holder.description.setText(a[0]);

            holder.hsn.setText(a[1]);

            holder.Unit_cost.setText(a[3]);

            holder.quantity.setText(a[4]);

            holder.amt.setText(a[5]);
        }

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView description,hsn,Unit_cost,quantity,amt;
        public ViewHolder(View itemView) {
            super(itemView);
            description=(TextView)itemView.findViewById(R.id.description);
            hsn=(TextView)itemView.findViewById(R.id.hsn);
            Unit_cost=(TextView)itemView.findViewById(R.id.Unit_cost);
            quantity=(TextView)itemView.findViewById(R.id.quantity);
            amt=(TextView)itemView.findViewById(R.id.amt);

        }
    }
}

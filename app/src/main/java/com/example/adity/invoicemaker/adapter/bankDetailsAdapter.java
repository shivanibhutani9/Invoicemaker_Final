package com.example.adity.invoicemaker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adity.invoicemaker.bank_activity.AccPaymentDetailsActivity;
import com.example.adity.invoicemaker.R;
import com.example.adity.invoicemaker.Listener.onItemTouchListener;

import java.util.ArrayList;

/**
 * Created by shivani on 13/7/17.
 */

public class bankDetailsAdapter extends RecyclerView.Adapter<bankDetailsAdapter.ViewHolder> {
    public Context mContext;
    public onItemTouchListener onItemTouchListener;
    public ArrayList<AccPaymentDetailsActivity.ObjectAcc> objects;
    public bankDetailsAdapter(Context mContext, ArrayList<AccPaymentDetailsActivity.ObjectAcc> objects, onItemTouchListener listener
    ){
        this.mContext=mContext;
        this.objects=objects;
        this.onItemTouchListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(mContext).inflate(R.layout.bankitemdetail, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final AccPaymentDetailsActivity.ObjectAcc obj=objects.get(position);

        holder.bankName.setText(obj.bankname);

        holder.Acc_no.setText(obj.accno);

    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setClickListener(onItemTouchListener itemClickListener) {
        this.onItemTouchListener = itemClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
      public TextView Acc_name,Acc_no,bankName,ifsc;
        public ViewHolder(View itemView) {
            super(itemView);
          //  Acc_name=(TextView)itemView.findViewById(R.id.ac_name);
            Acc_no=(TextView)itemView.findViewById(R.id.ac_no);
            bankName=(TextView)itemView.findViewById(R.id.bname);
           // ifsc=(TextView)itemView.findViewById(R.id.ifsc_code);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemTouchListener.onClick(view,getAdapterPosition());
        }
    }
}

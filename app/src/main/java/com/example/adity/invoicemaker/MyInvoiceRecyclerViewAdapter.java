package com.example.adity.invoicemaker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adity.invoicemaker.InvoiceListFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyInvoiceRecyclerViewAdapter extends RecyclerView.Adapter<MyInvoiceRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<InvoiceListFragment.ObjectInv> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyInvoiceRecyclerViewAdapter(ArrayList<InvoiceListFragment.ObjectInv> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_invoice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       final InvoiceListFragment.ObjectInv obj= mValues.get(position);
        holder.InvNo.setText(mValues.get(position).inv_no);
        holder.InvVendor.setText(mValues.get(position).inv_vname);
        holder.InvAmt.setText(mValues.get(position).inv_amt);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(obj);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView InvNo,InvVendor,InvAmt;

        public ViewHolder(View view) {
            super(view);
            InvNo = (TextView) view.findViewById(R.id.in_no);
            InvVendor = (TextView) view.findViewById(R.id.in_vname);
            InvAmt=(TextView)view.findViewById(R.id.in_amt);
        }

    }
}

package com.example.adity.invoicemaker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adity.invoicemaker.Fragments.InvoiceListFragment;
import com.example.adity.invoicemaker.Fragments.InvoiceListFragment.OnListFragmentInteractionListener;
import com.example.adity.invoicemaker.R;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
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
        holder.InvDate.setText(mValues.get(position).inv_date);
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

        public final TextView InvDate,InvVendor,InvAmt;

        public ViewHolder(View view) {
            super(view);
            InvDate = (TextView) view.findViewById(R.id.in_date);
            InvVendor = (TextView) view.findViewById(R.id.in_vname);
            InvAmt=(TextView)view.findViewById(R.id.in_amt);
        }

    }
}

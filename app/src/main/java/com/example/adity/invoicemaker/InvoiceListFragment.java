package com.example.adity.invoicemaker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adity.invoicemaker.dummy.DummyContent;
import com.example.adity.invoicemaker.dummy.DummyContent.DummyItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class InvoiceListFragment extends Fragment {

    // TODO: Customize parameters
    FloatingActionButton fab;
    private int mColumnCount = 1;
    ArrayList<ObjectInv> mValues;
 MyInvoiceRecyclerViewAdapter adapter;
    ProgressDialog pd;
RecyclerView recyclerView;
    String invoiceno,vname,amount;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InvoiceListFragment() {
    mValues=new ArrayList<>();

    Read();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice_list, container, false);

        pd=new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.show();
        // Set the adapter
        if (view !=null) {
            Context context = view.getContext();
            recyclerView = (RecyclerView)view.findViewById(R.id.fragment_invoice);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter=new MyInvoiceRecyclerViewAdapter(mValues, mListener);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }


  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }
*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), typesofinvoice.class));
            }
        });
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT ){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int pos=viewHolder.getAdapterPosition();
                ObjectInv obj=mValues.get(pos);
                AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getActivity())
                        //set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you really want to delete the following invoice?\n\n"+"\t\tInvoice Number -"+obj.inv_no+"\n\t\tVendor Name -"
                                +obj.inv_vname+"\n\t\tAmount -"+obj.inv_amt)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                mValues.remove(pos);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "DELETED", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                            }

                        })

                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();

                            }
                        })
                        .create();
                myQuittingDialogBox.show();
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ObjectInv obj);
    }

    public static class ObjectInv {
        public String inv_no, inv_vname, inv_amt;

        ObjectInv(String inv_no, String inv_vname, String inv_amt) {
            this.inv_no = inv_no;
            this.inv_amt = inv_amt;
            this.inv_vname = inv_vname;
        }
    }
    public void Read()
    {
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Invoice/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());



        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot invoice:dataSnapshot.getChildren())
                {
                     invoiceno=invoice.getKey();
                    for(DataSnapshot ds:invoice.getChildren())
                    {
                        if(ds.getKey().equals("Details"))
                        {
                            for (DataSnapshot details:ds.getChildren())
                            {
                                if(details.getKey().equals("VendorName"))
                                {
                                    vname=details.getValue(String.class);
                                }
                                if(details.getKey().equals("Amount"))
                                {
                                     amount=details.getValue(String.class);
                                }

                            }
                        }
                    }





                    ObjectInv obj=new ObjectInv(invoiceno,vname,amount);
                    mValues.add(obj);
                    adapter.notifyDataSetChanged();

                }



                pd.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "error"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
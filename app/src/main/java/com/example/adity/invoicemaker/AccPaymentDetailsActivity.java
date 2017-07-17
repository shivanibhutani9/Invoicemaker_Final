package com.example.adity.invoicemaker;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.R.drawable.ic_delete;



import java.util.ArrayList;

public class AccPaymentDetailsActivity extends AppCompatActivity implements onItemTouchListener{

    bankDetailsAdapter adapter;
    RecyclerView rv;
    FloatingActionButton fab;
    ArrayList<AccPaymentDetailsActivity.ObjectAcc> arrayList;
    String ifsc,bname,accnum,accname;
    ProgressDialog pd;
    onItemTouchListener onItemTouchListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_acc_payment_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        arrayList=new ArrayList<ObjectAcc>();
        rv= (RecyclerView)findViewById(R.id.list_item);

        adapter =new bankDetailsAdapter(this,arrayList,onItemTouchListener);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        pd=new ProgressDialog(this);
        pd.setMessage("Please Wait ...");
        pd.show();
        Read();
        adapter.setClickListener(this);


        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT ){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int pos=viewHolder.getAdapterPosition();
                ObjectAcc obj=arrayList.get(pos);
                AlertDialog myQuittingDialogBox =new AlertDialog.Builder(AccPaymentDetailsActivity.this)
                        //set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you really want to delete the following bank details?\n\n"+"\t\tAccount Holder -"+obj.accname+"\n\t\tAccount Number -"
                                +obj.accno+"\n\t\tBank Name -"+obj.bankname)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                arrayList.remove(pos);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(AccPaymentDetailsActivity.this, "DELETED", Toast.LENGTH_SHORT).show();

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
        helper.attachToRecyclerView(rv);




        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AccPaymentDetailsActivity.this,BankDetails.class);
                arrayList.clear();
                i.putExtra("Type","BankDetails");
                startActivityForResult(i,6);
            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 5 ){
            //   Toast.makeText(getContext(), "RESULT", Toast.LENGTH_SHORT).show();
            // ObjectAcc Ob= new ObjectAcc(data.getStringExtra("account_holder"),data.getStringExtra("bank_name"),data.getStringExtra("account_number"),data.getStringExtra("ifsc_code"));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view, int position) {

        Bundle extras = this.getIntent().getExtras();
        if(extras!=null){
            String act = extras.getString("from");

            if(act.equals("Invoice")) {
               AccPaymentDetailsActivity.ObjectAcc ob=arrayList.get(position);
                Intent i=new Intent();
                i.putExtra("bank_name",ob.bankname);
                i.putExtra("ifsc_code",ob.ifsc_code);
                i.putExtra("account_holder",ob.accname);
                i.putExtra("account_number",ob.accno);


                setResult(1,i);
                finish();

            }}
        else{
        }

    }

    public static class ObjectAcc{
        public String accno,accname,bankname,ifsc_code;
        ObjectAcc(String name,String bname,String acno,String ifsc){
            accname=name;
            accno=acno;
            bankname=bname;
            ifsc_code=ifsc;
        }

    }


    public void Read()
    {
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Account Details/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());

        // String name,String bname,String acno,String ifsc

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot bank:dataSnapshot.getChildren())
                {

                    bname=bank.getKey();
                    for(DataSnapshot accno:bank.getChildren())
                    {
                        accnum=accno.getKey();
                        for(DataSnapshot details:accno.getChildren())
                        {
                            if(details.getKey().equals("Ifsc Code"))
                            {
                                ifsc=details.getValue(String.class);
                            }
                            else if(details.getKey().equals("Account Holder"))
                            {
                                accname=details.getValue(String.class);
                            }



                        }

                    }
                    ObjectAcc obj=new ObjectAcc(accname,bname,accnum,ifsc);
                    arrayList.add(obj);
                    adapter.notifyDataSetChanged();

                }



                pd.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AccPaymentDetailsActivity.this, "error"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        String from = getIntent().getExtras().getString("from");
        Intent newIntent = null;
        if(from.equals("Invoice")){
            newIntent = new Intent(this, InvoiceGenerate.class);
        }else if(from.equals("profile")){
            //newIntent = new Intent(this,NavigationDrawer.class);
            onBackPressed();
        }
        return newIntent;
    }
}


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccPaymentDetails} factory method to
 * create an instance of this fragment.
 */











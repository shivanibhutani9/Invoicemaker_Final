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

public class Vendor_Details extends AppCompatActivity implements onItemTouchListener{

    Vendor_Adapter adapter;
    RecyclerView rv;
    FloatingActionButton fab;
    ArrayList<ObjectVendor> arrayList;
    String name,email,gstin,pan,address,number;
    ProgressDialog pd;
    onItemTouchListener onItemTouchListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        arrayList=new ArrayList<ObjectVendor>();
        rv= (RecyclerView)findViewById(R.id.vendor_list);

        adapter =new Vendor_Adapter(this,arrayList,onItemTouchListener);

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
               ObjectVendor obj=arrayList.get(pos);
                AlertDialog myQuittingDialogBox =new AlertDialog.Builder(Vendor_Details.this)
                        //set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you really want to delete the following bank details?\n\n"+"\t\tVendor Name-"+obj.v_name+"\n\t\tE-mail -"
                                +obj.v_email)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                arrayList.remove(pos);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(Vendor_Details.this, "DELETED", Toast.LENGTH_SHORT).show();

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
                Intent i=new Intent(Vendor_Details.this,ClientDetails.class);
                arrayList.clear();
                i.putExtra("Type","Vendor");
                startActivityForResult(i,6);
            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 3 ){
            //   Toast.makeText(getContext(), "RESULT", Toast.LENGTH_SHORT).show();
            // ObjectAcc Ob= new ObjectAcc(data.getStringExtra("account_holder"),data.getStringExtra("bank_name"),data.getStringExtra("account_number"),data.getStringExtra("ifsc_code"));
            adapter.notifyDataSetChanged();
        }
    }



    public static class ObjectVendor{
        public String v_name,v_email,v_gstin,v_pan,v_addr,v_phone;
        ObjectVendor(String name,String mail,String gstin,String pan,String addr,String phone){

            v_name=name;
            v_email=mail;
            v_gstin=gstin;
            v_pan=pan;
            v_addr=addr;
            v_phone=phone;
        }

    }

    @Override
    public void onClick(View view, int position) {
        Bundle extras = this.getIntent().getExtras();
        if(extras!=null){
            String act = extras.getString("from");

            if(act.equals("Invoice")) {



                ObjectVendor ob=arrayList.get(position);
                Intent i=new Intent();
        i.putExtra("name",ob.v_name);
        i.putExtra("phone",ob.v_phone);
        i.putExtra("email",ob.v_email);
        i.putExtra("address",ob.v_email);
        i.putExtra("gstin",ob.v_gstin);
        i.putExtra("pan",ob.v_pan);

               setResult(3,i);
               finish();

            }}
        else{

        }
    }

    public void Read()
    {
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Company/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());

        // String name,String bname,String acno,String ifsc

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot Company:dataSnapshot.getChildren())
                {

                    name=Company.getKey();
                    for(DataSnapshot ds:Company.getChildren())
                    {

                        if(ds.getKey().equals("Address"))
                        {
                            address=ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("Email"))
                        {
                            email=ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("Gstin"))
                        {
                            gstin=ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("Pan no"))
                        {
                            pan=ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("Phone"))
                        {
                            number=ds.getValue(String.class);
                        }

                    }
                    ObjectVendor obj=new ObjectVendor(name,email,gstin,pan,address,number);
                    arrayList.add(obj);
                    adapter.notifyDataSetChanged();

                }



                pd.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Vendor_Details.this, "error"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
            newIntent = new Intent(this,NavigationDrawer.class);
        }
        return newIntent;
    }
}


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccPaymentDetails} factory method to
 * create an instance of this fragment.
 */











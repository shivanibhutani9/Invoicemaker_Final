package com.example.adity.invoicemaker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adity.invoicemaker.Fragments.NavigationDrawer;
import com.example.adity.invoicemaker.adapter.listadapt;
import com.example.adity.invoicemaker.bank_activity.AccPaymentDetailsActivity;
import com.example.adity.invoicemaker.invoice_layout.Credit_Note;
import com.example.adity.invoicemaker.invoice_layout.Debit_Note;
import com.example.adity.invoicemaker.invoice_layout.Export_invoice;
import com.example.adity.invoicemaker.invoice_layout.Payment_Voucher;
import com.example.adity.invoicemaker.invoice_layout.Receipt_Voucher;
import com.example.adity.invoicemaker.invoice_layout.tax_invoice1;
import com.example.adity.invoicemaker.invoice_layout.tax_invoice2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.io.File;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class InvoiceGenerate extends AppCompatActivity {
 static TextView dateString;
Uri logopath;
    int ADD_SEAL=99,ADD_STAMP=101;
    ProgressDialog pd;
    String bank,ifsccode,accholder,accno;
     String currentDate;
    String type;
    String state,zip;
    String description,HSNcode,unitcost,quantity,amount;
    String c,ad,cp,user_gst,user_pan,user_phone;
    listadapt adapter;
    RecyclerView rv;
    String Name,Phone,Email,Address,Gstin,Pan_no,sgst,cgst,igst;
    TextView subtotal, dis,Discount1,total;
    Double sub=0.0,discount=0.0,tot=0.0;
    TextView bank_details;
    LinearLayout l,ClientDetails;
    File file;
    String num_to_words="";
    DatabaseReference db;
    TextView invoice;
    static ImageView image;
    String in="";
    int i=1;
    ArrayList<String[]> items;
    ArrayList<String[]> GST;
    Map<String,String> mp;
    ImageButton cal;
    ImageView stamp;
    Uri path=null,StampPath=null;
    TextView noclient,noitem,uploadSign,uploadStamp;
    LinearLayout clients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_gen);
        dateString=(TextView)findViewById(R.id.textdate);
        bank_details=(TextView)findViewById(R.id.bank);
        image=(ImageView)findViewById(R.id.SEAL);
        cal=(ImageButton)findViewById(R.id.calendar);
        type=getIntent().getExtras().getString("type");
        uploadSign=(TextView)findViewById(R.id.uploadSign);
        uploadStamp=(TextView)findViewById(R.id.uploadStamp);
        noitem=(TextView)findViewById(R.id.noitem);
        clients=(LinearLayout)findViewById(R.id.clients);
         noclient=(TextView)findViewById(R.id.noclient);
        stamp=(ImageView)findViewById(R.id.STAMP);
        items=new ArrayList<>();
        GST=new ArrayList<>();

        in="";
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        adapter=new listadapt(InvoiceGenerate.this,items,type);
        pd  =new ProgressDialog(InvoiceGenerate.this);
        pd.setMessage("please wait ....");
        pd.show();
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        currentDate=setDateString(day,month,year);
        dateString.setText(currentDate);
        if(clients.getVisibility()==View.INVISIBLE)
        {
                noclient.setVisibility(View.VISIBLE);
        }
        db=FirebaseDatabase.getInstance().getReference("Invoice/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long g = dataSnapshot.getChildrenCount();
                Long l = 0l;
                if (g > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (l == g-1) {
                            in = ds.getKey();
                        }
                        l++;
                    }


                        in = in.substring(3);
                        int o = Integer.parseInt(in);
                        o++;
                        DecimalFormat format = new DecimalFormat("000");
                        in = format.format(o);
                        invoice.setText("INV"+in);



                    pd.hide();
                }
                else
                {
                    invoice.setText("INV000");
                    pd.hide();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bank_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AccPaymentDetailsActivity.class);
                //intent.putExtra("Type","VENDOR");
                intent.putExtra("from","Invoice");
                startActivityForResult(intent,1);

            }
        });

        subtotal=(TextView)findViewById(R.id.subtotal);

        Discount1=(TextView)findViewById(R.id.discount1);

        total=(TextView)findViewById(R.id.total);
        DatabaseReference db2=FirebaseDatabase.getInstance().getReference("CompanyLogo/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Companylogo");
        db2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                        logopath= Uri.parse(dataSnapshot.getValue(String.class));
                }

                                               @Override
                                               public void onCancelled(DatabaseError databaseError) {

                                               }
        });

                    DatabaseReference db1=FirebaseDatabase.getInstance().getReference("Users/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        db1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a1="",a2="",a3="";
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if(ds.getKey().equals("Company"))
                        c =ds.getValue(String.class);

                    if(ds.getKey().equals("Address1"))
                        a1 =ds.getValue(String.class);

                    if(ds.getKey().equals("Address2"))
                        a2 =ds.getValue(String.class);

                    if(ds.getKey().equals("Address3"))
                        a3 =ds.getValue(String.class);

                    if(ds.getKey().equals("contact person"))
                        cp =ds.getValue(String.class);

                    if(ds.getKey().equals("GSTIN"))
                        user_gst =ds.getValue(String.class);

                    if(ds.getKey().equals("Pan"))
                        user_pan =ds.getValue(String.class);

                    if(ds.getKey().equals("Mobile Number"))
                        user_phone =ds.getValue(String.class);

                }
                ad=a1+"\n"+a2+"\n"+a3;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        Button preview=(Button)findViewById(R.id.previewbutton);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(validate()) {
                  ProgressDialog pd = new ProgressDialog(InvoiceGenerate.this);
                  String companyname = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                  pd.setMessage("Generating Invoice ...");
                  pd.show();
                  String path1 = Environment.getExternalStorageDirectory() + File.separator + invoice.getText().toString() + "temp.pdf";
                  file = new File(path1);
                  if (type.contains("Intra")) {
                      tax_invoice1 in = new tax_invoice1(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                      in.pdfcreate(file, path,StampPath,logopath);
                      pd.hide();
                      startActivity(new Intent(InvoiceGenerate.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                  } else if (type.contains("Inter")) {
                      tax_invoice2 in = new tax_invoice2(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                      in.pdfcreate(file, path,StampPath,logopath);
                      pd.hide();
                      startActivity(new Intent(InvoiceGenerate.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                  }
                  if (type.contains("Credit")) {
                      Credit_Note in = new Credit_Note(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                      in.pdfcreate(file, path,StampPath,logopath);
                      pd.hide();

                      startActivity(new Intent(InvoiceGenerate.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                  }
                  if (type.contains("Debit")) {
                      Debit_Note in = new Debit_Note(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                      in.pdfcreate(file, path,StampPath,logopath);
                      pd.hide();

                      startActivity(new Intent(InvoiceGenerate.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                  } else if (type.contains("Receipt")) {
                      Receipt_Voucher in = new Receipt_Voucher(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                      in.pdfcreate(file, path,StampPath,logopath);
                      pd.hide();

                      startActivity(new Intent(InvoiceGenerate.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                  } else if (type.contains("Payment")) {
                      Payment_Voucher in = new Payment_Voucher(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                      in.pdfcreate(file, path,StampPath,logopath);
                      pd.hide();

                      startActivity(new Intent(InvoiceGenerate.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                  } else if (type.contains("Export")) {
                      Export_invoice in = new Export_invoice(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                      in.pdfcreate(file, path,StampPath,logopath);
                      pd.hide();

                      startActivity(new Intent(InvoiceGenerate.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));

                  }
              }
              else
              {
                  Toast.makeText(InvoiceGenerate.this, "Please ensure you have either added a vendor or an item!", Toast.LENGTH_SHORT).show();
              }

            }
        });




         invoice=(TextView) findViewById(R.id.invoiceid);
        rv= (RecyclerView)findViewById(R.id.itemlist);


        rv.setLayoutManager(new LinearLayoutManager(InvoiceGenerate.this));
        rv.setAdapter(adapter);

         dis=(TextView)findViewById(R.id.discount);
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(InvoiceGenerate.this,Discount.class);
                startActivityForResult(i,5);
            }
        });

        l=(LinearLayout) findViewById(R.id.linearLayout4);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddItem.class);
                intent.putExtra("type",type);
                startActivityForResult(intent,2);

            }
        });

         ClientDetails =(LinearLayout) findViewById(R.id.linearLayout3);
        ClientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Vendor_Details.class);
                intent.putExtra("from","Invoice");
                startActivityForResult(intent,3);

            }
        });

        mp=new HashMap<>();
        dateString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        TextView save=(TextView)findViewById(R.id.saveinvoice);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(validate()) {
                        pd.setMessage("Generating Invoice");
                        pd.show();
                        uploadInvoice();
                        save();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                pd.hide();
                                AlertDialog BackToInvoice =new AlertDialog.Builder(InvoiceGenerate.this)
                                        //set message, title, and icon
                                        .setTitle("Successful")
                                        .setMessage("Do you want to further edit the invoice?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                //your deleting code
                                                dialog.dismiss();
                                            }

                                        })

                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(InvoiceGenerate.this, NavigationDrawer.class));

                                            }
                                        })
                                        .create();
                                BackToInvoice.show();
                            }
                        }, 2000);

                    }
                    else
                    {
                        Toast.makeText(InvoiceGenerate.this, "Please ensure you have either added a vendor or an item!", Toast.LENGTH_SHORT).show();

                    }


            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(InvoiceGenerate.this, image);

                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
               // image.invalidate();
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                       switch(item.getItemId())
                       {
                           case R.id.upload:
                               Intent intent = new Intent(InvoiceGenerate.this,explorer.class);
                              /* intent.setType("image/*");
                               intent.setAction(Intent.ACTION_GET_CONTENT);*/
                               startActivityForResult(intent,99);
                               break;
                           case R.id.draw:

                               startActivityForResult(new Intent(InvoiceGenerate.this,Signature_Activity.class),99);
                               image.postInvalidate();
                       }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
        stamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),ADD_STAMP);

            }
        });
    }


    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
    //        currentDate=setDateString(day,month,year);
            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity(), this, year, month, day);
          //  datePickerDialog.getDatePicker().setCalendarViewShown(false);

            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

           String date;
            date=setDateString(dayOfMonth, monthOfYear, year);

            dateString.setText(date);
        }



    }
    private static String setDateString(int dayOfMonth, int monthOfYear, int year) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        String s= day + "/" + mon + "/" + year;
        return s;
    }
    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if(resultCode==99)
        {   uploadSign.setVisibility(View.GONE);
            File f=new File(data.getStringExtra("image"));
            path=Uri.parse(f.getPath());
          //  Toast.makeText(this, path.toString(), Toast.LENGTH_SHORT).show();
            Picasso.with(getApplicationContext()).load(f).memoryPolicy(MemoryPolicy.NO_CACHE).into(image);



        }
            if (resultCode == 1) {
                bank = data.getStringExtra("bank_name");
                ifsccode = data.getStringExtra("ifsc_code");
                accholder = data.getStringExtra("account_holder");
                accno = data.getStringExtra("account_number");
                bank_details.setText(accholder);

            }
           else if (resultCode == 2) {

                description = data.getStringExtra("description");
                HSNcode = data.getStringExtra("HSNcode");
                unitcost = data.getStringExtra("unitcost");
                quantity = data.getStringExtra("quantity");
                amount = data.getStringExtra("amount");

                Double d=Math.ceil(Double.parseDouble(amount));
                amount=d.toString();


                String [] gstcost=new String[2];

                if(type.contains("Intra")||type.contains("Debit")||type.contains("Credit")||type.contains("Receipt")||type.contains("Payment"))
                {
                   sgst=data.getStringExtra("Sgst");
                    cgst=data.getStringExtra("Cgst");
                    gstcost[0]=data.getStringExtra("Sgstcost");
                    gstcost[1]=data.getStringExtra("Cgstcost");
                }

                if(type.contains("Inter")||type.contains("Export"))
                    {
                    igst=data.getStringExtra("Igst");
                    gstcost[0]=data.getStringExtra("Igstcost");
                }

                GST.add(gstcost);


                if(type.contains("Intra")||type.contains("Debit")||type.contains("Credit")||type.contains("Receipt")||type.contains("Payment"))
                items.add(new String[]{description, HSNcode,sgst,cgst, unitcost, quantity, amount});
                else if(type.contains("Inter")||type.contains("Export"))
                    items.add(new String[]{description, HSNcode,igst, unitcost, quantity, amount});


                adapter.notifyDataSetChanged();
                if(adapter.getItemCount()>0)
                {
                    noitem.setVisibility(View.GONE);
                }
                String invoiceid = invoice.getText().toString();


                db = FirebaseDatabase.getInstance().getReference("Invoice").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(invoiceid);
                mp.put("Description", description);
                mp.put("HSN code", HSNcode);
                if(type.contains("Intra")||type.contains("Debit")||type.contains("Credit")||type.contains("Receipt")||type.contains("Payment"))
                {
                    mp.put("Sgst", sgst);
                    mp.put("Cgst", cgst);
                }
                if(type.contains("Inter")||type.contains("Export"))
                mp.put("Igst",igst);

                mp.put("unit cost", unitcost);
                mp.put("quantity", quantity);
                mp.put("amount", amount);

                sub=sub+Double.parseDouble(amount);
                subtotal.setText("₹ "+sub.toString());
                sub=Math.ceil(sub) ;

                convert con=new convert();

                if(sub<1000)
                {
                 num_to_words=con.convertLessThanOneThousand(sub.intValue());
                }
                else
                {
                    num_to_words=con.convert(sub.longValue());
                }

                total.setText("₹"+sub.toString());


                db.child("Items").child("Item " + i).setValue(mp, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    }
                });

                i++;
                mp.clear();

            }
            else if (resultCode == 3) {
                Name = data.getStringExtra("name");
                Phone = data.getStringExtra("phone");
                Email = data.getStringExtra("email");
                Address = data.getStringExtra("address1")+"\n"+data.getStringExtra("address2")+"\n";
                Gstin=data.getStringExtra("gstin");
                Pan_no=data.getStringExtra("pan");
                state=data.getStringExtra("State");
                zip=data.getStringExtra("Zip");

                TextView company,gst,pan;
                company=(TextView)findViewById(R.id.com);
                gst=(TextView)findViewById(R.id.gst);
                pan=(TextView)findViewById(R.id.pan);

                company.setText(Name);
                gst.setText(Gstin);
                pan.setText(Pan_no);


                clients.setVisibility(View.VISIBLE);
                noclient.setVisibility(View.GONE);

            }
            else if(resultCode==5)
            {
                discount=data.getExtras().getDouble("discount");
                discount=sub*(discount/100);
                Discount1.setText("₹"+discount);


                tot=sub-discount;
                total.setText("₹"+tot.toString());


            }

            /*else if (requestCode == ADD_SEAL) {
            try {
                switch (resultCode) {

                    case  Activity.RESULT_OK:
                        uploadSign.setVisibility(View.GONE);
                        //path=(data.getData());
                        path=Uri.parse(GetURI.getPath(this,data.getData()));
                        Picasso.with(this).load(data.getData()).into(image);
                        //path=Uri.parse(getRealPathFromURI(data.getData()));
                        //Toast.makeText(this, path.toString(), Toast.LENGTH_SHORT).show();
                        break;
                        case  Activity.RESULT_CANCELED:
                         Log.e("", "Selecting picture cancelled");
                            break;
                }
            } catch (Exception e) {
                Log.e("", "Exception in onActivityResult : " + e.getMessage());
            }
        }*/
            else if (requestCode == ADD_STAMP) {
                try {
                    switch (resultCode) {

                        case  Activity.RESULT_OK:
                            uploadStamp.setVisibility(View.GONE);
                            //path=(data.getData());
                            StampPath=Uri.parse(GetURI.getPath(this,data.getData()));
                            Picasso.with(this).load(data.getData()).into(stamp);
                            //path=Uri.parse(getRealPathFromURI(data.getData()));
                            //Toast.makeText(this, path.toString(), Toast.LENGTH_SHORT).show();
                            break;
                        case  Activity.RESULT_CANCELED:
                            Log.e("", "Selecting picture cancelled");
                            break;
                    }
                } catch (Exception e) {
                    Log.e("", "Exception in onActivityResult : " + e.getMessage());
                }
            }
    }
        public void uploadInvoice()
        {

             db= FirebaseDatabase.getInstance().getReference("Invoice");


            String invoiceid=invoice.getText().toString();

            mp.put("Date_of_Invoice",dateString.getText().toString());
         //   mp.put("Invoice_ID",invoiceid);
            mp.put("VendorName",Name);
            mp.put("Amount",amount);
            mp.put("place_of_supply","india");

            db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(invoiceid).child("Details").setValue(mp, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                }
            });
        }


    public void save()
    {
        String companyname=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        file=new File(Environment.getExternalStorageDirectory()+ File.separator+invoice.getText().toString()+".pdf");

        if(type.contains("Intra")) {
            tax_invoice1 in = new tax_invoice1(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp,user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file,path,StampPath,logopath);
        }
        else if(type.contains("Inter"))
        {
            tax_invoice2 in = new tax_invoice2(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file,path,StampPath,logopath);
        }
        if(type.contains("Credit")) {
            Credit_Note in = new Credit_Note(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp,user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file,path,StampPath,logopath);
        }
        if(type.contains("Debit")) {
            Debit_Note in = new Debit_Note(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp,user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file,path,StampPath,logopath);
        }

        else if(type.contains("Receipt"))
        {
            Receipt_Voucher in = new Receipt_Voucher(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp,user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file,path,StampPath,logopath);
        }
        else if(type.contains("Payment"))
        {
            Payment_Voucher in = new Payment_Voucher(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp,user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file,path,StampPath,logopath);
        }
        else if (type.contains("Export"))
        {
            Export_invoice in = new Export_invoice(num_to_words,invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp,user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file,path,StampPath,logopath);
        }

    }






boolean validate()
{
    return ((noclient.getVisibility()== View.GONE )||( noitem.getVisibility()== View.GONE));
}

}




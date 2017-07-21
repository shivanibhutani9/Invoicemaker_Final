package com.example.adity.invoicemaker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InvoiceGenerate extends AppCompatActivity {
 static TextView dateString;
    int ADD_SEAL=99;
    ProgressDialog pd;
    String bank,ifsccode,accholder,accno;
    String type;
    String state,zip;
    String description,HSNcode,unitcost,quantity,amount;
    String c,ad,cp,user_gst,user_pan;
    listadapt adapter;
    RecyclerView rv;
    String Name,Phone,Email,Address,Gstin,Pan_no,sgst,cgst,igst;
    TextView subtotal, dis,Discount1,total;
    Double sub=0.0,discount=0.0,tot=0.0;
    TextView bank_details;
    LinearLayout l,ClientDetails;
    File file;
    DatabaseReference db;
    TextView invoice;
    ImageView image;
    String in="";
    int i=1;
    ArrayList<String[]> items;
    ArrayList<String[]> GST;
    Map<String,String> mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_gen);
        dateString=(TextView)findViewById(R.id.textdate);
        bank_details=(TextView)findViewById(R.id.bank);
        image=(ImageView)findViewById(R.id.SEAL);

        type=getIntent().getExtras().getString("type");


        items=new ArrayList<>();
        GST=new ArrayList<>();



        in="";
        adapter=new listadapt(InvoiceGenerate.this,items,type);
        pd  =new ProgressDialog(InvoiceGenerate.this);
        pd.setMessage("please wait ....");
        pd.show();
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

                String path=Environment.getExternalStorageDirectory()+ File.separator+invoice.getText().toString()+"temp.pdf";
                file=new File(path);
                if(type.contains("Intra")) {
                    intra in = new intra(invoice.getText().toString(), dateString.getText().toString(), c, ad, user_gst, cp, Name, Address, state, zip, Gstin, items, GST, total.getText().toString());
                    in.createpdf(file);
                    startActivity(new Intent(InvoiceGenerate.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                }
                else if(type.contains("Inter"))
                {
                    tax_invoice2 in = new tax_invoice2(invoice.getText().toString(), dateString.getText().toString(), c, ad, user_gst, cp, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
                    in.pdfcreate(file);
                    startActivity(new Intent(InvoiceGenerate.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
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


                    pd.setMessage("Generating Invoice");
                    pd.show();
                    uploadInvoice();
                    save();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pd.hide();
                        }
                    }, 2000);


            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(InvoiceGenerate.this, image);

                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                       switch(item.getItemId())
                       {
                           case R.id.upload:
                               Intent intent = new Intent();
                               intent.setType("image/*");
                               intent.setAction(Intent.ACTION_GET_CONTENT);
                               startActivityForResult(Intent.createChooser(intent, "Select Picture"),ADD_SEAL);
                               break;
                           case R.id.draw:
                               startActivity(new Intent(InvoiceGenerate.this,Signature_Activity.class));
                       }
                        return true;
                    }
                });

                popup.show();//showing popup menu
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
            String currentDate=setDateString(day,month,year);
            dateString.setText(currentDate);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);

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


                String [] gstcost=new String[2];

                if(type.contains("Intra"))
                {
                    sgst=data.getStringExtra("Sgst");
                    cgst=data.getStringExtra("Cgst");
                    gstcost[0]=data.getStringExtra("Sgstcost");
                    gstcost[1]=data.getStringExtra("Cgstcost");
                }

                if(type.contains("Inter"))
                {
                    igst=data.getStringExtra("Igst");
                    gstcost[0]=data.getStringExtra("Igstcost");
                }

                GST.add(gstcost);


                if(type.contains("Intra"))
                items.add(new String[]{description, HSNcode,sgst,cgst, unitcost, quantity, amount});
                else if(type.contains("Inter"))
                    items.add(new String[]{description, HSNcode,igst, unitcost, quantity, amount});


                adapter.notifyDataSetChanged();
                String invoiceid = invoice.getText().toString();


                db = FirebaseDatabase.getInstance().getReference("Invoice").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(invoiceid);
                mp.put("Description", description);
                mp.put("HSN code", HSNcode);
                if(type.contains("Intra"))
                {
                    mp.put("Sgst", sgst);
                    mp.put("Cgst", cgst);
                }
                if(type.contains("Inter"))
                mp.put("Igst",igst);

                mp.put("unit cost", unitcost);
                mp.put("quantity", quantity);
                mp.put("amount", amount);

                sub=sub+Double.parseDouble(amount);
                subtotal.setText("₹ "+sub.toString());

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

                LinearLayout clients=(LinearLayout)findViewById(R.id.clients);
                clients.setVisibility(View.VISIBLE);

            }
            else if(resultCode==5)
            {
                discount=data.getExtras().getDouble("discount");
                discount=sub*(discount/100);
                Discount1.setText("₹"+discount);


                tot=sub-discount;
                total.setText("₹"+tot.toString());


            }

            else if (requestCode == ADD_SEAL) {
            try {
                switch (resultCode) {

                    case  Activity.RESULT_OK:
                            Picasso.with(this).load(data.getData()).into(image);
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

        file=new File(Environment.getExternalStorageDirectory()+ File.separator+invoice.getText().toString()+".pdf");

        if(type.contains("Intra")) {
            intra in = new intra(invoice.getText().toString(), dateString.getText().toString(), c, ad, user_gst, cp, Name, Address, state, zip, Gstin, items, GST, total.getText().toString());
            in.createpdf(file);
            startActivity(new Intent(InvoiceGenerate.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
        }
        else if(type.contains("Inter"))
        {
            tax_invoice2 in = new tax_invoice2(invoice.getText().toString(), dateString.getText().toString(), c, ad, user_gst, cp, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file);
            startActivity(new Intent(InvoiceGenerate.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
        }

    }







}




package com.example.adity.invoicemaker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageButton;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InvoiceEdit extends AppCompatActivity {
    static TextView dateString;
    int ADD_SEAL=99;
    Uri path;
    ProgressDialog pd;
    String bank,ifsccode,accholder,accno;
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
    DatabaseReference db;
    TextView invoice;
    static ImageView image;
    String in="";
    int i=1;
    ArrayList<String[]> items;
    ArrayList<String[]> GST;
    Map<String,String> mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_edit);
        dateString = (TextView) findViewById(R.id.textdate);
        bank_details = (TextView) findViewById(R.id.bank);
        image = (ImageView) findViewById(R.id.SEAL);
//        cal=(ImageButton)findViewById(R.id.calendar);

        // type=getIntent().getExtras().getString("type");

        LinearLayout clients=(LinearLayout)findViewById(R.id.clients);
        clients.setVisibility(View.VISIBLE);
        items = new ArrayList<>();
        GST = new ArrayList<>();
        TextView company,gst,pan;
        company=(TextView)findViewById(R.id.com);
        gst=(TextView)findViewById(R.id.gst);
        pan=(TextView)findViewById(R.id.pan);


        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            dateString.setText(extras.getString("date"));
            company.setText(extras.getString("vname"));
            gst.setText(Gstin);
            pan.setText(Pan_no);


            in = "";
            adapter = new listadapt(InvoiceEdit.this, items, type);


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            bank_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AccPaymentDetailsActivity.class);
                    //intent.putExtra("Type","VENDOR");
                    intent.putExtra("from", "Invoice");
                    startActivityForResult(intent, 1);

                }
            });
            AlertDialog DeletionDialogBox =new AlertDialog.Builder(this)
                    //set message, title, and icon
                    .setMessage("Working on it!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            //your deleting code
                            dialog.dismiss();
                            onBackPressed();
                        }

                    })

                    .create();
            DeletionDialogBox.show();
            subtotal = (TextView) findViewById(R.id.subtotal);

            Discount1 = (TextView) findViewById(R.id.discount1);

            total = (TextView) findViewById(R.id.total);
            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
            db1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String a1 = "", a2 = "", a3 = "";
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.getKey().equals("Company"))
                            c = ds.getValue(String.class);

                        if (ds.getKey().equals("Address1"))
                            a1 = ds.getValue(String.class);

                        if (ds.getKey().equals("Address2"))
                            a2 = ds.getValue(String.class);

                        if (ds.getKey().equals("Address3"))
                            a3 = ds.getValue(String.class);

                        if (ds.getKey().equals("contact person"))
                            cp = ds.getValue(String.class);

                        if (ds.getKey().equals("GSTIN"))
                            user_gst = ds.getValue(String.class);

                        if (ds.getKey().equals("Pan"))
                            user_pan = ds.getValue(String.class);

                        if (ds.getKey().equals("Mobile Number"))
                            user_phone = ds.getValue(String.class);

                    }
                    ad = a1 + "\n" + a2 + "\n" + a3;

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Button preview = (Button) findViewById(R.id.previewbutton);
            preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressDialog pd = new ProgressDialog(InvoiceEdit.this);
                    String companyname = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    pd.setMessage("Generating Invoice ...");
                    pd.show();
                    String path1 = Environment.getExternalStorageDirectory() + File.separator + invoice.getText().toString() + "temp.pdf";
                    file = new File(path1);
                    if (type.contains("Intra")) {
                        tax_invoice1 in = new tax_invoice1(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                        in.pdfcreate(file,path);
                        pd.hide();
                        startActivity(new Intent(InvoiceEdit.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                    } else if (type.contains("Inter")) {
                        tax_invoice2 in = new tax_invoice2(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                        in.pdfcreate(file);
                        pd.hide();
                        startActivity(new Intent(InvoiceEdit.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                    }
                    if (type.contains("Credit")) {
                        Credit_Note in = new Credit_Note(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                        in.pdfcreate(file);
                        pd.hide();

                        startActivity(new Intent(InvoiceEdit.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                    }
                    if (type.contains("Debit")) {
                        Debit_Note in = new Debit_Note(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                        in.pdfcreate(file);
                        pd.hide();

                        startActivity(new Intent(InvoiceEdit.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                    } else if (type.contains("Receipt")) {
                        Receipt_Voucher in = new Receipt_Voucher(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                        in.pdfcreate(file);
                        pd.hide();

                        startActivity(new Intent(InvoiceEdit.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                    } else if (type.contains("Payment")) {
                        Payment_Voucher in = new Payment_Voucher(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                        in.pdfcreate(file);
                        pd.hide();

                        startActivity(new Intent(InvoiceEdit.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));
                    } else if (type.contains("Export")) {
                        Export_invoice in = new Export_invoice(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
                        in.pdfcreate(file);
                        pd.hide();

                        startActivity(new Intent(InvoiceEdit.this, pdfreader.class).putExtra("inv", invoice.getText().toString()));

                    }


                }
            });


            invoice = (TextView) findViewById(R.id.invoiceid);
            rv = (RecyclerView) findViewById(R.id.itemlist);


            rv.setLayoutManager(new LinearLayoutManager(InvoiceEdit.this));
            rv.setAdapter(adapter);

            dis = (TextView) findViewById(R.id.discount);
            dis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(InvoiceEdit.this, Discount.class);
                    startActivityForResult(i, 5);
                }
            });

            l = (LinearLayout) findViewById(R.id.linearLayout4);
            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddItem.class);
                    intent.putExtra("type", type);
                    startActivityForResult(intent, 2);

                }
            });

            ClientDetails = (LinearLayout) findViewById(R.id.linearLayout3);
            ClientDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Vendor_Details.class);
                    intent.putExtra("from", "Invoice");
                    startActivityForResult(intent, 3);

                }
            });

            mp = new HashMap<>();
            dateString.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDatePickerDialog();
                }
            });

            TextView save = (TextView) findViewById(R.id.saveinvoice);
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

                    PopupMenu popup = new PopupMenu(InvoiceEdit.this, image);

                    popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.upload:
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), ADD_SEAL);
                                    break;
                                case R.id.draw:
                                    startActivityForResult(new Intent(InvoiceEdit.this, Signature_Activity.class), 99);
                                    image.postInvalidate();
                            }
                            return true;
                        }
                    });

                    popup.show();//showing popup menu
                }
            });
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
        DialogFragment newFragment = new InvoiceGenerate.DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if(resultCode==99)
        {
            File f=new File(data.getStringExtra("image"));
          path=Uri.parse(f.getPath());
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
                        path=data.getData();
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
        String companyname=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        file=new File(Environment.getExternalStorageDirectory()+ File.separator+invoice.getText().toString()+".pdf");

        if(type.contains("Intra")) {
            tax_invoice1 in = new tax_invoice1(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(), accno, ifsccode);
            in.pdfcreate(file,path);
            }
        else if(type.contains("Inter"))
        {
            tax_invoice2 in = new tax_invoice2(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file);
        }
        if(type.contains("Credit")) {
            Credit_Note in = new Credit_Note(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp,user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file);
        }
        if(type.contains("Debit")) {
            Debit_Note in = new Debit_Note(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp,user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file);
        }

        else if(type.contains("Receipt"))
        {
            Receipt_Voucher in = new Receipt_Voucher(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp,user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file);
        }
        else if(type.contains("Payment"))
        {
            Payment_Voucher in = new Payment_Voucher(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp,user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file);
        }
        else if (type.contains("Export"))
        {
            Export_invoice in = new Export_invoice(invoice.getText().toString(), dateString.getText().toString(), companyname, ad, user_gst, cp,user_phone, Name, Address, state, zip, Gstin, items, GST, total.getText().toString(),accno,ifsccode);
            in.pdfcreate(file);
        }

    }


    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        onBackPressed();
        return null;
    }
}




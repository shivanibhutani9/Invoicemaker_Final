package com.example.adity.invoicemaker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ClientDetails extends AppCompatActivity {

    EditText name,phone,email,addline,addline2,state,zip,GSTIN,PAN_NO;
    String Name,Phone,Email,add1,add2,zp,st,pan_no,gstin;
    Map<String,String> mp;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pd=new ProgressDialog(ClientDetails.this);

        name=(EditText)findViewById(R.id.clientname);
        phone=(EditText)findViewById(R.id.clientphone);
        email=(EditText)findViewById(R.id.clientemail);
        addline=(EditText)findViewById(R.id.Address1);
        addline2=(EditText)findViewById(R.id.Address2);
        state=(EditText)findViewById(R.id.Address3);
        zip=(EditText)findViewById(R.id.zip);
        GSTIN=(EditText)findViewById(R.id.gst);
        PAN_NO=(EditText)findViewById(R.id.pan);

        Button save=(Button)findViewById(R.id.buttonclient) ;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Name=name.getText().toString();
                Phone=phone.getText().toString();
                Email=email.getText().toString();
                pan_no=PAN_NO.getText().toString();
                gstin=GSTIN.getText().toString();
                add1=addline.getText().toString();
                add2=addline2.getText().toString();
                st=state.getText().toString();
                zp=zip.getText().toString();


                mp=new HashMap<>();

                if(Name.isEmpty())
                {
                    name.setError("Please enter the Company Name");
                name.requestFocus();
                }
                else if(Phone.isEmpty())
                {phone.setError("Please enter the Phone number");
                    phone.requestFocus();
                }
                else if(Email.isEmpty())
                { email.setError("Please enter the Email");
                    email.requestFocus();
                }
                else if(gstin.isEmpty())
                {   GSTIN.setError("Please enter the GSTIN");
                    GSTIN.requestFocus();
                }
                else if(pan_no.isEmpty())
                {PAN_NO.setError("Please enter the Pan No");
                    PAN_NO.requestFocus();
                }
                else if(add1.isEmpty())
                {   addline.setError("Please enter the Address");
                    addline.requestFocus();
                }
                else if(add2.isEmpty())
                {   addline2.setError("Please enter the Address");
                    addline2.requestFocus();
                }
                else if(st.isEmpty())
                {   state.setError("Please enter the State");
                    state.requestFocus();
                }
                else if(zp.isEmpty())
                {   zip.setError("Please enter the Zip code");
                    zip.requestFocus();
                }
                else {
                    pd.setMessage("Please Wait...");
                    pd.show();
                    mp.put("Phone", Phone);
                    mp.put("Email", Email);
                    mp.put("Address1", addline.getText().toString());
                    mp.put("Address2", addline2.getText().toString());
                    mp.put("State", state.getText().toString());
                    mp.put("Zip", zip.getText().toString());
                    mp.put("Phone", Phone);
                    mp.put("Email", Email);
                    mp.put("Pan no", pan_no);
                    mp.put("Gstin", gstin);


                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Company");
                    db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Name).setValue(mp, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            pd.hide();
                        }
                    });

                    finish();
                }

            }
        });

    }

}


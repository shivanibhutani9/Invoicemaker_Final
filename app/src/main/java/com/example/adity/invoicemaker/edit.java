package com.example.adity.invoicemaker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class edit extends AppCompatActivity {

    EditText person,phone,email,gstin,pan,address1,address2,address3;
    ProgressDialog pd;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        pd=new ProgressDialog(this);
        pd.setMessage("Please wait ...");
        pd.show();


        person=(EditText)findViewById(R.id.usercontect);
        phone=(EditText)findViewById(R.id.userphone);
        email=(EditText)findViewById(R.id.useremail);
        gstin=(EditText)findViewById(R.id.usergst);
        pan=(EditText)findViewById(R.id.userpan);
        address1=(EditText)findViewById(R.id.Address1);
        address2=(EditText)findViewById(R.id.Address2);
        address3=(EditText)findViewById(R.id.Address3);

          db= FirebaseDatabase.getInstance().getReference("Users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if(ds.getKey().equals("Email"))
                    {
                        email.setText(""+ds.getValue(String.class));
                    }

                    else if(ds.getKey().equals("Mobile number"))
                    {
                        phone.setText(""+ds.getValue(String.class));
                    }

                    else if(ds.getKey().equals("contact person"))
                    {
                        person.setText(""+ds.getValue(String.class));
                    }

                    else if(ds.getKey().equals("GSTIN"))
                    {
                        gstin.setText(""+ds.getValue(String.class));
                    }

                    else if(ds.getKey().equals("Pan"))
                    {
                        pan.setText(""+ds.getValue(String.class));
                    }

                    else if(ds.getKey().equals("Address1"))
                    {
                        address1.setText(""+ds.getValue(String.class));
                    }
                    else if(ds.getKey().equals("Address2"))
                    {
                        address2.setText(""+ds.getValue(String.class));
                    }
                    else if(ds.getKey().equals("Address3"))
                    {
                        address3.setText(""+ds.getValue(String.class));
                    }
                }
                pd.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button save=(Button)findViewById(R.id.usersave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> mp=new HashMap<>();
                mp.put("contact person",person.getText().toString());
                mp.put("GSTIN",gstin.getText().toString());
                mp.put("Email",email.getText().toString());
                mp.put("Mobile number",phone.getText().toString());
                mp.put("Pan",pan.getText().toString());
                mp.put("Address1",address1.getText().toString());
                mp.put("Address2",address2.getText().toString());
                mp.put("Address3",address3.getText().toString());

            db.setValue(mp, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    finish();
                }
            });


            }
        });

        }
}

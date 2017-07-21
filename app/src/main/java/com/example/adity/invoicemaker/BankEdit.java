package com.example.adity.invoicemaker;

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

public class BankEdit extends AppCompatActivity {

    EditText bankname,ifsc,accholdername,accnumber;
    String bank,ifsccode,accholder,accno;
    Map<String,String> mp;
    DatabaseReference db;
    String bnam,isf,accn,acch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_edit);
        mp=new HashMap<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bankname=(EditText)findViewById(R.id.bankname);
        ifsc=(EditText)findViewById(R.id.IfSc);
        accholdername=(EditText)findViewById(R.id.accholder);
        accnumber=(EditText)findViewById(R.id.accno);

        bankname.setText(getIntent().getStringExtra("Bankname"));
        accholdername.setText(getIntent().getStringExtra("Accholder"));
        accnumber.setText(getIntent().getStringExtra("Accno"));
        ifsc.setText(getIntent().getStringExtra("Ifsc"));


        Button save=(Button)findViewById(R.id.savebank) ;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bank = bankname.getText().toString();
                ifsccode = ifsc.getText().toString();
                accholder = accholdername.getText().toString();
                accno = accnumber.getText().toString();
                if(bank.isEmpty())
                {
                    bankname.setError("Please enter the Bank Name");
                    bankname.requestFocus();
                }
                else if(ifsccode.isEmpty())
                {ifsc.setError("Please enter the Ifsc Code");
                    ifsc.requestFocus();
                }
                else if(accholder.isEmpty())
                { accholdername.setError("Please enter the Account Holder Name");
                    accholdername.requestFocus();
                }
                else if(accno.isEmpty()) {
                    accnumber.setError("Please enter the Account No");
                    accnumber.requestFocus();
                }
                else {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Account Details");
                    mp.put("Ifsc Code", ifsccode);
                    mp.put("Account Holder", accholder);
                    db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(bank).child(accno).setValue(mp, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        }
                    });
                    finish();
                }
            }
        });









        }
}

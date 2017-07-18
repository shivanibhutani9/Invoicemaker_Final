package com.example.adity.invoicemaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Discount extends AppCompatActivity {
    EditText dis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         dis=(EditText)findViewById(R.id.editdiscount);

        Button save =(Button)findViewById(R.id.savedis);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dis.getText().equals(""))
                    dis.setText("0.0");



                Intent i=new Intent();
                i.putExtra("discount",Double.parseDouble(dis.getText().toString()));
                setResult(5,i);
                finish();

            }
        });


    }
}

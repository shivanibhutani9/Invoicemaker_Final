package com.example.adity.invoicemaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Credentials extends AppCompatActivity {
    String[] items={"Signatures","Stamps"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);


        ListView lv=(ListView)findViewById(R.id.lv123);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    startActivity(new Intent(Credentials.this,explorer2.class));

                }
            }
        });


    }
}

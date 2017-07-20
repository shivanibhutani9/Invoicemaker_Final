package com.example.adity.invoicemaker;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class VendorEDIT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        onBackPressed();
        return null;
    }
}

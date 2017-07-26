package com.example.adity.invoicemaker;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;

public class explorer extends AppCompatActivity  {

    String [] signs;
    String [] img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int permissionCheck1 = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 == PackageManager.PERMISSION_GRANTED && permissionCheck2 == PackageManager.PERMISSION_GRANTED) {
            try {
                GridView gridView=(GridView)findViewById(R.id.gd);
                String root =Environment.getExternalStorageDirectory().getAbsolutePath();
                File f = new File(root+File.separator+"Signature");
                if (f.exists())
                {
                    File lst[] = f.listFiles();

                    signs =  new String[lst.length];
                    img=new String [lst.length];
                    int i = 0;
                    for (File f2 : lst) {
                        signs[i] = f2.getName();
                        img[i]=f2.getPath();
                        i++;
                    }
                    gridadapter Gridadapter=new gridadapter(explorer.this,signs,img);
                    gridView.setAdapter(Gridadapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent();
                            i.putExtra("image", img[position]);
                            setResult(99, i);
                            finish();
                        }
                    });

                }
                else
                {
                    Toast.makeText(explorer.this, "FOLDER NOT FOUND", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e)
            {
                Toast.makeText(explorer.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE},123);
        }



    }

}

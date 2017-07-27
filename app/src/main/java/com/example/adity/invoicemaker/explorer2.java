package com.example.adity.invoicemaker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.example.adity.invoicemaker.adapter.gridadapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;

public class explorer2 extends AppCompatActivity  {

    String [] signs;
    String [] img;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView=(GridView)findViewById(R.id.gd1);
        int permissionCheck1 = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 == PackageManager.PERMISSION_GRANTED && permissionCheck2 == PackageManager.PERMISSION_GRANTED) {
            try {

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
                    gridadapter Gridadapter=new gridadapter(explorer2.this,signs,img);
                    gridView.setAdapter(Gridadapter);


                }
                else
                {
                    Toast.makeText(explorer2.this, "FOLDER NOT FOUND", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e)
            {
                Toast.makeText(explorer2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE},123);
        }

        registerForContextMenu(gridView);
        FloatingActionButton flo=(FloatingActionButton)findViewById(R.id.floatb);

        flo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(explorer2.this,Signature_Activity.class).putExtra("from","exp2"));
            }
        });

    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        super.onBackPressed();
        return null;
    }


    @Override
    public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select option");
        menu.add(0,v.getId(),0,"Make Default");
        menu.add(0,v.getId(),0,"Delete");

    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Make Default"){
            DatabaseReference db=FirebaseDatabase.getInstance().getReference("defaultsign/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
            HashMap<String,String>mp=new HashMap<>();
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;

            mp.put("Default",img[index]);
            db.setValue(mp, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                }
            });
        }
        else if(item.getTitle()=="Delete"){

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;

            File f=new File(img[index]);
            f.delete();



        }else{
            return false;
        }
        return true;
    }


}

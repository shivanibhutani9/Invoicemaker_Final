package com.example.adity.invoicemaker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.xml.sax.Attributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Signature_Activity extends AppCompatActivity {
    Button save,preview;
    View v;ImageView v1;
    Button rotate;
    float angle=0;Canvas canvas;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_);
        v=(View)findViewById(R.id.Signed);
        v1=(ImageView)findViewById(R.id.pSign);
        v.setVisibility(View.VISIBLE);
        v1.setVisibility(View.INVISIBLE);
        rotate=(Button)findViewById(R.id.ROTATE);
        rotate.setVisibility(View.GONE);
        Button save=(Button)findViewById(R.id.saveSignature);
        Button preview=(Button)findViewById(R.id.previewSignature);
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED&&permissionCheck2 != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,  android.Manifest.permission.READ_EXTERNAL_STORAGE
                    },123);
        }

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                v.draw(canvas);
                v1.setImageBitmap(bitmap);
                v.setVisibility(View.INVISIBLE);
                v1.setVisibility(View.VISIBLE);
                Toast.makeText(Signature_Activity.this, "Preview...", Toast.LENGTH_SHORT).show();
                rotate.setVisibility(View.VISIBLE);
                rotate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(angle==360)
                        {angle=0;}
                        angle=angle+45;
                        Matrix matrix = new Matrix();
                        matrix.setRotate(angle);
                        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        v1.setImageBitmap(rotatedBitmap);
                        v1.postInvalidate();

                    }
                });
            }

        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    convertToImage();
                  } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.clear:
             recreate();   break;
        }
        return true;
    }
    void convertToImage() throws IOException {
        ProgressDialog p=new ProgressDialog(this);
        p.show();
        Bitmap bitmap = Bitmap.createBitmap(v1.getWidth(), v1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v1.draw(canvas);
        File file = new File(Environment.getExternalStorageDirectory() +File.separator + "sign.png");
        FileOutputStream fout=new FileOutputStream(file,false);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,fout );
        Toast.makeText(this, "File Saved...", Toast.LENGTH_SHORT).show();
        fout.flush();
        fout.close();
        Intent i=new Intent();
        i.putExtra("image",file.getPath());
        setResult(99,i);
        p.hide();
        finish();
    }}

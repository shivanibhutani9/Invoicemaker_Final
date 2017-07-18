package com.example.adity.invoicemaker;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Simrandeep Singh
 *
 */
public class pdfreader extends AppCompatActivity {
   private ImageView imageView;
     private int currentPage = 0;
     private Button next, previous;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfreader);
        imageView = (ImageView) findViewById(R.id.image);


      // next = (Button) findViewById(R.id.next);
     /*   previous = (Button) findViewById(R.id.previous);




        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentPage--;
                render();
            }
        });

        render();*/

        try {
            openPDF();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,
                    "Something Wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }

    /**
     * method to show the stored PDF in app
     *
     */


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPDF() throws IOException {
        File file = new File(Environment.getExternalStorageDirectory()+ File.separator+getIntent().getStringExtra("inv")+".pdf");
        ParcelFileDescriptor fileDescriptor = null;
        fileDescriptor = ParcelFileDescriptor.open(
                file, ParcelFileDescriptor.MODE_READ_ONLY);

        //min. API Level 21
        PdfRenderer pdfRenderer = null;
        pdfRenderer = new PdfRenderer(fileDescriptor);

        final int pageCount = pdfRenderer.getPageCount();
      //  Toast.makeText(this,
             //   "pageCount = " + pageCount,
               // Toast.LENGTH_LONG).show();

        //Display page 0
        PdfRenderer.Page rendererPage = pdfRenderer.openPage(0);
        int rendererPageWidth = rendererPage.getWidth();
        int rendererPageHeight = rendererPage.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(
                rendererPageWidth,
                rendererPageHeight,
                Bitmap.Config.ARGB_8888);
        rendererPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        PhotoViewAttacher photoView= new PhotoViewAttacher(imageView);
        photoView.update();
       // imageView.setRotation(90);
        imageView.invalidate();
        rendererPage.close();

        pdfRenderer.close();
        fileDescriptor.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        File file = new File(Environment.getExternalStorageDirectory()+ File.separator+getIntent().getStringExtra("inv")+"temp.pdf");
            file.delete();
    }

    /*

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void render() {
        try{
            imageView = (ImageView) findViewById(R.id.image);
            int REQ_WIDTH = imageView.getWidth();
            int REQ_HEIGHT = imageView.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(REQ_WIDTH, REQ_HEIGHT, Bitmap.Config.ARGB_4444);
            File file = new File(Environment.getExternalStorageDirectory()+"/mypdf.pdf");
            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));

            if(currentPage < 0) {
                currentPage = 0;
            } else if(currentPage > renderer.getPageCount()) {
                currentPage = renderer.getPageCount() - 1;
            }

            Matrix m = imageView.getImageMatrix();
            Rect rect = new Rect(0, 0, REQ_WIDTH, REQ_HEIGHT);
            renderer.openPage(currentPage).render(bitmap, rect, m, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            imageView.setImageMatrix(m);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            PhotoViewAttacher photoView= new PhotoViewAttacher(imageView);
            photoView.update();
            imageView.invalidate();

        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/
}

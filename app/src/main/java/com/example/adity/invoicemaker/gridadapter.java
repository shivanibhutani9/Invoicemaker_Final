package com.example.adity.invoicemaker;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

public class gridadapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] imgid;

    public gridadapter(Activity context, String[] itemname, String[] imgid) {
        super(context, R.layout.explorerlist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.explorerlist, null,true);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.sign);
        TextView extratxt = (TextView) rowView.findViewById(R.id.filename);
        File f=new File(imgid[position]);
        Picasso.with(context).load(f).into(imageView);
        extratxt.setText(itemname[position]);
        return rowView;

    }
}
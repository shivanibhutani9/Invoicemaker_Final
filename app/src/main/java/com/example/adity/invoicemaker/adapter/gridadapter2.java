package com.example.adity.invoicemaker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adity.invoicemaker.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by adity on 8/1/2017.
 */

public class gridadapter2 extends RecyclerView.Adapter<gridadapter2.MyViewHolder> {

    private Context context;
    private ArrayList signs;
    private ArrayList img;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.filename);
            imageView=(ImageView)view.findViewById(R.id.sign);
//            view.setOnCreateContextMenuListener(context);

        }
    }

    public gridadapter2(Context context,ArrayList signs,ArrayList img)
    {
        this.context=context;
        this.signs=signs;
        this.img=img;
    }

    @Override
    public gridadapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.signature_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(gridadapter2.MyViewHolder holder, int position) {

        holder.textView.setText(""+signs.get(position));
        File f=new File(img.get(position).toString());
        Picasso.with(context).load(f).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return img.size();
    }



}

package com.androiddeft.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class dataAdapter extends ArrayAdapter<Details>{
    Context context;
    int layoutResourceId;
    // BcardImage data[] = null;
    ArrayList<Details> data=new ArrayList<Details>();

    public dataAdapter(Context context, int layoutResourceId,  ArrayList<Details> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }




    // public dataAdapter(Context context, int layoutResourceId, ArrayList<Details> data) {
      //  super(context, layoutResourceId, data);
       // this.layoutResourceId = layoutResourceId;
       // this.context = context;
       // this.data = data;
   // }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.custom_list_layout, parent, false);

            holder = new ImageHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtAge= (TextView)row.findViewById(R.id.txtAge);
            holder.txtPhone = (TextView)row.findViewById(R.id.txtPhone);
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }

        Details picture = data.get(position);
        Details p=getItem(position);
        holder.txtTitle.setText(picture.getNom());
        holder.txtAge.setText(picture.getDatee());
       holder.txtPhone.setText(Integer.toString(p.getReference()));
        //convert byte to bitmap take from contact class

        byte[] outImage=picture.getImage();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imgIcon.setImageBitmap(theImage);
        return row;

    }

    public void setFilterQueryProvider(FilterQueryProvider reference) {
    }


    static class ImageHolder
    {
         TextView txtAge;
        TextView txtPhone;
        ImageView imgIcon;
        TextView txtTitle;
    }




}

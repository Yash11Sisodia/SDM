package com.downloadmanager.speeddownloadmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TotalDownloads extends ArrayAdapter<String> implements View.OnClickListener {

    public TotalDownloads(@NonNull Context context, ArrayList<String> resource) {
        super(context,R.layout.singleitemtotaldownload,resource);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater lij=LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder") View vv= lij.inflate(R.layout.singleitemtotaldownload,parent,false);
        TextView ss=vv.findViewById(R.id.Cfilename2);
        String singlename=getItem(position);
        ss.setText(singlename);
        return vv;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);


        switch (v.getId())
        {


            //case R.id.item_info:
              //  Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                //        .setAction("No action", null).show();
                //break;


        }
    }
}

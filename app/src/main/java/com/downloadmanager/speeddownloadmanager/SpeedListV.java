package com.downloadmanager.speeddownloadmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;


public class SpeedListV extends ArrayAdapter<String> {
    DownInfos DwnL;
    View v;


    public SpeedListV(@NonNull Context context, ArrayList<String> filename) {
        super(context,R.layout.customitemview,filename);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li=LayoutInflater.from(getContext());
        v=li.inflate(R.layout.customitemview,parent,false);
        final TextView ss=v.findViewById(R.id.Cfilename);
        //ImageView sarty=v.findViewById(R.id.CImgV);
        DwnL=new DownInfos();
        String singlename=getItem(position);
        ss.setText(singlename);
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String j=(String) ss.getText();
                Log.e("EMINRM","EMINEM BOl "+j);
            }
        });
                /* new Thread(new Runnable() {
                @Override
                public void run() {
                ProgressBar pb=v.findViewById(R.id.CPbar);
                pb.setMax(100);
                pb.setProgress((DwnL.getProgress());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }

                }).start();*/
                return v;
    }

}

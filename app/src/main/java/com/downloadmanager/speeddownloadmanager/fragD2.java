package com.downloadmanager.speeddownloadmanager;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.util.ArrayList;

public class fragD2 extends Fragment {
    ListAdapter ks;
    ArrayList<String> name=new ArrayList<>();
    String names;
    int i=0;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
     View v=inflater.inflate(R.layout.fragd2l,container,false);
        ListView mls=(ListView)v.findViewById(R.id.inProcess2);
        fileame();
        ks=new TotalDownloads(getContext(),name);
        mls.setAdapter(ks);
        mls.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        return v;
    }


    public void fileame(){
        File sdCardRoot = Environment.getExternalStorageDirectory();
        File speedFolder = new File(sdCardRoot, "/SpeedDownloads");
        for (File f : speedFolder.listFiles()) {
            if (f.isFile())
                names = f.getName();
            // Do your stuff
             name.add(names);
             i++;
        }

    }
    //isko main activity ke layout meain paste karna fragd2 wapas lane ke liye
    /*  <TextView
            android:id="@+id/tv2"
            android:layout_width="181dp"
            android:layout_height="45dp"
            android:layout_weight="1"
            android:gravity="center"
            android:text="TextView"
            android:textSize="20sp"
            android:textStyle="bold"
            android:onClick="tv2click"/>*/
}

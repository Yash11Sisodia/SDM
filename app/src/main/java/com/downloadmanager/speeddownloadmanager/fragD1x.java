package com.downloadmanager.speeddownloadmanager;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class fragD1x extends Fragment {
    int ded;
    ArrayList<String> frdeafult = new ArrayList<>();
    ListView inProcess;
    TextView mtext;
    ListAdapter speedl;
    BroadcastReceiver uiReceiver;
    View veW;
    volatile int presentDwn = 0;
    ProgressBar progress;
    IntentFilter filter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragd1l, container, false);
        inProcess = v.findViewById(R.id.inProcess);
        //    mtext=v.findViewById(R.id.DirectoryOpener);
        //    mtext.setText(Environment.getExternalStorageDirectory().getPath()+"/SpeedDownloads");
        if (DownInfos.filedownloading.isEmpty()) {
            frdeafult.add("Empty");
            speedl = new SpeedListV(getContext(), frdeafult);
        } else {
            speedl = new SpeedListV(getContext(), DownInfos.filedownloading);
        }

        inProcess.setAdapter(speedl);
        return v;
        /* <TextView
       android:id="@+id/DirectoryOpener"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:text="TextView"/> */
    }

  //  @Override
//    public void onResume() {
        /*Log.e("FragDQ1", "AboveBrodINResume");
        //Toast.makeText(getContext(),"Inside Onresume",Toast.LENGTH_SHORT).show();
        super.onResume();
        {
            uiReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //Toast.makeText(getContext(),"Inside OnRecive",Toast.LENGTH_LONG).show();
                    //Check if the Intents purpose is to send the message.
                    String action = intent.getAction();
                    if (action.equals(DownInfos.ProgSnder)) {
                        //Toast.makeText(getContext(),"JJJJJJ",Toast.LENGTH_LONG).show();
                        //presentDwn = (int) intent.getExtras().get("ProgValue");
                        Log.e("getProgress", "N.0" + presentDwn);
                        veW = inProcess.getChildAt(0);
                        progress = (ProgressBar) veW.findViewById(R.id.CPbar);
                        progress.setMax(100);

                        Log.e("formula", "N.0" + ded);
                        //Toast.makeText(getContext(),ded,Toast.LENGTH_LONG).show();
                        UIupdater();
                     }
                }
            };
            filter = new IntentFilter(DownInfos.ProgSnder);
            getContext().registerReceiver(uiReceiver, filter);
        }*/
    //}

    /*public synchronized void setDED() {
        double vv = ((double) (DownInfos.filedownloading_SIZE) / (DownInfos.getpresentDwn()));
        ded = (int) vv / 100;
    }

    public synchronized int getDED() {
        return ded;
    }*/


//agar while loop se constant intent bheje toh onpause nahi lagana chahiye isse hamg hota
   /* @Override
    public void onPause() {
        super.onPause();
      //  getContext().unregisterReceiver(uiReceiver);
    }*/
}



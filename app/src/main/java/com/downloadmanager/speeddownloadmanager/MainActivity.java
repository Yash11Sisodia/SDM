package com.downloadmanager.speeddownloadmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ListView inProcess;
    public static ListAdapter speedl;
    public View veW;
    static DownloadReceiver rabi;
    public static TextView Tprogrek4;
    public static ProgressBar progress4;
    public static TextView Tprogrek3;
    public static ProgressBar progress3;
    public static TextView Tprogrek2;
    public static ProgressBar progress2;
    public static TextView Tprogrek1;
    public static ProgressBar progress1;
    public static TextView Tprogrek;
    public static ProgressBar progress;


  static   String contentDispositionR ;
   static String serAgentR ;
  static   String urlR;
  static   String     mimeTypeR ;
   static String mimeToExtensionR  ;
   static long contentlengthR ;
    static int j;

    int progrekss;
    int progrekss1;
    int progrekss2;
    int progrekss3;
    int progrekss4;

    Intent intent;
    TextView tv1, tv2, tv3;
    TextView mtext;
    String mimeToExtension;
    String userAgent;
    String contentDisposition;
    String mimeType;
    static int L_count=0;
    long contentlength;
    ArrayList<Long> Content_length=new ArrayList<Long>();
//ViewPager Vpager;
//PagerViewAdapter VAP;

    private DrawerLayout DL;
    private ActionBarDrawerToggle ADT;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Vpager=(ViewPager)findViewById(R.id.Pager);
        //VAP=new PagerViewAdapter(getSupportFragmentManager());
        //Vpager.setAdapter(VAP);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DL = (DrawerLayout) findViewById(R.id.DLDL);
        ADT = new ActionBarDrawerToggle(this, DL, R.string.Open, R.string.Close);
        rabi=new DownloadReceiver(new Handler());
        ADT.setDrawerIndicatorEnabled(true);
        DL.addDrawerListener(ADT);
        ADT.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView NavView = (NavigationView) findViewById(R.id.navigation);
        NavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.iitem1) {
                    //Toast.makeText(MainActivity.this, "HIIIII", Toast.LENGTH_SHORT).show();

                    //   FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    // fragmentTransaction.replace(R.id.fragcoantainer,new fragD3()).addToBackStack(null).commit();
                    closeDrawer();
                    // fragmentTransaction.commit();


                }
                return true;
            }
        });
        File file = new File(Environment.getExternalStorageDirectory() + "/SpeedDownloads");
        if (!file.exists())
            file.mkdir();
         mtext=findViewById(R.id.tv1);
         mtext.setText(Environment.getExternalStorageDirectory().getPath()+"/SpeedDownloads");
        FragmentTransaction sc = getSupportFragmentManager().beginTransaction().add(R.id.fragcoantainer, new fragD1()).addToBackStack(null);
        sc.commit();
        haveStoragePermission();
        //ArrayList<DwnLogic> newDwn=new ArrayList<DwnLogic>();
        // haveInternetPermission();
        // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
        //haveNetworkPermission();
    }

    @Override
    protected void onResume() {
        Log.e("MainACT", "JAI SHREE RAM");
        super.onResume();
        intent = getIntent();
        if (intent.getAction() != null)
            Log.e("MAINACTU", intent.getAction());
        else Log.e("MainACT", "NULL JAI BHAUAYA");
        try {
            if (intent.getAction().equals(DownInfos.DWNINI) && intent.getAction() != null) {
                int h=L_count++;
                Log.e("MainACT", "Andar GHus GAye");
                contentDisposition = (String) intent.getExtras().get("ContentDisposition");
                userAgent = (String) intent.getExtras().get("UserAgent");
                mimeType = (String) intent.getExtras().get("MimeType");
                mimeToExtension = (String) intent.getExtras().get("MimeToExtension");
                contentlength = (long) intent.getExtras().get("ContentLength");
                url = (String) intent.getExtras().get("URL");
                Content_length.add(contentlength);
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                Intent jh = null;
                if(DownInfos.firsttim) {
                    jh = new Intent(this, DwnLogicServ.class);
                    DownInfos.firsttim=false;
                    Log.e("FirstTim","FirstTim");
                }
                else if(DownInfos.secondtim){
                    jh = new Intent(this, DwnSrv.class);
                    DownInfos.secondtim=false;
                    Log.e("secondtim","secondtim");
                }
                else if(DownInfos.thirdtim){
                    jh = new Intent(this, DwnSrvv.class);
                    DownInfos.thirdtim=false;
                    Log.e("thirdtim","thirdtim");
                }
                else if(DownInfos.fourthtim){
                    jh = new Intent(this, DwnSrvvv.class);
                    DownInfos.fourthtim=false;
                    Log.e("fourthtim","fourthtim");
                }
                jh.putExtra("Lcount",h);
                jh.putExtra("ContentDisposition", contentDisposition);
                jh.putExtra("MimeType", mimeType);
                String ff = mimeType.substring(mimeType.lastIndexOf('/') + 1);
                jh.putExtra("MimeToExtension", ff);
                Log.e("SpeedExtension", ff);
                jh.putExtra("ContentLength", contentlength);
                DownInfos.filedownloading_SIZE = (int) contentlength;
                Log.e("SpeedPencho", "N.0" + DownInfos.filedownloading_SIZE);
                jh.putExtra("UserAgent", userAgent);
                jh.putExtra("URL", url);

                jh.putExtra("wreckP",rabi);
                startService(jh);
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }

           /* if(DownInfos.RESTBEGAIN){
                            Intent jh = new Intent(this, DwnLogicServ.class);
                            jh.putExtra("ContentDisposition", contentDispositionR);
                            jh.putExtra("MimeType", mimeTypeR);
                            String ff = mimeTypeR.substring(mimeTypeR.lastIndexOf('/') + 1);
                            jh.putExtra("MimeToExtension", ff);
                            Log.e("SpeedExtension", ff);
                            jh.putExtra("ContentLength", contentlengthR);
                            jh.putExtra("  Lcount", j);
                            DownInfos.filedownloading_SIZE = (int) contentlengthR;
                            Log.e("SpeedPencho", "N.0" + DownInfos.filedownloading_SIZE);
                            jh.putExtra("UserAgent", serAgentR);
                            jh.putExtra("URL", urlR);
                            jh.putExtra("Lcount",j);
                            Log.e("SERVICERE","SERVICERE");
                            Log.d("SERVIVE","STARTAGAIN");
                            startService(jh);
                            DownInfos.RESTBEGAIN=false;
            }*/
        } catch (Exception e) {
            Log.e("MainActu", e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater MII = getMenuInflater();
        MII.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itm1:
                Toast.makeText(getApplicationContext(), "Inside OnRecive", Toast.LENGTH_LONG).show();
                   Log.e("SpeedIntent", "Redirecting to browser");
                   Intent xyz = new Intent(this, Speedbrowser.class);
                   startActivity(xyz);
                break;
        }
        return ADT.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void closeDrawer() {
        if (DL.isDrawerOpen(GravityCompat.START)) {
            DL.closeDrawer(GravityCompat.START);
        }
    }


/*    <RelativeLayout
        android:layout_marginTop="50dp"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    <TextView
        android:id="@+id/DirectoryOpener"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
    </RelativeLayout> */

    /**public void tv3click(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragcoantainer, new fragD3()).addToBackStack(null);
        fragmentTransaction.commit();
    }*/
    //isko main activiry ke layout meain linearlayout section meain paste ka dena aa jayega fragment wapas aa jayega
/**  <TextView
 android:id="@+id/tv3"
 android:layout_width="181dp"
 android:layout_height="45dp"
 android:layout_weight="1"
 android:gravity="center"
 android:text="TextView"
 android:textSize="20sp"
 android:textStyle="bold"
 android:clickable="true"
 android:onClick="tv3click"/>*/
  /*  public void tv2click(View view) {
        TextView tv2=(TextView)findViewById(R.id.tv2);//agar isko yaha se hata ncreate meain banau toh click kRNE PAR ILLEGAL STATE EXCEPTION AA JATI HAI ISLYE
        //YAHA PAR HI THIK HAI
        TextView tv1=(TextView)findViewById(R.id.tv1);
        tv2.setText("Yeet");
        int redColorValue = Color.RED;
        tv2.setBackgroundColor(redColorValue);
        tv1.setText("Yeet");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragcoantainer,
               new fragD2()).addToBackStack(null);
        fragmentTransaction.commit();
    }*/

    public void tv1click(View view) {
    //    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      //  fragmentTransaction.replace(R.id.fragcoantainer, new fragD1()).addToBackStack(null);
        //fragmentTransaction.commit();
    Log.e("TOUVH","ME NOT");
    }

    public boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error", "You have permission");
                return true;
            } else {

                Log.e("Permission error", "You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error", "You already have the permission");
            return true;
        }
    }

    public boolean haveNetworkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error", "You have permission");
                return true;
            } else {

                Log.e("Permission error", "You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
                return false;
            }
        } else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error", "You already have the permission");
            return true;
        }
    }
    public boolean haveInternetPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.INTERNET)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error", "You have permission");
                return true;
            } else {

                Log.e("Permission error", "You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
                return false;
            }
        } else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error", "You already have the permission");
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        //onDestroy();
    }

    private class DownloadReceiver extends ResultReceiver {
        View v;
        View v1;
        View v2;
        View v3;
        View v4;
        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            //  progress = (ProgressBar)inProcess.getAdapter().getView(0, null,inProcess).findViewById(R.id.CPbar);
            // progress.setMax(100);
            super.onReceiveResult(resultCode, resultData);
            Log.e("RESCUD",""+resultCode);
            if (resultCode == 0) {
                progrekss = resultData.getInt("progressk"); //get the progress
                v  = inProcess.getChildAt(0);

                    Log.e("Broadcast","INSIDEFRAGGY");
                    //Toast.makeText(getContext(),ded,Toast.LENGTH_LONG).show();

                if(v!=null) {
                        long c0=Content_length.get(0);
                    progress = (ProgressBar) v.findViewById(R.id.CPbar);
                        progress.setMax(100);
                        int fgr=(int)c0/1000;
                        int jossa=progrekss/10;
                        int jhj= (jossa/fgr);
                        progress.setProgress(jhj);
                        Tprogrek=(TextView)v.findViewById(R.id.CPbarK);
                        Tprogrek.setText(""+jhj);
                        Log.e("Broadcast","beh"+jhj+"progrekss"+progrekss);
                        Log.e(" DownreceiverIN","Content"+c0+"YES Percent"+fgr);
                    }

                else {
                   // onResume();
                    //DownInfos.IntentSND=0;
                    Log.e(" DownreceiverIN", "ITS NOT TUPACK");
                }
                Log.e(" DownreceiverIN", "progressK " + progrekss+" Content lgt"+contentlength);
             }
            if (resultCode ==1) {

                progrekss1 = resultData.getInt("progressk"); //get the progress
                v1  = inProcess.getChildAt(1);
                Log.e("Broadcast","INSIDEFRAGGY");
                //Toast.makeText(getContext(),ded,Toast.LENGTH_LONG).show();

                if(v1!=null) {
                    long c1=contentlength;
                    progress1 = (ProgressBar) v1.findViewById(R.id.CPbar);
                    progress1.setMax(100);
                    int fgr=(int)c1/1000;
                    int jossa=progrekss1/10;
                    int jhj= (jossa/fgr);
                    progress1.setProgress(jhj);
                    Tprogrek1=(TextView)v1.findViewById(R.id.CPbarK);
                    Tprogrek1.setText(""+jhj);
                    Log.e("Broadcast","beh"+jhj+"progrekss"+progrekss1);
                    Log.e(" DownreceiverIN","Content"+c1+"YES Percent"+fgr);
                }

                else {
                    // onResume();
                    //DownInfos.IntentSND=0;
                    Log.e(" DownreceiverIN", "ITS NOT TUPACK");
                }
            }
            if (resultCode ==2) {
                progrekss2 = resultData.getInt("progressk"); //get the progress
                v2 = inProcess.getChildAt(2);
                Log.e("Broadcast","INSIDEFRAGGY");
                //Toast.makeText(getContext(),ded,Toast.LENGTH_LONG).show();

                if(v2!=null) {
                    long c2=Content_length.get(0);
                    progress2 = (ProgressBar) v2.findViewById(R.id.CPbar);
                    progress2.setMax(100);
                    int fgr=(int)c2/1000;
                    int jossa=progrekss2/10;
                    int jhj= (jossa/fgr);
                    progress2.setProgress(jhj);
                    Tprogrek2=(TextView)v2.findViewById(R.id.CPbarK);
                    Tprogrek2.setText(""+jhj);
                    Log.e("Broadcast","beh"+jhj+"progrekss"+progrekss2);
                    Log.e(" DownreceiverIN","Content"+c2+"YES Percent"+fgr);
                }

                else {
                    // onResume();
                    //DownInfos.IntentSND=0;
                    Log.e(" DownreceiverIN", "ITS NOT TUPACK");
                }
            }
            if (resultCode ==3) {
                progrekss3 = resultData.getInt("progressk"); //get the progress
                v3 = inProcess.getChildAt(3);
                Log.e("Broadcast","INSIDEFRAGGY");
                //Toast.makeText(getContext(),ded,Toast.LENGTH_LONG).show();

                if(v3!=null) {
                    long c3=Content_length.get(0);
                    progress3 = (ProgressBar) v3.findViewById(R.id.CPbar);
                    progress3.setMax(100);
                    int fgr=(int)c3/1000;
                    int jossa=progrekss3/10;
                    int jhj= (jossa/fgr);
                    progress3.setProgress(jhj);
                    Tprogrek=(TextView)v3.findViewById(R.id.CPbarK);
                    Tprogrek.setText(""+jhj);
                    Log.e("Broadcast","beh"+jhj+"progrekss"+progrekss3);
                    Log.e(" DownreceiverIN","Content"+c3+"YES Percent"+fgr);
                }

                else {
                    // onResume();
                    //DownInfos.IntentSND=0;
                    Log.e(" DownreceiverIN", "ITS NOT TUPACK");
                }
            }            if (resultCode ==4) {
                progrekss4 = resultData.getInt("progressk"); //get the progress
                v4 = inProcess.getChildAt(4);
                Log.e("Broadcast","INSIDEFRAGGY");
                //Toast.makeText(getContext(),ded,Toast.LENGTH_LONG).show();

                if(v4!=null) {
                    long c4=Content_length.get(4);
                    progress4 = (ProgressBar) v4.findViewById(R.id.CPbar);
                    progress4.setMax(100);
                    int fgr=(int)c4/1000;
                    int jossa=progrekss4/10;
                    int jhj= (jossa/fgr);
                    progress4.setProgress(jhj);
                    Tprogrek4=(TextView)v4.findViewById(R.id.CPbarK);
                    Tprogrek4.setText(""+jhj);
                    Log.e("Broadcast","beh"+jhj+"progrekss"+progrekss4);
                    Log.e(" DownreceiverIN","Content"+c4+"YES Percent"+fgr);
                }

                else {
                    // onResume();
                    //DownInfos.IntentSND=0;
                    Log.e(" DownreceiverIN", "ITS NOT TUPACK");
                }
            }

        }
    }

    public static class fragD1 extends Fragment {
        IntentFilter filter;
        int ded;
        BroadcastReceiver uiREC;
        ArrayList<String> frdeafult = new ArrayList<>();
        private BroadcastReceiver uiReceiver;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            final View v = inflater.inflate(R.layout.fragd1l, container, false);
            inProcess = v.findViewById(R.id.inProcess);
            //    mtext=v.findViewById(R.id.DirectoryOpener);
            //    mtext.setText(Environment.getExternalStorageDirectory().getPath()+"/SpeedDownloads");
            if (DownInfos.filedownloading.isEmpty()) {
                frdeafult.add("Empty");
                speedl = new SpeedListV(getContext(), frdeafult);
            } else {
                speedl = new SpeedListV(getContext(),DownInfos.filedownloading);
            }
            inProcess.setAdapter(speedl);
            inProcess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ImageView ld=view.findViewById(R.id.CImgV);
                    TextView T_v=view.findViewById(R.id.Cfilename);
                    final String h= (String) T_v.getText();
                    ld.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("LD","CLICKED!!!!");
                            pauseDWN(h);
                            //resumekar();
                        }
                    });
                }
            });
           // DownInfos.IntentSND=0;
            return v;
        /* <TextView
       android:id="@+id/DirectoryOpener"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:text="TextView"/> */
        }

        private void resumekar() {
        if(DownInfos.PAUSED){

        }
        }

        private void pauseDWN(String h) {
            Intent intent = new Intent(DownInfos.ProgSnderIS);
            intent.setPackage(getActivity().getPackageName());
            intent.putExtra("TV_N",h);
            getActivity().sendBroadcast(intent);
            Log.d("TAGTAAGA","Received message from  broadcast sent.");
        }

        public void onResume() {
            Log.e("FragDQ1","AboveBrodINResume");
            //Toast.makeText(getContext(),"Inside Onresume",Toast.LENGTH_SHORT).show();
            super.onResume();
            {
                uiReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        // To
                        //ast.makeText(getContext(),"Inside OnRecive",Toast.LENGTH_LONG).show();
                        //Check if the Intents purpose is to send the message.
                        String action = intent.getAction();
                        if (action.equals(DownInfos.ProgSnder)) {

                            Log.e("BROEDY","BROADCASTERT");
                      //      contentDispositionR = (String) intent.getExtras().get("ContentDisposition");
                            serAgentR = (String) intent.getExtras().get("UserAgent");
                            mimeTypeR = (String) intent.getExtras().get("MimeType");
                            mimeToExtensionR = (String) intent.getExtras().get("MimeToExtension");
                            contentlengthR = (long) intent.getExtras().get("ContentLength");
                            urlR = (String) intent.getExtras().get("URL");
                            j=(int)intent.getExtras().get("LCOUNT");
                            //////////////////////////////////////////////
                            Intent jh = new Intent(getContext(),DwnLogicServ.class);
                            jh.putExtra("ContentDisposition", contentDispositionR);
                            jh.putExtra("MimeType", mimeTypeR);
                            String ff = mimeTypeR.substring(mimeTypeR.lastIndexOf('/') + 1);
                            jh.putExtra("MimeToExtension", ff);
                            Log.e("SpeedExtension", ff);
                            jh.putExtra("ContentLength", contentlengthR);
                            jh.putExtra("  Lcount", j);
                            DownInfos.filedownloading_SIZE = (int) contentlengthR;
                            Log.e("SpeedPencho", "N.0" + DownInfos.filedownloading_SIZE);
                            jh.putExtra("UserAgent", serAgentR);
                            jh.putExtra("URL", urlR);
                            jh.putExtra("Lcount",j);
                            Log.e("SERVICERE","SERVICERE");
                            Log.d("SERVIVE","STARTAGAIN");
                            jh.putExtra("wreckP",rabi);
                            context.startService(jh);

//
                        }
                    }
                };
                IntentFilter j=new IntentFilter(DownInfos.ProgSnder);
                getContext().registerReceiver(uiReceiver, j);
            }
        }

        @Override
        public void onPause() {
            super.onPause();
            // tupac=false;
            getActivity().unregisterReceiver(uiReceiver);
        }
    }}

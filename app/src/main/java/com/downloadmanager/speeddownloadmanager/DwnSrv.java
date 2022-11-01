package com.downloadmanager.speeddownloadmanager;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DwnSrv extends IntentService {
    boolean its_resume;
    int percent;
    int Llcount;
    String url;
    String mimeToExtension;
    DownInfos DwnI;
    URL DwnUrl2 = null;
    String userAgent;
    String contentDisposition;
    String mimeType;
    long contentlength;
    int RcontentLGT;

    public DwnSrv() {
        super("jj");

    }


    RandomAccessFile ramFile;
    public int _DOWNLOADING = 0;
    public int _PAUSE = 0;
    public int _COMPLETED = 0;
    public int _CANCELLED = 0;
    public int _ERROR = 0;
    public static final String STATE[] = {"DOWNLOADING", "PAUSE", "COMPLETED", "CANCELLED", "ERROR"};  //logic meain iska koi role nahi
    String newName=null;
    public int MAX_BUFF_SIZE = 1024;
    public  int totallgt;
    int presentDwn = 0;
    int stateInt;

    public int intgetTotalLength()
    {
        return totallgt;
    }

    private String getFileName() throws MalformedURLException {//filename ke liye connection kholne ki load nahi
        String fileName = new URL(url).getFile();
        Log.e("getFileName()Real", fileName);
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        Log.e("getFileName()", fileName);
        return fileName;
    }

    public String getStoragePath() throws MalformedURLException {



        String storpath = Environment.getExternalStorageDirectory() + "/SpeedDownloads";
        String fileName = "/" + getFileName();
        Log.d("getStoragePath()", storpath + fileName);
        return storpath + fileName;


    }


    //stateint=downloading nahi banay kyoki wo run meain hai

    public void error() {
        stateInt = _ERROR;
    }

    public void pause() {
        stateInt = _PAUSE;
    }

    public void completed() {
        stateInt = _ERROR;
    }

    public void cancelled() {
        stateInt = _CANCELLED;
    }
   /*public void updateStatus(int j) {
        int index=1;
     double vv=((double) DwnI.getcontentLength()/ j) * 100;
        //View v=fragD1.inProcess.getChildAt(index-fragD1.inProcess.getFirstVisiblePosition());
        ProgressBar progress = (ProgressBar)v.findViewById(R.id.CPbar);
        progress.setProgress((int) vv);
    }*/

    public synchronized void alpha() {
        DownInfos.IntentSND=1;
        Intent progressender=new Intent(DownInfos.ProgSnder);
        progressender.setPackage(getBaseContext().getPackageName());
        getBaseContext().sendBroadcast(progressender);
        Log.e("V>IMP","SND HO GAYA BAWA");
    }


    public void alagchod() throws IOException {
        totallgt=DwnI.getcontentLength();

        stateInt = _DOWNLOADING;

        RandomAccessFile ramFile = null;

        InputStream mIS = null;

        HttpURLConnection htcn = (HttpURLConnection) DwnI.getDwnUrl2().openConnection();
        htcn.setRequestProperty("Range","bytes="+presentDwn+ "-");
        htcn.connect();

        Log.e("REsCode", String.valueOf(htcn.getResponseCode()));
        {
            //error();
            //}

            if (totallgt == -1) {
                totallgt = htcn.getContentLength();
            }

            Log.e("DWREsCode", String.valueOf(htcn.getResponseCode()));

            if (totallgt < 1) {

                error();

            }

            getFileName();

            ramFile = new RandomAccessFile(getStoragePath(), "rw");

            ramFile.seek(presentDwn);

            mIS = htcn.getInputStream();
            try {
                while (stateInt == _DOWNLOADING) {
                    Log.e("InsideCATCHWhile", "Dwonloading");
                    byte buff[];
                    if (totallgt - presentDwn > MAX_BUFF_SIZE) {
                        buff = new byte[MAX_BUFF_SIZE];
                    } else {
                        buff = new byte[totallgt - presentDwn];
                    }
                    int read = mIS.read(buff);//buff means ek baar meain itna data
                    if (read == -1)
                        break;//ek bar meain read ko bas ek hi number milta hai thats stream andding gives a jump but one nimber only
                    ramFile.write(buff, 0, read);//0 and read ,buff array ke respect meain hai 0 means start of array not start of not start stream
                    presentDwn += read;
                    Bundle resData=new Bundle();
                    resData.putInt("progress",presentDwn);
///                    rr.send(DownInfos.UPD,resData);
                    //DownInfos.setpresentDwn(presentDwn);
                }
            }catch (ProtocolException j){
                htcn.disconnect();
                alagchod();
            }
            catch (Exception j){
                Log.e("2GANDMEAiaenD",j.toString());
                j.printStackTrace(System.out);
                htcn.disconnect();
            }

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e("DwnLogicServ","*******************");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        contentDisposition = (String) intent.getExtras().get("ContentDisposition");
        userAgent = (String) intent.getExtras().get("UserAgent");
        mimeType = (String) intent.getExtras().get("MimeType");
        mimeToExtension = (String) intent.getExtras().get("MimeToExtension");
        contentlength = (long) intent.getExtras().get("ContentLength");
        Llcount=(int)intent.getExtras().get("Lcount");
        Log.e("Llcount","rec "+Llcount);
        ResultReceiver rr=(ResultReceiver)intent.getParcelableExtra("wreckP");
        try {
            url = (String) intent.getExtras().get("URL");
            DwnUrl2 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //DwnI = new DownInfos();
        Log.e("onSERVICEStrat", "*******************");
        Log.e("ur", url);
        Log.e("userAgent", userAgent);
        Log.e("contentDisposition", contentDisposition);
        Log.e("mimeType", mimeType);
        Log.e("contentLenght", String.valueOf(contentlength));
        //DwnI.setmimeType(mimeType);
        //DwnI.setMimeToExtension(mimeToExtension);
        Log.e("MimeToExtension", mimeToExtension);

        // DwnI.setuserAgent(userAgent);
        // DwnI.setcontentDisposition(contentDisposition);
        Log.d("contentlgt", String.valueOf(contentlength));
        int j = (int) contentlength;
        Log.d("contentlgtAfterInt", String.valueOf(j));
        //DwnI.setcontentLength(j);


        //  Thread tr= null;
        // tr = new Thread(new DwnLogic(DwnI,this));
        //new Thread(new Runnable() {
        //  @Override
        // public void run() {
        //   ResultReceiver kl=(ResultReceiver)
        RandomAccessFile ramFile = null;
        InputStream mIS = null;
        Log.e("HTTP", "Before Connect");
        HttpURLConnection htcn = null;
        try {
            htcn = (HttpURLConnection)new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file= null;
        try {
            file = new File(Environment.getExternalStorageDirectory() + "/SpeedDownloads" + "/" + getFileName().substring(0, 10) + "." + mimeToExtension);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if(file.exists()){
            its_resume=true;
            Log.e("file_exists","FILE_TRACED");
            RcontentLGT = (int) file.length();
            htcn.setRequestProperty("Range", "bytes="+(file.length())+"-");
        }else{
            Log.e("resumeDwn","New Download");
        }
        //htcn.setRequestProperty("Range","bytes="+presentDwn+"-");
        try {
            htcn.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Log.e("REsCode", String.valueOf(htcn.getResponseCode()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        {
            //error();
            //}

            if (contentlength == -1) {
                contentlength = htcn.getContentLength();
            }

            try {
                Log.e("REsCode", String.valueOf(htcn.getResponseCode()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (contentlength < 1) {

                error();

            }

            try {
                getFileName();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                ramFile = new RandomAccessFile(getStoragePath(), "rw");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                if (its_resume) ramFile.seek(RcontentLGT);

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mIS = htcn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {


                while (true) {
                    Log.e("InsideWhile", Llcount+"Dwonloading");
                    Log.e("Itotallgt", Llcount+"totallgt" + contentlength);
                    Log.e("presentDwn", Llcount+" " + presentDwn);
                    byte buff[];
                    if (contentlength - presentDwn > MAX_BUFF_SIZE) {
                        buff = new byte[MAX_BUFF_SIZE];
                    } else {
                        buff = new byte[totallgt - presentDwn];
                    }
                    int read = mIS.read(buff);//buff means ek baar meain itna data
                    if (read == -1)
                        break;//ek bar meain read ko bas ek hi number milta hai thats stream andding gives a jump but one nimber only
                    ramFile.write(buff, 0, read);//0 and read ,buff array ke respect meain hai 0 means start of array not start of not start stream
                    presentDwn += read;
                    percent=(int)contentlength/presentDwn;
                    if (DownInfos.IntentSND != 1) {
                        alpha();
                    }
                    Bundle resData=new Bundle();
                    resData.putInt("progressk",presentDwn);
                    rr.send(Llcount,resData);

                }
            } catch (ProtocolException h) {
                Log.e("LandFarak", h.toString());
                h.printStackTrace(System.out);
                htcn.disconnect();
                try {
                    alagchod();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception h) {
                Log.e("GANDMEAiaenD", h.toString());
                h.printStackTrace(System.out);
                htcn.disconnect();
                //alagchod();
            }

            Log.e("AfterWhile", "Dwonload Successfull");
            try {
                if (getFileName().length() >= 10) {
                    Log.e("Rename", "Required");
                    newName = Environment.getExternalStorageDirectory() + "/SpeedDownloads" + "/" + getFileName().substring(0, 10) + "." + mimeToExtension;
                    File kxx = new File(newName);
                    File kx = new File(getStoragePath());
                    boolean SOI = kx.renameTo(kxx);
                    if (SOI) Log.e("Rename", "Success");
                    htcn.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //           }
        }
    }
    public void resumeDwn(URL url,String DESTINATION_PATH) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //if(ISSUE_DOWNLOAD_STATUS.intValue()==ECMConstant.ECM_DOWNLOADING){

    }
}



package com.downloadmanager.speeddownloadmanager;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
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

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DwnLogicService extends IntentService {


    String url;
    String mimeToExtension;
    DownInfos DwnI;
    URL DwnUrl2 = null;
    String userAgent;
    String contentDisposition;
    String mimeType;
    long contentlength;


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
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.downloadmanager.speeddownloadmanager.action.FOO";
    private static final String ACTION_BAZ = "com.downloadmanager.speeddownloadmanager.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.downloadmanager.speeddownloadmanager.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.downloadmanager.speeddownloadmanager.extra.PARAM2";

    public DwnLogicService() {
        super("DwnLogicService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DwnLogicService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DwnLogicService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        contentDisposition = (String) intent.getExtras().get("ContentDisposition");
        userAgent = (String) intent.getExtras().get("UserAgent");
        mimeType = (String) intent.getExtras().get("MimeType");
        mimeToExtension = (String) intent.getExtras().get("MimeToExtension");
        contentlength = (long) intent.getExtras().get("ContentLength");
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
            htcn = (HttpURLConnection) DwnI.getDwnUrl2().openConnection();
        } catch (IOException e) {
            e.printStackTrace();
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
                ramFile.seek(presentDwn);
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
                    Log.e("InsideWhile", "Dwonloading");
                    Log.e("Itotallgt", "totallgt" + contentlength);
                    Log.e("presentDwn", " " + presentDwn);
                     /*if(presentDwn!=0)
                     {

                          percent=(totallgt/presentDwn)*100;
                         Log.e("Itotallgt","totallgt"+totallgt);Log.e("presentDwn"," "+presentDwn);
                         new Thread(new Runnable() {
                             @Override
                             public void run() {
                                 DownInfos.setpresentDwn((int)percent);
                                 try {
                                     Thread.sleep(500);
                                 } catch (InterruptedException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }).start();

                     }*/
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
                    Bundle resData=new Bundle();
                    resData.putInt("progress",presentDwn);
                    rr.send(DownInfos.UPD,resData);
                        /*if (DownInfos.IntentSND != 1) {
                            alpha();
                        }*/
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
                    newName = Environment.getExternalStorageDirectory() + "/SpeedDownloads" + "/" + getFileName().substring(0, 10) + "." + DwnI.getMimeToExtension();
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

    private void error() {
    Log.e("ERRORMTHD","MERA LAWDA");
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

            Log.e("REsCode", String.valueOf(htcn.getResponseCode()));

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
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */



    private String getFileName() throws MalformedURLException {//filename ke liye connection kholne ki load nahi
        String fileName = new URL(url).getFile();
        Log.e("getFileName()Real", fileName);
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        Log.e("getFileName()", fileName);
        return fileName;
    }

    public String getStoragePath() throws MalformedURLException {


        File file = new File(Environment.getExternalStorageDirectory() + "/SpeedDownloads");
        if (!file.exists())
            file.mkdir();
        String storpath = Environment.getExternalStorageDirectory() + "/SpeedDownloads";
        String fileName = "/" + getFileName();
        Log.d("getStoragePath()", storpath + fileName);
        return storpath + fileName;


    }







    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

package com.downloadmanager.speeddownloadmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import javax.xml.transform.Result;


public class DwnLogic implements Runnable {
   // private  final Context mApplicationContext;
   // ResultReceiver kl;
    //Handler handler;
    float percent;
    RandomAccessFile ramFile;
    public int _DOWNLOADING = 0;
    public int _PAUSE = 0;
    public int _COMPLETED = 0;
    public int _CANCELLED = 0;
    public int _ERROR = 0;
    public static final String STATE[] = {"DOWNLOADING", "PAUSE", "COMPLETED", "CANCELLED", "ERROR"};  //logic meain iska koi role nahi
    String newName=null;
    DownInfos DwnI;
    public int MAX_BUFF_SIZE = 1024;
    public  int totallgt;
    int presentDwn = 0;
    int stateInt;

    public int intgetTotalLength()
    {
        return totallgt;
    }
    public DwnLogic(DownInfos DwnI,Context context) throws MalformedURLException {
      this.DwnI=DwnI;
  //  mApplicationContext=context.getApplicationContext();
    }
    private String getFileName() {//filename ke liye connection kholne ki load nahi
        String fileName = DwnI.getDwnUrl2().getFile();
        Log.e("getFileName()Real", fileName);
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        Log.e("getFileName()", fileName);
        return fileName;
    }

    public String getStoragePath() {


        File file = new File(Environment.getExternalStorageDirectory() + "/SpeedDownloads");
        if (!file.exists())
            file.mkdir();
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
           // progressender.putExtra("ProgValue",presentDwn);
         //   progressender.setPackage(mApplicationContext.getPackageName());
      ///      mApplicationContext.sendBroadcast(progressender);

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
                     DownInfos.setpresentDwn(presentDwn);

                     if (DownInfos.IntentSND != 1) {
                         alpha();
                     }
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
    public void run() {

        //   ResultReceiver kl=(ResultReceiver)
        totallgt=DwnI.getcontentLength();

        stateInt = _DOWNLOADING;

        RandomAccessFile ramFile = null;

        InputStream mIS = null;

        Log.e("HTTP", "Before Connect");

        try {

            HttpURLConnection htcn = (HttpURLConnection) DwnI.getDwnUrl2().openConnection();
            //htcn.setRequestProperty("Range","bytes="+presentDwn+"-");
            htcn.connect();

            Log.e("REsCode", String.valueOf(htcn.getResponseCode()));
            {
                //error();
                //}

                if(totallgt==-1){
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
                        Log.e("InsideWhile", "Dwonloading");
                        Log.e("Itotallgt","totallgt"+totallgt);Log.e("presentDwn"," "+presentDwn);
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


                    }
                }catch (ProtocolException h){
                    Log.e("LandFarak",h.toString());
                    h.printStackTrace(System.out);
                    htcn.disconnect();
                    alagchod();
                }
                catch (Exception h){
                    Log.e("GANDMEAiaenD",h.toString());
                    h.printStackTrace(System.out);
                    htcn.disconnect();
                    //alagchod();
                }

                Log.e("AfterWhile","Dwonload Successfull");
                if(getFileName().length()>=10) {
                    Log.e("Rename","Required");
                    newName=Environment.getExternalStorageDirectory() + "/SpeedDownloads" + "/"+ getFileName().substring(0,10)+"."+DwnI.getMimeToExtension();
                    File kxx = new File(newName);
                    File kx = new File(getStoragePath());
                    boolean SOI = kx.renameTo(kxx);
                    if (SOI) Log.e("Rename", "Success");
                    htcn.disconnect();
                }

           }

        /*        try {

            stateInt=_DOWNLOADING;
            ftp = new FTPClient();
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            Log.d("SubDomain",DwnUrl.getHost());
            ftp.connect(DwnUrl.getHost(),22);//don't write ftp://
            Log.e("After Connect",DwnUrl.getHost());
            try {
                int reply = ftp.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    throw new Exception("Connect failed: " + ftp.getReplyString());
                }
                if (!ftp.login("anonymous", "anonymous")) {
                    throw new Exception("Login failed: " + ftp.getReplyString());
                }
                try {
                  //  ftp.enterLocalPassiveMode();
                    //if (!ftp.setFileType(FTP.BINARY_FILE_TYPE)) {
//                    //      Log.e(TAG, "Setting binary file type failed.");
                    //}

0              //transferFile(ftp);
                    getFileName();
                    ramFile = new RandomAccessFile(getStoragePath(), "rw");
                    Log.d("AbovemIS","********");
                    InputStream mIS=ftp.retrieveFileStream(DwnUrl.getPath());
                    Log.d("Filepath",DwnUrl.getPath());
                        FTPFile[] files = ftp.listFiles(DwnUrl.getPath());
                        if (files.length == 1 && files[0].isFile()) {
                            if(totallgt==-1) totallgt = (int) files[0].getSize();
                        }
                        Log.i("AboveWhileLoop", "File size = " + totallgt);
               if(totallgt==-1){
                   ftp.disconnect();
               }
                    while (stateInt == _DOWNLOADING) {
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
                    }
                } catch (Exception e) {
//                      handleThrowable(e);
                } finally {
                    if (!ftp.logout()) {
//                          Log.e(TAG, "Logout failed.");
                    }
                }
            } catch (Exception e) {
//                  handleThrowable(e);
            } finally {
                ftp.disconnect();
            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

      /*  public void forHttp () {
            stateInt = _DOWNLOADING;
            RandomAccessFile ramFile = null;
            InputStream mIS = null;
            OutputStream mOS = null;

            try {
                HttpURLConnection htcn = (HttpURLConnection) DwnUrl.openConnection();
                htcn.setRequestProperty("Range",
                        "bytes=" + presentDwn + "-");
                htcn.connect();
                //if(htcn.getResponseCode()!=200) {
                //error();
                //}
                totallgt = htcn.getContentLength();
                if (totallgt < 1) {
                    error();
                }
                getFileName();
                ramFile = new RandomAccessFile(getStoragePath(), "rw");
                ramFile.seek(presentDwn);

                mIS = htcn.getInputStream();
                mOS = htcn.getOutputStream();
                while (stateInt == _DOWNLOADING) {
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
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

    }

}

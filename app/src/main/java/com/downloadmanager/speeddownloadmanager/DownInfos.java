package com.downloadmanager.speeddownloadmanager;

import java.net.URL;
import java.util.ArrayList;

public class DownInfos {
    public static boolean RESTBEGAIN =false;
    public static String ProgSnder="com.downloadmanager.sppeddownloadmanager.ProgSnder";
    public static String ProgSnderIS="com.downloadmanager.sppeddownloadmanager.ProgSnderIS";
    public static String DWNINI="DOWNLOAD_INI";
    public static boolean PAUSED=false;
    public static boolean tupacshakur;
    public static int UPD = 8344;
    ////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    private String mimeToExtension;
    private URL DwnUrl2 = null;
    private String userAgent;
    private String contentDisposition;
    private String mimeType;
    private  int contentLength;
    public static int IntentSND;
    private static int contentLengthh;
    private static int presentDwn;
   static boolean firsttim=true;
    static boolean secondtim=true;
    static boolean thirdtim=true;
    static boolean fourthtim=true;
    static ArrayList<String> filedownloading=new ArrayList<>();
    static ArrayList<Integer> filedownloadingNUM=new ArrayList<>();
    static int filedownloading_SIZE;
    static int activenumber=0;
    //////////////////////////////////////////////////////////////////////
    public synchronized static void setpresentDwn(int presentDwn) {
        DownInfos.presentDwn=presentDwn;
    }
    public  void setcontentLength(int contentLength){
        this.contentLength=contentLength;
        contentLengthh=contentLength;
    }
    public void setmimeType(String mimeType){

        this.mimeType=mimeType;
    }
    public void setuserAgent(String mimeType){

        this.userAgent=userAgent;
    }
    public void setcontentDisposition(String contentDisposition){

        this.contentDisposition=contentDisposition;
    }
    public void setMimeToExtension(String mimeToExtension){

        this.mimeToExtension=mimeToExtension;
    }
    ////////////////////////////////////////////////////////////////////////////////
    public synchronized int getcontentLength(){
        return contentLength;
    }

    public String getmimeType(){
        return  mimeType;
    }
    public String getUserAgent(){
        return  userAgent;
    }
    public String getcontentDisposition(){
        return  contentDisposition;
    }
    public String getMimeToExtension(){
    return mimeToExtension;
    }
    public synchronized static int getpresentDwn(){
        return presentDwn;
    }
    /////////////////////////////////////////////////////////////////////////////
    public void setDwnUrl2(URL DwnUrl2){

        this.DwnUrl2=DwnUrl2;
    }
    public URL getDwnUrl2(){

        return DwnUrl2;
    }

    ////////////////////////////////////////////////////////////
    public static double getProgress()
    {


        return ((double) DownInfos.contentLengthh / DownInfos.presentDwn) * 100;


    }

}

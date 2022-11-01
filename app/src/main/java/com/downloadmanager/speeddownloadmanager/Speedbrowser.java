package com.downloadmanager.speeddownloadmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.net.MalformedURLException;
import java.net.URL;

public class Speedbrowser extends AppCompatActivity {
    ProgressBar Pbar;
    String hj;
    ImageView ImgV;
    WebView speedWebView;
    static String mimeToExtension;
    private String getFileName(URL s) {//filename ke liye connection kholne ki load nahi
        String fileName = s.getFile();
        DownInfos.activenumber=DownInfos.activenumber+1;
       // Toast.makeText(getBaseContext(), "Downloading File"+DownInfos.activenumber, Toast.LENGTH_LONG).show();
        Log.e("getFileName()Real", fileName);
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        fileName=fileName.substring(0,10);
        Log.e("getFileName()", fileName);
        return fileName;
    }
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedbrowser);
        Pbar = (ProgressBar) findViewById(R.id.Pbar);
        ImgV = (ImageView) findViewById(R.id.ImgV);
        speedWebView = (WebView) findViewById(R.id.speedWebView);
        Pbar.setMax(100);
        speedWebView.loadUrl("https://www.google.com");
        speedWebView.getSettings().setJavaScriptEnabled(true);
        speedWebView.setWebViewClient(new WebViewClient());
        speedWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Pbar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                ImgV.setImageBitmap(icon);
            }
        });

        speedWebView.setDownloadListener(new DownloadListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                try {
                    String h=getFileName(new URL(url));
                    DownInfos.filedownloading.add(h);
                    DownInfos.filedownloadingNUM.add(DownInfos.filedownloading.size());
                    Log.e("AleSixe"," "+DownInfos.filedownloading.size());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                Intent jh=new Intent(getBaseContext(),MainActivity.class);
                jh.setAction(DownInfos.DWNINI);
                jh.putExtra("ContentDisposition",contentDisposition);
                jh.putExtra("MimeType",mimeType);
                Log.e("SppedBrow",mimeType);
                String ff=mimeType.substring(mimeType.lastIndexOf('/')+1);
                jh.putExtra("MimeToExtension",ff);
                Log.e("SpeedExtension",ff);
                jh.putExtra("ContentLength",contentLength);
                DownInfos.filedownloading_SIZE=(int)contentLength;
                Log.e("SpeedPencho","N.0"+DownInfos.filedownloading_SIZE);
                jh.putExtra("UserAgent",userAgent);
                jh.putExtra("URL",url);
                startActivity(jh);
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (speedWebView.canGoBack()) {
            speedWebView.goBack();
        } else {
            Intent nn = new Intent(this, MainActivity.class);
            startActivity(nn);
        }
    }

}

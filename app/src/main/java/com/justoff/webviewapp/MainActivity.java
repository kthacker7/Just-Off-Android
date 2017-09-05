package com.justoff.webviewapp;

import com.justoff.webviewapp.R;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.GeolocationPermissions;

import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import android.widget.Toast;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.justoff.webviewapp.R.layout.activity_main);
        webView = (WebView) findViewById(com.justoff.webviewapp.R.id.NewWebview);
        setupWebview();

    }

    private void loadURL() {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        String urlString = ((WebviewApplication) getApplicationContext()).getURLString();
                        webView.loadUrl(urlString);
                    }
                });
            }
        });
    }

    private void showAlert() {
        System.out.println("DebugLog: Showing alert");
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                ProgressBar pb = (ProgressBar) findViewById(com.justoff.webviewapp.R.id.NewProgressBar);
                pb.setVisibility(pb.GONE);
                Toast.makeText(MainActivity.this, "Sorry, network unavailable!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupWebview() {
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.clearCache(true);
        webView.setWebChromeClient(new GeoWebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    //Your code goes here
                    if (isNetworkAvailable()) {
                        System.out.println("Network available");
                        loadURL();
                    } else {
                        System.out.println("Network unavailable");
                        showAlert();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    private boolean isNetworkAvailable() {

        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            System.out.print("debuglog-" + ipAddr);
            return !ipAddr.equals("");

        } catch (Exception e) {
            System.out.print("debuglog- " + e.getStackTrace().toString());
            return false;
        }

    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            System.out.println("DEBUGLOG: Called on page started");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ProgressBar pb = (ProgressBar) findViewById(com.justoff.webviewapp.R.id.NewProgressBar);
                    System.out.println("DEBUGLOG: Testing for timeout - " + pb.getProgress());
                    if(pb.getProgress() < 10) {
                        System.out.println("DEBUGLOG: Timed out");
                    }
                }
            }).start();
        }
    }

    public class GeoWebChromeClient extends WebChromeClient {

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            // Always grant permission since the app itself requires location
            // permission and the user has therefore already granted it
            callback.invoke(origin, true, false);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            ProgressBar pb = (ProgressBar) findViewById(com.justoff.webviewapp.R.id.NewProgressBar);
            pb.setProgress(newProgress);
            if (newProgress == 100) {
                pb.setVisibility(view.GONE);
            } else {
                pb.setVisibility(view.VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }

    };
}
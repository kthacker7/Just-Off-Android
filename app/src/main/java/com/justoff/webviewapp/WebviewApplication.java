package com.justoff.webviewapp;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;

import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by kunalthacker on 1/4/17.
 */

public class WebviewApplication extends Application {
    String urlString;

    public void setUrlString(String s) {
        urlString = s;
    }

    public String getURLString() {
        if (urlString == null || urlString == "") {
            return "http://www.just-off.co.uk/";
        }
        return urlString;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new NotificationHandler())
                .init();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private class NotificationHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            JSONObject data = result.notification.payload.additionalData;
            String customKey;

            if (data != null) {
                customKey = data.optString("url", null);
                System.out.println("DEBUGLOG- " + customKey);
                if (customKey != null) {
                    WebviewApplication webviewApplication = (WebviewApplication )getApplicationContext();
                    webviewApplication.setUrlString(customKey);
                }
                // The following can be used to open an Activity of your choice.
                // Replace - getApplicationContext() - with any Android Context.
                 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
            }
        }
    }

}

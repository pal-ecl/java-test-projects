package com.lescure.testAndroid;

import android.app.Application;

import com.squareup.otto.Bus;

public class MyApplication extends Application {
    private static Bus bus;

    public static Bus getBus() {
        return bus;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new Bus();
    }
}

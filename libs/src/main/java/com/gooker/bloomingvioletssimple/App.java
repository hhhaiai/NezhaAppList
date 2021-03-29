package com.gooker.bloomingvioletssimple;

import android.app.Application;

import com.gooker.bloomingvioletssimple.utils.L;
import com.gooker.bloomingvioletssimple.utils.UninstallApp;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        L.init(true, true, false, false, false, "sanbo");

        UninstallApp.getInstance(this);
    }
}

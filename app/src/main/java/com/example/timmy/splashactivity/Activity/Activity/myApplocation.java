package com.example.timmy.splashactivity.Activity.Activity;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.commonlibrary.utils.Utils;

public class myApplocation extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}

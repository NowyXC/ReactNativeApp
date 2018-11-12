package com.nowy.reactnativeapp;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JSIModulePackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.shell.MainReactPackage;
import com.nowy.reactnativeapp.reactnative.CusReactNativePackage;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class NowyApp extends Application implements ReactApplication {

    public static NowyApp sInstance;

    private ReactContext mReactContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;


    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }


    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(//高级应用：JS调原生模块
                    new MainReactPackage(),
                    new CusReactNativePackage()
            );
        }



        //对应react-native项目的入口文件的名称，此项目为index.js
        @Override
        protected String getJSMainModuleName() {
            return "index";
        }

        @Override
        public ReactInstanceManager getReactInstanceManager() {
            return super.getReactInstanceManager();
        }
    };





    public ReactContext getReactContext(){
        return mReactContext;
    }

    public static NowyApp getInstance(){
        return sInstance;
    }


}

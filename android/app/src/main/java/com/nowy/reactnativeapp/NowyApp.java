package com.nowy.reactnativeapp;

import android.app.Application;
import android.util.Log;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JSIModulePackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.nowy.reactnativeapp.reactnative.CusReactNativePackage;
import com.nowy.reactnativeapp.utils.FileUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class NowyApp extends Application implements ReactApplication {
    public static final String TAG = "NowyApp";
    public static NowyApp sInstance;



    private String mJSMainModuleName ="index";
    private File mJSBundleFile = null;
    private String mJSBundleDirName = "JSBundle";
    private String mJSBundleName = "index.android.bundle";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        SoLoader.init(this, /* native exopackage */ false);
        if(checkRNUpdate()){
            Logger.t(TAG).e("本地存在JSBundle文件");
        }else{
            Logger.t(TAG).e("本地不存在JSBundle文件");
        }
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
            return mJSMainModuleName;
        }

        @Override
        public ReactInstanceManager getReactInstanceManager() {
            return super.getReactInstanceManager();
        }

        //*******************************
        //  热更新
        //*******************************
        //
        //  String jsBundleFile = getJSBundleFile();
        //  if (jsBundleFile != null) {
        //      builder.setJSBundleFile(jsBundleFile);
        //  } else {
        //      builder.setBundleAssetName(Assertions.assertNotNull(getBundleAssetName()));
        //  }
        //*******************************
        @Nullable
        @Override
        protected String getBundleAssetName() {
            return super.getBundleAssetName();
        }

        @Nullable
        @Override
        protected String getJSBundleFile() {
            if (mJSBundleFile != null && mJSBundleFile.exists()) {
                return mJSBundleFile.getPath();
            }
            return super.getJSBundleFile();
        }
    };

    /**
     * 检测RN更新
     * @return true:存在本地RN文件
     */
    private boolean checkRNUpdate(){
        mJSBundleFile = null;
        File mJSBundleDir = new File(FileUtil.getAppPath(this),mJSBundleDirName);
        if(!mJSBundleDir.exists()){
            Logger.t(TAG).e("没有检测到文件夹："+mJSBundleDir.getPath());
            mJSBundleDir.mkdirs();
            return false;
        }else{
            String mJSBundleFilePath = mJSBundleDir.getPath()+File.separator+mJSBundleName;
            mJSBundleFile = new File(mJSBundleFilePath);
            if(mJSBundleFile.exists()){
                Logger.t(TAG).e("检测到新的"+mJSBundleName);
                return true;
            }
            return false;
        }
    }



    public static NowyApp getInstance(){
        return sInstance;
    }


}

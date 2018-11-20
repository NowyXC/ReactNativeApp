package com.nowy.reactnativeapp.reactnative.preLoad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactRootView;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;
import com.orhanobut.logger.Logger;

import javax.annotation.Nullable;

public class PreReactActivityDelegate extends ReactActivityDelegate {
    private final @Nullable Activity mActivity;
    private final @Nullable FragmentActivity mFragmentActivity;
    private final @Nullable String mMainComponentName;

    private @Nullable ReactRootView mReactRootView;


    public PreReactActivityDelegate(Activity activity, @Nullable String mainComponentName) {
        super(activity, mainComponentName);
        this.mActivity = activity;
        this.mFragmentActivity = null;
        this.mMainComponentName = mainComponentName;
    }

    public PreReactActivityDelegate(FragmentActivity fragmentActivity, @Nullable String mainComponentName) {
        super(fragmentActivity, mainComponentName);
        this.mFragmentActivity = fragmentActivity;
        this.mMainComponentName = mainComponentName;
        this.mActivity = null;
    }


    protected void loadApp(String appKey) {
        if (mReactRootView != null) {
            throw new IllegalStateException("Cannot loadApp while app is already running.");
        }

        // 1.从缓存中获取RootView
        mReactRootView = ReactNativePreLoader.getReactRootView(mMainComponentName);
        if(mReactRootView == null) {
            // 2.缓存中不存在RootView,直接创建
            mReactRootView = new ReactRootView(mActivity);
            mReactRootView.startReactApplication(
                    getReactNativeHost().getReactInstanceManager(),
                    appKey,
                    getLaunchOptions());
            ReactNativePreLoader.put(mMainComponentName,mReactRootView);
        }

        getPlainActivity().setContentView(mReactRootView);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 清除View
        ReactNativePreLoader.detachView(mMainComponentName);
    }




    private Activity getPlainActivity() {
        return ((Activity) getContext());
    }

    private Context getContext() {
        if (mActivity != null) {
            return mActivity;
        }
        return Assertions.assertNotNull(mFragmentActivity);
    }

}

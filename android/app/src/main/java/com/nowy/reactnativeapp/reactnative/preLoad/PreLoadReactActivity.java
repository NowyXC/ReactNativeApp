package com.nowy.reactnativeapp.reactnative.preLoad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import javax.annotation.Nullable;

/**
 * 预加载Activity基类
 */
public class PreLoadReactActivity extends ReactActivity  {

    /**
     * Called at construction time, override if you have a custom delegate implementation.
     */
    protected ReactActivityDelegate createReactActivityDelegate() {
        return new PreReactActivityDelegate(this, getMainComponentName());
    }
}


package com.nowy.reactnativeapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.react.ReactActivity;

public class ReactAty extends ReactActivity {
    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        //对应AppRegistry.registerComponent(appName, () => Test);中的appName,此处为ReactNativeApp
        return "ReactNativeApp";
    }
}

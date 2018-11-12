package com.nowy.reactnativeapp;

import android.os.Bundle;

import com.facebook.react.ReactActivity;
import com.nowy.reactnativeapp.reactnative.WindowModule;

public class ReactAty extends ReactActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendWindowMsg();
    }

    private void sendWindowMsg(){
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                if(getReactInstanceManager().getCurrentReactContext() == null ){
                    if(!isFinishing())
                        getWindow().getDecorView().postDelayed(this,1000);
                }else{
                    WindowModule.sendWindowInfo(getReactInstanceManager().getCurrentReactContext());
                }
            }
        });
    }


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

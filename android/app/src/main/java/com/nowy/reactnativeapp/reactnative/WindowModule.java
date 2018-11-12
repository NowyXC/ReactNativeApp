package com.nowy.reactnativeapp.reactnative;

import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

public class WindowModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "WindowModule";
    public static final String WINDOW_INFO_EVENT = "windowInfoEvent";
    public static final String WINDOW_INFO_HEIGHT = "windowInfoHeight";
    public static final String WINDOW_INFO_WIDTH = "windowInfoWidth";



    public WindowModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    private static void sendMsg2Rn(ReactContext reactContext,
                            String eventName,
                            @Nullable WritableMap params){
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }


    public static WritableMap generateWritableMap(){
        return Arguments.createMap();
    }


    public static void sendWindowInfo(ReactContext reactContext){
        WritableMap params = generateWritableMap();
        DisplayMetrics dm = reactContext.getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        params.putInt(WINDOW_INFO_HEIGHT,height);
        params.putInt(WINDOW_INFO_WIDTH,width);
        sendMsg2Rn(reactContext,WINDOW_INFO_EVENT,params);
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();
        constants.put(WINDOW_INFO_EVENT, WINDOW_INFO_EVENT);
        constants.put(WINDOW_INFO_HEIGHT, WINDOW_INFO_HEIGHT);
        constants.put(WINDOW_INFO_WIDTH, WINDOW_INFO_WIDTH);
        return constants;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }
}

package com.nowy.reactnativeapp.reactnative;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.util.Random;

public class RefreshModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "RefreshModule";

    public ReactApplicationContext mReactContext;

    public RefreshModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

//    Boolean -> Bool
//    Integer -> Number
//    Double -> Number
//    Float -> Number
//    String -> String
//    Callback -> function
//    ReadableMap -> Object
//    ReadableArray -> Array
    @ReactMethod
    public void refresh(String msg, Callback successCallback,Callback errorCallback){
        Random random = new Random();
        if(random.nextBoolean()){//注意react-native没有Long类型的转化
            successCallback.invoke(msg+"已修改",(int)(System.currentTimeMillis()/1000),"刷新成功！");
        }else{
            errorCallback.invoke("刷新失败！");
        }
    }

}

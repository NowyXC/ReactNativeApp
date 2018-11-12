package com.nowy.reactnativeapp.reactnative;

import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * 创建交互模块——ToastSample
 * 主要操作：
 * 1.声明JS组件名，对应react-native端的 NativeModules.ToastSample
 * 2.声明react-native方法，注解(@ReactMethod)标记。
 * 3.声明常量参数getConstants()
 *
 * react-native中调用示例：
 *
 * 直接调用：
 * NativeModules.ToastSample.show("msg", NativeModulesToastSample.SHORT);
 *
 * //定义ToastSample.js文件，简化调用
 * import { NativeModules } from "react-native"; //导入NativeModules组件
 * module.exports = NativeModules.ToastSample; //导出 ToastSample组件，为了方便调用
 *
 * //调用方式
 * import ToastSample from "./ToastSample";
 * ToastSample.show("msg", ToastSample.SHORT);
 *
 *
 */
public class ToastModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "ToastSample";
    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";

    public ToastModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    //对应JS端的 NativeModules.ToastSample
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        return constants;
    }

    @ReactMethod //交互为异步，只能通过回调返回
    public void show(String msg,int duration){
        Toast.makeText(getReactApplicationContext(), msg, duration).show();
    }
}

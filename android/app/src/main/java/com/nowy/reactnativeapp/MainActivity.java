package com.nowy.reactnativeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nowy.reactnativeapp.reactnative.preLoad.ReactNativePreLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //方法一：通过ReactRootView.startReactApplication(...)预加载界面和隐式调用createReactContextInBackground()
        //ReactNativePreLoader.preLoad(this,"ReactNativeApp");

        //方法二：通过createReactContextInBackground预加载ReactContext
        if(NowyApp.getInstance().getReactNativeHost().getReactInstanceManager().hasStartedCreatingInitialContext()){
            Log.e("MainActivity","已经开始创建ReactContext");
        }else {
            Log.e("MainActivity","未在创建ReactContext");
            NowyApp.getInstance().getReactNativeHost().getReactInstanceManager().createReactContextInBackground();
        }

        findViewById(R.id.tv_ReactNativePage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ReactAty.class));
            }
        });


    }
}

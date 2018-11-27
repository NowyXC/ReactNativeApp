package com.nowy.reactnativeapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nowy.RNUpdateManagerLib.OnUpdateListener;
import com.nowy.RNUpdateManagerLib.RNUpdateManager;
import com.nowy.RNUpdateManagerLib.UpdateInfo;
import com.nowy.RNUpdateManagerLib.utils.AssetsUtil;
import com.nowy.RNUpdateManagerLib.utils.FileUtil;
import com.nowy.reactnativeapp.dialog.LoadDialog;
import com.nowy.reactnativeapp.reactnative.preLoad.ReactNativePreLoader;
import com.nowy.reactnativeapp.utils.XPermissionUtils;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_PERMISSION = 13;


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

        final TextView tvRNPage = findViewById(R.id.tv_ReactNativePage);
        tvRNPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ReactAty.class));
            }
        });

        findViewById(R.id.tv_ReactNativeUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadDialog.show(MainActivity.this,"更新中...");
                XPermissionUtils.requestPermissions(
                        MainActivity.this,
                        REQ_CODE_PERMISSION, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                        }, new XPermissionUtils.OnPermissionListener() {
                            @Override
                            public void onPermissionGranted() {

                                RNUpdateManager.update(MainActivity.this,
                                        new UpdateInfo.Builder(MainActivity.this)
                                                .extBundleDirPath(NowyApp.getInstance().getJSBundleDirPath())
                                                .downUrl("https://github.com/NowyXC/RNUpdateManager/raw/master/raw/version0.3.zip")
                                                .isPat()
                                                .build(),
                                        new OnUpdateListener() {
                                            @Override
                                            public void onError(int code, String msg) {
                                                Log.e("test","错误信息:"+msg);
                                            }

                                            @Override
                                            public void onDeCompressFinish(String outPath) {

                                            }

                                            @Override
                                            public void onMergeFinish(String bundlePath) {

                                            }

                                            @Override
                                            public void onFinish(String bundlePath) {
                                                Log.e("test","完成:"+bundlePath);
                                               NowyApp.getInstance().setJSBundleFile(bundlePath);
                                                Toast.makeText(getApplicationContext(),"更新完成",Toast.LENGTH_SHORT).show();

                                                tvRNPage.setText("跳转到react-native界面(新)");


                                                if( NowyApp.getInstance().getReactNativeHost().hasInstance()){
                                                    NowyApp.getInstance().getReactNativeHost().clear();
                                                }

                                                NowyApp.getInstance().getReactNativeHost().getReactInstanceManager()
                                                        .createReactContextInBackground();
                                                //预加载需要时间
                                                getWindow().getDecorView().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if(!isFinishing())
                                                            LoadDialog.dismiss(MainActivity.this);
                                                    }
                                                },2000);

                                            }
                                        });
                            }




                            @Override
                            public void onPermissionDenied(String[] deniedPermissions, boolean alwaysDenied) {

                            }
                        });


            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        XPermissionUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadDialog.dismiss(MainActivity.this);
    }
}

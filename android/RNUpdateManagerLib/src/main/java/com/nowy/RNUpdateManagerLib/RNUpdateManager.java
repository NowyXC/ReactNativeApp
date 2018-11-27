package com.nowy.RNUpdateManagerLib;


import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.nowy.RNUpdateManagerLib.diffPatch.PatchUtil;
import com.nowy.RNUpdateManagerLib.net.download.DownloadManager;
import com.nowy.RNUpdateManagerLib.utils.AssetsUtil;
import com.nowy.RNUpdateManagerLib.utils.FileUtil;
import com.nowy.RNUpdateManagerLib.zip.SampleOnCompressListener;
import com.nowy.RNUpdateManagerLib.zip.ZipUtil;
import com.nowy.compressedfilelib.listener.OnCompressListener;

import java.io.File;

import rx.Observable;

public class RNUpdateManager {

    private RNUpdateManager(){
    }


    public static void update(final Context context, @NonNull final UpdateInfo updateInfo, final OnUpdateListener listener){
        DownloadManager.getInstance()
                .start(updateInfo.getDownUrl(), updateInfo.getZipFilePath(), new DownloadManager.DownloadListener() {
                    @Override
                    public void progressChanged(long read, long contentLength, boolean done) {
                    }

                    @Override
                    public void onFinish(String savePath) {
                        ZipUtil.deCompress(new File(updateInfo.getZipFilePath()),
                                updateInfo.getExtBundleDirPath(), new SampleOnCompressListener() {
                                    @Override
                                    public void onFinish(String outDirPath) {
                                        if(listener != null){
                                            listener.onDeCompressFinish(outDirPath);
                                        }
                                        if(updateInfo.isPat()){//差分包
                                            new MergeAndSaveTask(context,updateInfo,listener).execute();
                                        }else{
                                            if(listener != null){
                                                listener.onFinish(updateInfo.getExtBundleFilePath());
                                            }
                                        }
                                        FileUtil.deleteFile(new File(updateInfo.getZipFilePath()));
                                    }

                                    @Override
                                    public void onError(int errorMsg) {
                                        if(listener != null){
                                            listener.onError(OnUpdateListener.CODE_ERROR_DECOMPRESS,
                                                    "解压异常,错误码:"+errorMsg+",错误码请对照ExitCode类");
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onError(String msg) {
                        if(listener != null){
                            listener.onError(OnUpdateListener.CODE_ERROR_DOWN,msg);
                        }
                    }
                });
    }










}

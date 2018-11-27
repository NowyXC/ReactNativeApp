package com.nowy.RNUpdateManagerLib;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.nowy.RNUpdateManagerLib.diffPatch.PatchUtil;
import com.nowy.RNUpdateManagerLib.diffPatch.diff_match_patch;
import com.nowy.RNUpdateManagerLib.utils.AssetsUtil;
import com.nowy.RNUpdateManagerLib.utils.FileUtil;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class MergeAndSaveTask extends AsyncTask<String,Integer,Boolean> {
        private Reference<Context> mContextRef;
        private UpdateInfo mUpdateInfo;
        private OnUpdateListener mListener;
        MergeAndSaveTask(Context context,UpdateInfo updateInfo,OnUpdateListener listener){
            this.mContextRef = new WeakReference<>(context);
            this.mUpdateInfo = updateInfo;
            this.mListener = listener;
        }
        @Override
        protected Boolean doInBackground(String... strings) {
            boolean isSuccess ;
            try {
                isSuccess = mergeAndSave(mContextRef.get(),mUpdateInfo);
            }catch (Exception e){
                isSuccess = false;
            }

            return isSuccess;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(mListener != null){
                if(isSuccess){
                    mListener.onMergeFinish(mUpdateInfo.getExtBundleFilePath());
                    if(mListener != null){
                        mListener.onFinish(mUpdateInfo.getExtBundleFilePath());
                    }
                }else{
                    mListener.onError(OnUpdateListener.CODE_ERROR_MERGE,"合并失败");
                }

            }

        }

        private boolean mergeAndSave(Context context, UpdateInfo updateInfo){
            boolean isSuccess = false;
            String originalBundleStr;
            if(updateInfo.originInAssets()){//源文件在assets中
                originalBundleStr = AssetsUtil.getFromAssets(context,updateInfo.getOriginalBundlePath());
            }else{
                originalBundleStr = FileUtil.readFile(updateInfo.getOriginalBundlePath());
            }

            String patchStr = FileUtil.readFile(updateInfo.getPatFilePath());

            String newBundleStr = PatchUtil.merge(originalBundleStr,patchStr);



            if(!TextUtils.isEmpty(newBundleStr)){
                FileUtil.writeFile(new File(updateInfo.getExtBundleFilePath()),newBundleStr);
                FileUtil.deleteFile(new File(updateInfo.getPatFilePath()));
                isSuccess = true;
            }
            return isSuccess;
        }



}

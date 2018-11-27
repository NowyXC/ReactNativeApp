package com.nowy.RNUpdateManagerLib;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.nowy.RNUpdateManagerLib.utils.AssetsUtil;
import com.nowy.RNUpdateManagerLib.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;

public class UpdateInfo {
    public static final String RN_DATA_DIR_DEF = "RNData";//默认存放外部bundle文件夹名称
    public static final String RN_BUNDLE_NAME_DEF = "index.android.bundle";//默认存放外部bundle文件夹名称
    public static final String RN_ZIP_NAME_DEF = "RNUpdate.zip";//压缩包名称
    public static final String RN_PAT_NAME_DEF = "RNUpdate.pat";//差分包名称
    private String mDownUrl;//下载路径
    private String mExtBundleDirPath;//外部bundle所在目录名称
    private String mExtBundleName;//外部bundle文件名
    private String mExtBundleFilePath;//外部bundle文件路径
    private boolean mIsPat = false;//是否差分包
    private boolean mOriginInAssets;//bundle源文件是否存在assets中
    private String mOriginalBundlePath;//原bundle文件路径，默认assets根目录
    private String mZipFilePath;//下载的压缩包存放路径
    private String mPatFilePath;//差分包路径
    private String mPatName;//差分包名称
    private UpdateInfo(){
    }


    public static class Builder{
        String mDownUrl;
        String mExtBundleDirPath;
        String mExtBundleName;
        boolean mIsPat;
        Context mContext;
        String mOriginalBundlePath;
        boolean mOriginInAssets = true;
        String mPatFilePath;
        String mPatName;

        public Builder(Context context){
            this.mContext = context;
        }

        public Builder downUrl(String url){
            this.mDownUrl = url;
            return this;
        }

        public Builder extBundleDirPath(String extBundleDirPath){
            this.mExtBundleDirPath = extBundleDirPath;
            return this;
        }

        public Builder extBundleName(String extBundleName){
            this.mExtBundleName = extBundleName;
            return this;
        }

        public Builder isPat(){
            this.mIsPat = true;
            return this;
        }

        public Builder patFilePath(String patFilePath){
            this.mPatFilePath = patFilePath;
            return this;
        }

        public Builder patFileName(String patName){
            this.mPatName = patName;
            return this;
        }



        public Builder originBundle(boolean inAssets,String originalBundlePath){
            this.mOriginInAssets = inAssets;
            this.mOriginalBundlePath = originalBundlePath;
            return this;
        }

        private void apply(UpdateInfo info){
            info.mDownUrl = this.mDownUrl;
            info.mExtBundleDirPath = this.mExtBundleDirPath;
            info.mExtBundleName = this.mExtBundleName;
            info.mExtBundleFilePath = new File(this.mExtBundleDirPath,this.mExtBundleName).getAbsolutePath();
            info.mZipFilePath = new File(this.mExtBundleDirPath,RN_ZIP_NAME_DEF).getAbsolutePath();
            info.mPatName = this.mPatName;
            info.mPatFilePath = this.mPatFilePath;
            info.mIsPat = this.mIsPat;

            info.mOriginInAssets = this.mOriginInAssets;
            info.mOriginalBundlePath = this.mOriginalBundlePath;
        }

        public UpdateInfo build(){
            if(TextUtils.isEmpty(mDownUrl)){
                throw new NullPointerException("下载地址不能为空");
            }
            if(TextUtils.isEmpty(mExtBundleDirPath)){//默认您使用文件目录作为bundle文件存放目录
                mExtBundleDirPath = FileUtil.getRootPath(mContext,RN_DATA_DIR_DEF);
            }

            File bundleDir = new File(mExtBundleDirPath);
            if(!bundleDir.exists()){
                bundleDir.mkdirs();
            }

            mExtBundleDirPath = bundleDir.getAbsolutePath();


            if(TextUtils.isEmpty(mExtBundleName)){
                mExtBundleName = RN_BUNDLE_NAME_DEF;
            }

            if(TextUtils.isEmpty(mOriginalBundlePath)){//默认在assets下面
                this.mOriginalBundlePath = RN_BUNDLE_NAME_DEF;
            }

            if(this.mIsPat){
                if(TextUtils.isEmpty(mPatName)){
                    mPatName = RN_PAT_NAME_DEF;
                }

                if(TextUtils.isEmpty(mPatFilePath)){
                    mPatFilePath =  new File(mExtBundleDirPath,mPatName).getAbsolutePath();
                }
            }


            UpdateInfo updateInfo= new UpdateInfo();
            apply(updateInfo);
            return updateInfo;
        }

    }


    public String getDownUrl() {
        return mDownUrl;
    }

    public String getExtBundleDirPath() {
        return mExtBundleDirPath;
    }

    public String getExtBundleName() {
        return mExtBundleName;
    }

    public String getExtBundleFilePath() {
        return mExtBundleFilePath;
    }

    public boolean isPat() {
        return mIsPat;
    }

    public String getOriginalBundlePath() {
        return mOriginalBundlePath;
    }

    public String getZipFilePath() {
        return mZipFilePath;
    }

    public boolean originInAssets() {
        return mOriginInAssets;
    }

    public String getPatFilePath() {
        return mPatFilePath;
    }

    public String getPatName() {
        return mPatName;
    }

    @Override
    public String toString() {
        return "[\n" +
                "下载路径为：" +mDownUrl + ",\n"+
                "压缩包路径:" +mZipFilePath + ",\n"+
                "外部bundle所在目录:" +mExtBundleDirPath + ",\n"+
                "外部bundle文件名:" +mExtBundleName + ",\n"+
                "外部bundle文件路径:" +mExtBundleFilePath + ",\n"+
                "是否差分包：" +mIsPat + ",\n"+
                (isPat()?
                        "差分包名：" + mPatName + ",\n"+
                        "差分包路径：" + mPatFilePath + ",\n"
                        :"")+
                "源文件路径为：" +(mOriginInAssets ?"assets文件夹下：":"外部文件：")
                +mOriginalBundlePath + ",\n"+
                "]";
    }
}

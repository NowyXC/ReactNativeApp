package com.nowy.RNUpdateManagerLib;

public interface OnUpdateListener{
        int CODE_ERROR_DOWN = -1; //下载异常
        int CODE_ERROR_DECOMPRESS = -2; //解压异常
        int CODE_ERROR_MERGE = -3;   //合并异常


        void onError(int code,String msg);

        void onDeCompressFinish(String outPath);
        
        void onMergeFinish(String bundlePath);

        /**
         * 全部完成
         * @param bundlePath
         */
        void onFinish(String bundlePath);
    }
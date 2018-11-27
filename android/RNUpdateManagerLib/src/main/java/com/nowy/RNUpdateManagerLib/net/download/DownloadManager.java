package com.nowy.RNUpdateManagerLib.net.download;

import android.util.Log;

import com.nowy.RNUpdateManagerLib.net.ApiManager;
import com.nowy.RNUpdateManagerLib.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * https://blog.csdn.net/impure/article/details/79658098
 */
public class DownloadManager implements DownloadProgressListener {
 
    private DownloadInfo mDownloadInfo;
//    private ProgressListener progressObserver;
    private DownloadListener mDownloadListener;


    private DownloadManager() {
        mDownloadInfo = new DownloadInfo();
    }
 
    public static DownloadManager getInstance() {
        return Holder.manager;
    }
 
    public static class Holder {
        private static DownloadManager manager = new DownloadManager();
    }



    /**
     * 开始下载
     * @param url
     */
    public void start(String url,String savePath,DownloadListener downloadListener) {
        mDownloadInfo.setUrl(url);
        mDownloadInfo.setSavePath(savePath);
        downLoad(downloadListener);
    }

 
    /**
     * 开始下载
     */
    private void downLoad(final DownloadListener downloadListener) {
        if(downloadListener != null)
            this.setOnDownloadListener(downloadListener);

        /* 读取下载写入文件，并把ResponseBody转成DownInfo */
        ApiManager.getFileApi(this)
               .download("bytes=" + mDownloadInfo.getReadLength() + "-", mDownloadInfo.getUrl())
                .retryWhen(new RetryWhenNetworkException())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InputStream>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mDownloadListener != null) {
                            mDownloadListener.onError(e == null?"下载异常":e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(InputStream inputStream) {
                        FileUtil.writeFile(new File(mDownloadInfo.getSavePath()),inputStream);
                        if(mDownloadListener != null){
                            mDownloadListener.onFinish(mDownloadInfo.getSavePath());
                        }
                    }
                });
    }


    @Override
    public void progress(long read, final long contentLength, final boolean done) {
        if (mDownloadInfo.getContentLength() > contentLength) {
            read = read + (mDownloadInfo.getContentLength() - contentLength);
        } else {
            mDownloadInfo.setContentLength(contentLength);
        }
        mDownloadInfo.setReadLength(read);

        Observable.just(1).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (mDownloadListener != null) {
                            mDownloadListener.progressChanged(mDownloadInfo.getReadLength(), mDownloadInfo.getContentLength(), done);
                        }
                    }
                });
    }

 
    /**
     * 进度监听
     */
    public interface ProgressListener {
        void progressChanged(long read, long contentLength, boolean done);


    }

    public void setOnDownloadListener(DownloadListener downloadListener) {
        this.mDownloadListener = downloadListener;
    }

    public interface DownloadListener{
        void onError(String msg);
        void progressChanged(long read, long contentLength, boolean done);
        void onFinish(String savePath);
    }


    private static void writeCache(ResponseBody responseBody, File file, DownloadInfo info) throws Exception {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        long allLength;
        if (info.getContentLength() == 0) {
            allLength = responseBody.contentLength();
        } else {
            allLength = info.getContentLength();
        }

        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(file, "rwd");
        channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                info.getReadLength(), allLength - info.getReadLength());
        byte[] buffer = new byte[1024 * 4];
        int len;
        int record = 0;
        while ((len = responseBody.byteStream().read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        responseBody.byteStream().close();
        if (channelOut != null) {
            channelOut.close();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }

}

package com.nowy.RNUpdateManagerLib.net;


import com.nowy.RNUpdateManagerLib.net.download.DownloadInterceptor;
import com.nowy.RNUpdateManagerLib.net.download.DownloadProgressListener;
import com.nowy.RNUpdateManagerLib.utils.UnicodeUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nowy on 2016/10/13.
 * 网络访问管理类
 * 主要实例化Retrofit，添加请求头和管理打印日志
 */
public class ApiManager {
    public static final String TAG = ApiManager.class.getSimpleName();
    private static RxNetWorkApi sRxNetWorkApi;
    private static final CallAdapter.Factory RXJAVA_CALL_ADAPTER_FACTORY = RxJavaCallAdapterFactory.create();

    private static final long TIME_OUT_CONNECT = 25;//秒
    private static final long TIME_OUT_READ = 30;//秒




    /**
     * 文件传输的api,只采取默认的超时设置
     * @return
     */
    public static RxNetWorkApi getFileApi(DownloadProgressListener listener) {
        if (sRxNetWorkApi == null) {
            final DownloadInterceptor interceptor = new DownloadInterceptor(listener);
            sRxNetWorkApi = getRetrofit(Api.BASE_API,buildOkHttp()
                    .connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build())
                    .create(RxNetWorkApi.class);
        }
        return sRxNetWorkApi;
    }


    private static Retrofit getRetrofit(String baseUrl,OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RXJAVA_CALL_ADAPTER_FACTORY)
                .build();
    }



    /**
     * 构建基础okHttp实例
     * 设置日志
     * 设置请求头
     * //后期可能设置cookie
     * @return
     */
    private static OkHttpClient.Builder buildOkHttp(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(createHttpLog());
        builder.addNetworkInterceptor(new HeaderInterceptor());

        return builder;
    }


    private static class HeaderInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .build();
            return chain.proceed(request);
        }
    }


    private static HttpLoggingInterceptor createHttpLog() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.t(TAG).i(UnicodeUtil.decode(message));
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logInterceptor;
    }
}

package com.nowy.RNUpdateManagerLib.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface RxNetWorkApi {

    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String fileUrl);
}

package com.hisu.smart.dj.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaydenxiao.common.baserx.GsonDConverterFactory;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * @author lichee
 */
public class Api {
    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 30 * 1000;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 30 * 1000;
    public Retrofit retrofit;
    public ApiService movieService;

    private static Context sContext;

    private static final String TAG = "Api";
    public static final String GET = "GET";
    public static final String POST = "POST";
    private static HashMap<String,Api> sRetrofitManager = new HashMap<>();
    /*************************缓存设置*********************/
/*
    1. noCache 不使用缓存，全部走网络

    2. noStore 不使用缓存，也不存储缓存

    3. onlyIfCached 只使用缓存

    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言

    6. minFresh 设置有效时间，依旧如上

    7. FORCE_NETWORK 只走网络

    8. FORCE_CACHE 只走缓存*/

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";


    /**
     *  增加统一请求参数
     */
    Interceptor paramsInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //获取到request
            Request request = chain.request();
            //获取到方法
            String method = request.method();
            //get请求的封装
            if(method.equals(GET)){
                //获取到请求地址api
                HttpUrl httpUrl = request.url();
                String url = httpUrl.toString();
                if(url.indexOf("?") > 0){
                    url = url + "&requestUser=hotel&requestPassword=123456";
                }else{
                    url = url + "?requestUser=hotel&requestPassword=123456";
                }
                LogUtils.logd(TAG,"GET=====>"+url);
                request = request.newBuilder().url(url).build();
            }else if(method.equals(POST)){
                // 构造新的请求表单
                FormBody.Builder builder = new FormBody.Builder();
                FormBody body;
                RequestBody requestBody = request.body();
                if( requestBody instanceof FormBody ){
                    body = (FormBody) request.body();
                    //将以前的参数添加
                    for (int i = 0; i < body.size(); i++) {
                        builder.add(body.encodedName(i), body.encodedValue(i));
                    }
                    //追加新的参数
                    builder.add("requestUser", "hotel");
                    builder.add("requestPassword", "123456");
                    //构造新的请求体
                    request = request.newBuilder().post(builder.build()).build();
                }
            }
            return chain.proceed(request);
        }
    };
    /**
     * 设置Cookie
     */
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    CookieJar cookieJar = new CookieJar() {
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    };

    //构造方法私有
    private Api(Context context,String baseUrl) {
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存
        File cacheFile = new File(context.getCacheDir(), "cache");
        //100Mb
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(build);
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
                .addInterceptor(paramsInterceptor)
                .cache(cache)
                .cookieJar(cookieJar)
                .build();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonDConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        movieService = retrofit.create(ApiService.class);
    }

    /**

     */
    public static ApiService getDefault(Context context,String baseUrl) {
        Api retrofitManager = sRetrofitManager.get(baseUrl);
        if (retrofitManager == null) {
            sContext = context;
            retrofitManager = new Api(context,baseUrl);
            sRetrofitManager.put(baseUrl, retrofitManager);
        }
        return retrofitManager.movieService;
    }


    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return NetWorkUtils.isNetConnected(sContext) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String cacheControl = request.cacheControl().toString();
            if (!NetWorkUtils.isNetConnected(sContext)) {
                request = request.newBuilder()
                        .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetConnected(sContext)) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置

                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

}

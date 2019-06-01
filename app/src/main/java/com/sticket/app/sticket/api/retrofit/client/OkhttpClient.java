package com.sticket.app.sticket.api.retrofit.client;

import android.content.Context;
import android.webkit.CookieManager;

import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class OkhttpClient {

    private OkHttpClient okclient;
    private volatile static OkhttpClient instance;
    //public ClearableCookieJar cookieJar;

    public static OkhttpClient getInstance(Context context){
        if(instance == null){
            synchronized(OkhttpClient.class){
                if(instance == null){
                    instance = new OkhttpClient(context);
                }
            }
        }
        return instance;
    }

    public OkhttpClient(Context context) {
        //cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okclient =new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .connectTimeout(2, java.util.concurrent.TimeUnit.SECONDS)
//                .readTimeout(2,java.util.concurrent.TimeUnit.SECONDS)
//                .cookieJar(new WebviewCookieHandler())
//                .build();
    }
    public final class WebviewCookieHandler implements CookieJar {
        private CookieManager webviewCookieManager = CookieManager.getInstance();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            String urlString = url.toString();

            for (Cookie cookie : cookies) {
                webviewCookieManager.setCookie(urlString, cookie.toString());
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            String urlString = url.toString();
            String cookiesString = webviewCookieManager.getCookie(urlString);

//            if (cookiesString != null && !cookiesString.isEmpty()) {
//                We can split on the ';' char as the cookie manager only returns cookies
//                that match the url and haven't expired, so the cookie attributes aren't included
//                String[] cookieHeaders = cookiesString.split(";");
//                List<Cookie> cookies = new ArrayList<>(cookieHeaders.length);
//
//                for (String header : cookieHeaders) {
//                    cookies.add(Cookie.parse(url, header));
//                }
//
//                return cookies;
//            }
//
            return Collections.emptyList();
        }
    }

    public OkHttpClient getOkclient() {
        return okclient;
    }
}


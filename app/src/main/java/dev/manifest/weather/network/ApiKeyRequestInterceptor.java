package dev.manifest.weather.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyRequestInterceptor implements Interceptor {

    private String apiKey;
    private String units;

    public ApiKeyRequestInterceptor(String apiKey, String units) {
        this.apiKey = apiKey;
        this.units = units;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalUrl = originalRequest.url();

        HttpUrl newUrl = originalUrl.newBuilder()
                .addQueryParameter("APPID", apiKey)
                .addQueryParameter("units", units)
                .build();

        Request newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build();

        return chain.proceed(newRequest);
    }
}

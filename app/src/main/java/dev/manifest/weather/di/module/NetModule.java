package dev.manifest.weather.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.manifest.weather.BuildConfig;
import dev.manifest.weather.data.repo.WeatherDataSource;
import dev.manifest.weather.data.repo.RemoteWeatherDataSource;
import dev.manifest.weather.di.annotation.Remote;
import dev.manifest.weather.network.ApiKeyRequestInterceptor;
import dev.manifest.weather.network.WeatherApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private static final String UNITS = "metric";

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    ApiKeyRequestInterceptor provideApiKeyRequestInterceptor() {
        return new ApiKeyRequestInterceptor(BuildConfig.API_KEY, UNITS);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(ApiKeyRequestInterceptor apiKeyRequestInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(apiKeyRequestInterceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS);
        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    WeatherApi provideWeatherApi(Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }

    @Provides
    @Singleton
    @Remote
    WeatherDataSource provideRemoteWeatherDataSource(WeatherApi weatherApi) {
        return new RemoteWeatherDataSource(weatherApi);
    }
}

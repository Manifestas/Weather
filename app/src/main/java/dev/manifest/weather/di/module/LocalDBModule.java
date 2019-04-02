package dev.manifest.weather.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.manifest.weather.data.repo.LocalWeatherDataSource;
import dev.manifest.weather.data.repo.WeatherDataSource;
import dev.manifest.weather.di.annotation.Local;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class LocalDBModule {

    private static final int DB_VERSION = 1;

    @Provides
    @Singleton
    RealmConfiguration provideRealmCOnfiguration(Context context) {
        Realm.init(context);
        return new RealmConfiguration.Builder()
                .schemaVersion(DB_VERSION)
                .build();
    }

    @Provides
    @Singleton
    Realm provideRealm(RealmConfiguration realmConfig) {
        Realm.setDefaultConfiguration(realmConfig);
        try {
            return Realm.getDefaultInstance();
        } catch (Exception e) {
            Realm.deleteRealm(realmConfig);
            Realm.setDefaultConfiguration(realmConfig);
            return Realm.getDefaultInstance();
        }
    }

    @Provides
    @Singleton
    @Local
    WeatherDataSource provideLocalWeatherDataSource(Realm realm) {
        return new LocalWeatherDataSource(realm);
    }
}

package dev.manifest.weather.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.manifest.weather.data.repo.WeatherDataSource;
import dev.manifest.weather.data.repo.WeatherRepository;
import dev.manifest.weather.di.annotation.Local;
import dev.manifest.weather.di.annotation.Remote;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    WeatherRepository provideWeatherRepository(@Local WeatherDataSource localDataSource,
                                               @Remote WeatherDataSource remoteDataSource) {
        return new WeatherRepository(localDataSource, remoteDataSource);
    }
}

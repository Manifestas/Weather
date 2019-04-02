package dev.manifest.weather.di.component;

import javax.inject.Singleton;

import dagger.Component;
import dev.manifest.weather.App;
import dev.manifest.weather.data.repo.WeatherRepository;
import dev.manifest.weather.di.module.AppModule;
import dev.manifest.weather.di.module.LocalDBModule;
import dev.manifest.weather.di.module.NetModule;
import dev.manifest.weather.di.module.RepositoryModule;

@Singleton
@Component(modules = {AppModule.class,
        NetModule.class,
        LocalDBModule.class,
        RepositoryModule.class})
public interface RepositoryComponent {

    void injectInto(App app);

    WeatherRepository getWeatherRepository();
}

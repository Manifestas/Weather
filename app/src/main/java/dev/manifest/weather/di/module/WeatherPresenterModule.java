package dev.manifest.weather.di.module;

import dagger.Module;
import dagger.Provides;
import dev.manifest.weather.di.annotation.ActivityScope;
import dev.manifest.weather.weather.WeatherContract;
import dev.manifest.weather.weather.view.WeatherActivity;

@ActivityScope
@Module
public class WeatherPresenterModule {
    private final WeatherActivity view;

    public WeatherPresenterModule(WeatherActivity view) {
        this.view = view;
    }

    @Provides
    WeatherContract.View provideView() {
        return view;
    }
}

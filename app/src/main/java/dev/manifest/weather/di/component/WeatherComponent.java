package dev.manifest.weather.di.component;

import dagger.Component;
import dev.manifest.weather.di.annotation.ActivityScope;
import dev.manifest.weather.di.module.WeatherPresenterModule;
import dev.manifest.weather.weather.view.WeatherActivity;

@ActivityScope
@Component(dependencies = RepositoryComponent.class, modules = {WeatherPresenterModule.class})
public interface WeatherComponent {

    void injectInto(WeatherActivity weatherActivity);
}

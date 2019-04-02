package dev.manifest.weather;

import android.app.Application;

import dev.manifest.weather.di.component.DaggerRepositoryComponent;
import dev.manifest.weather.di.component.RepositoryComponent;
import dev.manifest.weather.di.module.AppModule;

public class App extends Application {

    private RepositoryComponent repositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        repositoryComponent = DaggerRepositoryComponent.builder()
                .appModule(new AppModule(this))
                .build();

        repositoryComponent.injectInto(this);
    }

    RepositoryComponent getRepositoryComponent() {
        return repositoryComponent;
    }
}

package dev.manifest.weather.data.repo;

import java.util.List;

import androidx.annotation.NonNull;
import dev.manifest.weather.data.model.City;
import dev.manifest.weather.di.annotation.Local;
import dev.manifest.weather.di.annotation.Remote;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherRepository implements WeatherDataSource {

    @NonNull
    private final WeatherDataSource localDataSource;
    @NonNull
    private final WeatherDataSource remoteDataSource;

    public WeatherRepository(@NonNull @Local WeatherDataSource localDataSource,
                             @NonNull @Remote WeatherDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Single<List<City>> updateWeatherInfo() {
        return localDataSource.getAddedCitiesWeather()
                .flatMapObservable(Observable::fromIterable)
                .filter(city -> !city.isCurrentLocationCity())
                .flatMap(city -> addCity(city.getName()).toObservable())
                .toList();
    }

    @Override
    public Maybe<List<City>> getAddedCitiesWeather() {
        return localDataSource.getAddedCitiesWeather();
    }

    @Override
    public Single<City> saveOrUpdateCity(City city) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<City> addCity(String cityName) {
        return remoteDataSource.addCity(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(localDataSource::saveOrUpdateCity);
    }

    @Override
    public Single<City> addCity(double lat, double lon) {
        return remoteDataSource.addCity(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(city -> {
                    city.setCurrentLocationCity(true);
                    return localDataSource.saveOrUpdateCity(city);
                });
    }

    @Override
    public Completable removeCityById(int id) {
        return localDataSource.removeCityById(id);
    }
}

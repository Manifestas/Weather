package dev.manifest.weather.data.repo;

import java.util.List;

import javax.inject.Inject;

import dev.manifest.weather.data.model.City;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.realm.Realm;

public class LocalWeatherDataSource implements WeatherDataSource {

    private Realm realm;

    @Inject
    public LocalWeatherDataSource(Realm realm) {
        this.realm = realm;
    }

    @Override
    public Single<List<City>> updateWeatherInfo() {
        return null;
    }

    @Override
    public Maybe<List<City>> getAddedCitiesWeather() {
        return null;
    }

    @Override
    public Single<City> saveOrUpdateCity(City city) {
        return null;
    }

    @Override
    public Single<City> addCity(String cityName) {
        return null;
    }

    @Override
    public Single<City> addCity(double lat, double lon) {
        return null;
    }

    @Override
    public Completable removeCityById(int id) {
        return null;
    }
}

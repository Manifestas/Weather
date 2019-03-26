package dev.manifest.weather.data.repo;

import java.util.List;

import dev.manifest.weather.data.model.City;
import dev.manifest.weather.network.WeatherApi;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class WeatherRemoteDataSource implements WeatherDataSource {

    private WeatherApi weatherApi;

    public WeatherRemoteDataSource(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
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

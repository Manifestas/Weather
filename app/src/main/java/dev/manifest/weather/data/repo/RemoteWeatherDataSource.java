package dev.manifest.weather.data.repo;

import java.util.List;

import dev.manifest.weather.data.model.City;
import dev.manifest.weather.network.WeatherApi;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class RemoteWeatherDataSource implements WeatherDataSource {

    private WeatherApi weatherApi;

    public RemoteWeatherDataSource(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    @Override
    public Single<List<City>> updateWeatherInfo() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Maybe<List<City>> getAddedCitiesWeather() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<City> saveOrUpdateCity(City city) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<City> addCity(String cityName) {
        return weatherApi.getWeather(cityName);
    }

    @Override
    public Single<City> addCity(double lat, double lon) {
        return weatherApi.getWeather(lat, lon);
    }

    @Override
    public Completable removeCityById(int id) {
        throw new UnsupportedOperationException();
    }
}

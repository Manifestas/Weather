package dev.manifest.weather.data.repo;

import java.util.List;

import dev.manifest.weather.data.model.City;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

interface WeatherDataSource {

    Single<List<City>> updateWeatherInfo();

    Maybe<List<City>> getAddedCitiesWeather();

    Single<City> saveOrUpdateCity(City city);

    Single<City> addCity(String cityName);

    Single<City> addCity(double lat, double lon);

    Completable removeCityById(int id);
}

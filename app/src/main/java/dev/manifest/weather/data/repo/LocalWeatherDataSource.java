package dev.manifest.weather.data.repo;

import java.util.List;

import javax.inject.Inject;

import dev.manifest.weather.data.model.City;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmResults;

public class LocalWeatherDataSource implements WeatherDataSource {

    private Realm realm;

    @Inject
    public LocalWeatherDataSource(Realm realm) {
        this.realm = realm;
    }

    @Override
    public Single<List<City>> updateWeatherInfo() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Maybe<List<City>> getAddedCitiesWeather() {
        RealmResults<City> cities = realm.where(City.class).findAll();
        return cities.isEmpty() ? Maybe.empty() : Maybe.just(realm.copyFromRealm(cities));
    }

    @Override
    public Single<City> saveOrUpdateCity(City city) {
        realm.executeTransaction(x -> {
            if (city.isCurrentLocationCity()) {
                City currentCity = realm.where(City.class)
                        .equalTo("currentLocationCity", true)
                        .findFirst();
                if (currentCity != null) {
                    currentCity.deleteFromRealm();
                }
            } else {
                City savedCity = realm.where(City.class)
                        .equalTo("id", city.getId())
                        .findFirst();
                if (savedCity != null) {
                    city.setCurrentLocationCity(savedCity.isCurrentLocationCity());
                }
            }
            realm.copyToRealmOrUpdate(city);
        });
        return Single.just(city);
    }

    @Override
    public Single<City> addCity(String cityName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<City> addCity(double lat, double lon) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Completable removeCityById(int id) {
        return Completable.fromAction(() -> realm.executeTransaction(x -> {
            City cityToRemove = realm.where(City.class)
                    .equalTo("id", id)
                    .findFirst();
            if (cityToRemove != null) {
                cityToRemove.deleteFromRealm();
            }
        }));
    }
}

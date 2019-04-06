package dev.manifest.weather.weather;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import dev.manifest.weather.data.model.City;
import dev.manifest.weather.data.repo.WeatherRepository;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class WeatherPresenter implements WeatherContract.Presenter {

    public static final String TAG = WeatherPresenter.class.getSimpleName();

    private CompositeDisposable subscriptions = new CompositeDisposable();
    private WeatherRepository repository;
    private WeatherContract.View view;

    @Inject
    public WeatherPresenter(WeatherRepository repository, WeatherContract.View view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void updateData(boolean refresh) {
        if (!refresh) {
            view.showProgress();
        }
        subscriptions.add(repository.updateWeatherInfo()
                .subscribe(x -> {
                            Log.d(TAG, "weather updated");
                            getDataFromDb(false);
                        },
                        e -> {
                            Log.d(TAG, "updateData: error + " + e);
                            getDataFromDb(true);
                            view.showNetworkErrorNotification();
                        }));
    }


    @Override
    public void addCityByCoordinates(double latitude, double longitude) {
        subscriptions.add(repository.addCity(latitude, longitude)
                .subscribe(city -> {
                            Log.d(TAG, "addCityByCoordinates: " + city + " added.");
                            getDataFromDb(false);
                        },
                        error -> {
                            Log.d(TAG, "addCityByCoordinates: " + error);
                            getDataFromDb(true);
                        }));
    }

    @Override
    public void addCityByName(String name) {
        view.showProgress();
        subscriptions.add(repository.addCity(name)
                .subscribe(city -> {
                    Log.d(TAG, "addCityByName: " + city + " added.");
                    getDataFromDb(false);
                }, error -> {
                    Log.d(TAG, "addCityByName: " + error);
                    getDataFromDb(true);
                    view.showNetworkErrorNotification();
                }));
    }

    @Override
    public void itemRemovedAtPosition(int swipedPosition) {
        subscriptions.add(getSortedListOfCities()
                .flatMapCompletable(cities -> {
                    if (!cities.isEmpty()) {
                        City cityToDelete = cities.get(swipedPosition);
                        if (cityToDelete.isCurrentLocationCity()) {
                            view.showCurrentCityDeletionErrorNotification();
                            return Completable.complete();
                        }
                        return repository.removeCityById(cityToDelete.getId());

                    }
                    return Completable.complete();
                })
                .subscribe(() -> {
                    Log.d(TAG, "itemRemovedAtPosition: " + swipedPosition);
                    getDataFromDb(false);
                }, e -> {
                    Log.e(TAG, "itemRemovedAtPosition: error - " + e);
                    getDataFromDb(true);
                    view.showNetworkErrorNotification();
                }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (subscriptions != null && !subscriptions.isDisposed()) {
            subscriptions.dispose();
        }
    }

    private void getDataFromDb(boolean isError) {
        subscriptions.add(getSortedListOfCities()
                .subscribe(cities -> {
                    if (cities.isEmpty()) {
                        if (isError) {
                            view.showError();
                        } else {
                            view.showEmptyState();
                        }
                    } else {
                        view.showData(cities);
                    }
                }, error -> Log.e(TAG, "getDataFromDb: ", error)));
    }

    private Single<List<City>> getSortedListOfCities() {
        return repository.getAddedCitiesWeather()
                .flatMapObservable(Observable::fromIterable)
                .toSortedList((first, second) -> {
                    if (first.isCurrentLocationCity()) {
                        return -1;
                    }
                    if (second.isCurrentLocationCity()) {
                        return 1;
                    }
                    return first.getName().compareTo(second.getName());
                });
    }
}

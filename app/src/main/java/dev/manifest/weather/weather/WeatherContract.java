package dev.manifest.weather.weather;

import java.util.List;

import dev.manifest.weather.common.BasePresenter;
import dev.manifest.weather.common.BaseView;
import dev.manifest.weather.data.model.City;

interface WeatherContract {

    interface View extends BaseView<Presenter> {

        void showProgress();

        void showData(List<City> cities);

        void showError();

        void hideItemAtPosition(int position);

        void showEmptyState();

        void showNetworkErrorNotification();

        void showCurrentCityDeletionErrorNotification();
    }

    interface Presenter extends BasePresenter {

        void updateData(boolean refresh);

        void addCityByCoordinates(double latitude, double longitude);

        void addCityByName(String name);

        void itemRemovedAtPosition(int swipedPosition);
    }
}

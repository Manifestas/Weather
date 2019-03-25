package dev.manifest.weather.network;

import dev.manifest.weather.data.model.City;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather")
    Single<City> getWeather(@Query("q") String cityName);

    @GET("weather")
    Single<City> getWeather(@Query("lat") double lat, @Query("lon") double lon);
}

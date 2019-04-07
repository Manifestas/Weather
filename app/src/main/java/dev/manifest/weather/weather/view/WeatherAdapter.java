package dev.manifest.weather.weather.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.manifest.weather.R;
import dev.manifest.weather.data.model.City;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<City> cities;

    @Inject
    public WeatherAdapter() {
        cities = new ArrayList<>();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.weather_item, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.bind(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setData(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        private final ImageView weatherPicture;
        private final TextView locationTextView;
        private final TextView temperatureTextView;
        private final TextView dateTextView;

        WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherPicture = itemView.findViewById(R.id.pic_image_view);
            locationTextView = itemView.findViewById(R.id.location_name_text_view);
            temperatureTextView = itemView.findViewById(R.id.temperature_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
        }

        void bind(City city) {
            locationTextView.setText(String.format("%s/%s", city.getName(), city.getSys().getCountry()));
            temperatureTextView.setText(String.format("%d°", ((int) city.getMain().getTemp().doubleValue())));
            Date date = new Date((long) city.getDt() * 1000);
            dateTextView.setText(String.format("%s %s", DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date),
                    DateFormat.getTimeInstance(DateFormat.SHORT).format(date)));
        }
    }
}

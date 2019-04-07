package dev.manifest.weather.weather.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import dev.manifest.weather.App;
import dev.manifest.weather.R;
import dev.manifest.weather.data.model.City;
import dev.manifest.weather.di.component.DaggerWeatherComponent;
import dev.manifest.weather.di.module.WeatherPresenterModule;
import dev.manifest.weather.weather.WeatherContract;
import dev.manifest.weather.weather.WeatherPresenter;

public class WeatherActivity extends AppCompatActivity implements WeatherContract.View {

    @Inject
    WeatherPresenter presenter;
    @Inject
    WeatherAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton addCityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerWeatherComponent.builder()
                .repositoryComponent(((App) getApplication()).getRepositoryComponent())
                .weatherPresenterModule(new WeatherPresenterModule(this))
                .build()
                .injectInto(this);

        setContentView(R.layout.activity_main);

        initViews();

        presenter.subscribe();
        presenter.updateData(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showData(List<City> cities) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.setData(cities);
    }

    @Override
    public void showError() {
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void hideItemAtPosition(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void showEmptyState() {
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showNetworkErrorNotification() {
        Snackbar.make(swipeRefreshLayout, R.string.network_error_text, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showCurrentCityDeletionErrorNotification() {
        Snackbar.make(swipeRefreshLayout, R.string.remove_current_city_error, Snackbar.LENGTH_LONG).show();
    }

    private void initViews() {
        initRecyclerView();

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::fetchData);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addCityButton = findViewById(R.id.fab);
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    if (addCityButton.isShown()) {
                        addCityButton.hide();
                    }
                } else if (dy < 0 && !addCityButton.isShown()) {
                    addCityButton.show();
                }
            }
        });
    }

    private void fetchData() {
        presenter.updateData(true);
    }

    public void addCityByName(String name) {
        presenter.addCityByName(name);
    }
}

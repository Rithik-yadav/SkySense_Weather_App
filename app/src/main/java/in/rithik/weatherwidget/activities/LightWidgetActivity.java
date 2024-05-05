package in.rithik.weatherwidget.activities;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.rithik.weatherwidget.R;
import in.rithik.weatherwidget.adapters.LightWidgetAdapter;
import in.rithik.weatherwidget.interfaces.AcquireWeatherData;
import in.rithik.weatherwidget.utils.Weather;
import in.rithik.weatherwidget.utils.WeatherItems;

public class LightWidgetActivity extends AppCompatActivity {

    private static int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recyclerview);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (Weather.getLatitude(this) == null || Weather.getLongitude(this) == null) {
            Intent initialize = new Intent(this, InitializeActivity.class);
            startActivity(initialize);
            finish();
        }

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        new AcquireWeatherData(Weather.getLatitude(this), Weather.getLongitude(this), this) {

            @Override
            public void successLister(List<WeatherItems> weatherItems) {
                mRecyclerView.setAdapter(new LightWidgetAdapter(weatherItems, mAppWidgetId, LightWidgetActivity.this));
            }
        }.acquire();
    }

}
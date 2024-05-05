package in.rithik.weatherwidget.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import in.rithik.weatherwidget.R;
import in.rithik.weatherwidget.fragments.WeatherFragment;
import in.rithik.weatherwidget.utils.Weather;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Weather.getLatitude(this) == null || Weather.getLongitude(this) == null) {
            Intent initialize = new Intent(this, InitializeActivity.class);
            startActivity(initialize);
            finish();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new WeatherFragment()).commit();
    }

}
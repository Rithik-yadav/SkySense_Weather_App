package in.rithik.weatherwidget.adapters;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import in.rithik.weatherwidget.R;
import in.rithik.weatherwidget.providers.WidgetProviderLight;
import in.rithik.weatherwidget.utils.Weather;
import in.rithik.weatherwidget.utils.WeatherItems;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on April 23, 2023
 */
public class LightWidgetAdapter extends RecyclerView.Adapter<LightWidgetAdapter.ViewHolder> {

    private final Activity mActivity;
    private final int mAppWidgetId;
    private final List<WeatherItems> mData;

    public LightWidgetAdapter(List<WeatherItems> data, int appWidgetId, Activity activity) {
        this.mData = data;
        this.mAppWidgetId = appWidgetId;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public LightWidgetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_lightwidget, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull LightWidgetAdapter.ViewHolder holder, int position) {
        if (this.mData.get(position).isSuccess()) {
            holder.mStatusIcon.setImageDrawable(this.mData.get(position).getWeatherIcon());
            holder.mTemperature.setText(this.mData.get(position).getTemperatureStatus());
            holder.mTemperature.setTextColor(this.mData.get(position).getAccentColor(holder.mTemperature.getContext()));
            holder.mTempUnit.setText(Weather.getTemperatureUnit(holder.mTempUnit.getContext()));
        } else {
            holder.mTemperature.setText(holder.mTemperature.getContext().getString(R.string.weather_status_failed));
            holder.mTemperature.setTextColor(ContextCompat.getColor(holder.mTemperature.getContext(), R.color.color_red));
            holder.mStatusIcon.setVisibility(View.GONE);
            holder.mTempUnit.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final AppCompatImageButton mStatusIcon;
        private final MaterialTextView mTemperature, mTempUnit;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.mStatusIcon = view.findViewById(R.id.weather_button);
            this.mTemperature = view.findViewById(R.id.temperature_status);
            this.mTempUnit = view.findViewById(R.id.temperature_unit);
        }

        @Override
        public void onClick(View view) {
            if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                return;
            }

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mActivity);
            WidgetProviderLight.update(appWidgetManager, mAppWidgetId, mActivity);
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            mActivity.setResult(Activity.RESULT_OK, resultValue);
            mActivity.finish();
        }
    }

}
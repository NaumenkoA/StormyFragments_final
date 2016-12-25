package teamtreehouse.com.stormy.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.weather.Day;

public class DailyDetailedFragment extends Fragment {

    @InjectView(R.id.iconForecastView) ImageView iconImageView;
    @InjectView(R.id.dayofWeekLabel) TextView dayLabelTextView;
    @InjectView(R.id.degreeValue) TextView temperatureLabelTextView;
    @InjectView(R.id.sunsetTimeTextView)  TextView sunsetTimeTextView;
    @InjectView(R.id.sunriseTimeTextView) TextView sunriseTimeTextView;
    @InjectView(R.id.moonPhaseTextView) TextView moonTextView;
    @InjectView(R.id.cloudCoverTextView)  TextView cloudTextView;
    @InjectView(R.id.humidityTextView) TextView humidityTextView;
    @InjectView(R.id.precipChanceTextView) TextView precipChanceTextView;
    @InjectView(R.id.pressureTextView) TextView pressureTextView;
    @InjectView(R.id.windSpeedTextView) TextView windSpeedTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_details, container, false);
        Bundle bundle = getArguments();
        Parcelable[] parcelables = bundle.getParcelableArray(MainActivity.DAILY_FORECAST);
        Day[] days = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        int index = bundle.getInt(DailyDualPaneFragment.DAY_INDEX);
        ButterKnife.inject(this, view);
        Day day = days [index];

        iconImageView.setImageResource(day.getIconId());
        if (index == 0) {
            dayLabelTextView.setText("Today");
        } else {
            dayLabelTextView.setText(day.getDayOfTheWeek());
        }
        temperatureLabelTextView.setText(day.getTemperatureMax()+"");
        windSpeedTextView.setText (day.getWindSpeed() + "");
        pressureTextView.setText(day.getPressure());
        humidityTextView.setText(day.getHumidity() + "");
        precipChanceTextView.setText(day.getPrecipChance()+"%");
        cloudTextView.setText (day.getCloudCover() + "%");
        moonTextView.setText(day.getMoonPhase()+"");
        sunriseTimeTextView.setText(day.getSunriseTime());
        sunsetTimeTextView.setText(day.getSunsetTime());
        return view;
    }
   }

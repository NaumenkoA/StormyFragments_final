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
import teamtreehouse.com.stormy.weather.Hour;

public class HourlyDetailedFragment extends Fragment {
    @InjectView(R.id.iconImageView) ImageView iconImageView;
    @InjectView(R.id.timeLabel) TextView timeLabelTextView;
    @InjectView(R.id.degreeValue) TextView temperatureLabelTextView;
    @InjectView(R.id.humidityTextView) TextView humidityTextView;
    @InjectView(R.id.precipChanceTextView) TextView precipChanceTextView;
    @InjectView(R.id.pressureTextView) TextView pressureTextView;
    @InjectView(R.id.windSpeedTextView) TextView windSpeedTextView;
    @InjectView(R.id.cloudCoverTextView) TextView cloudTextView;
    @InjectView(R.id.visibilityTextView) TextView visibilityTextView;
    private int mIndex;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hourly_details, container, false);
        ButterKnife.inject(this, view);
        Bundle bundle = getArguments();
        Hour hour = bundle.getParcelable(HourlyDualPaneFragment.HOUR_DETAILED);

        iconImageView.setImageResource(hour.getIconId());
        timeLabelTextView.setText(hour.getHour());
        temperatureLabelTextView.setText(hour.getTemperature()+"");
        windSpeedTextView.setText (hour.getWindSpeed() + "");
        pressureTextView.setText(hour.getPressure() + "");
        humidityTextView.setText(hour.getHumidity() + "");
        precipChanceTextView.setText(hour.getPrecipChance()+"%");
        cloudTextView.setText (hour.getCloudCover() + "%");
        visibilityTextView.setText (hour.getVisibility() + " ml.");
        return view;
    }
}
//
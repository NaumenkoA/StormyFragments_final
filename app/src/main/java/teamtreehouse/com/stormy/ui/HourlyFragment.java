package teamtreehouse.com.stormy.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.adapters.HourAdapter;
import teamtreehouse.com.stormy.weather.Hour;

public class HourlyFragment extends BaseHourlyFragment {

    public interface onHourlyForecastSelectedInterface {
        void onHourlyForecastSelected(int index);
    }

    @Override
    public RecyclerView.Adapter setHourAdapter() {
        onHourlyForecastSelectedInterface listener = (onHourlyForecastSelectedInterface) getActivity();
        return new HourAdapter(getHours(), listener);
    }
}



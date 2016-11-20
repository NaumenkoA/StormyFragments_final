package teamtreehouse.com.stormy.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;

import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.adapters.DayAdapter;
import teamtreehouse.com.stormy.weather.Day;

public class DailyFragment extends Fragment {
private Day [] mDays;
    public interface onDailyForecastSelectedInterface {
        void onDailyForecastSelected (int index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        onDailyForecastSelectedInterface listener = (onDailyForecastSelectedInterface) getActivity();
        View view = inflater.inflate(R.layout.fragment_daily_forecast, container, false);
        ListView listView = (ListView) view.findViewById(R.id.dailyListView);
        Bundle bundle = getArguments();
        Parcelable[] parcelables = bundle.getParcelableArray(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter dayAdapter = new DayAdapter (getActivity(), mDays, listener);
        listView.setAdapter(dayAdapter);
        listView.setEmptyView(view.findViewById(R.id.emptyTextView));
        return view;
    }
}

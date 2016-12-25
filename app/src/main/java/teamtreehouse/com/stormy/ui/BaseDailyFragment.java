package teamtreehouse.com.stormy.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.adapters.DayAdapter;
import teamtreehouse.com.stormy.weather.Day;

public abstract class BaseDailyFragment extends Fragment implements AdapterView.OnItemClickListener {
private Day [] mDays;

    @Override
    public abstract void onItemClick(AdapterView<?> adapterView, View view, int i, long l);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_forecast, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setEmptyView(view.findViewById(R.id.emptyTextView));
        Bundle bundle = getArguments();
        boolean isCold = bundle.getBoolean(MainActivity.IS_COLD);
        Parcelable[] parcelables =  bundle.getParcelableArray(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter adapter = new DayAdapter (mDays, isCold);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }
    public Day [] getDays () {
        return mDays;
    }
}

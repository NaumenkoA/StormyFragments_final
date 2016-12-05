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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.adapters.DayAdapter;
import teamtreehouse.com.stormy.adapters.HourAdapter;
import teamtreehouse.com.stormy.weather.Day;
import teamtreehouse.com.stormy.weather.Hour;

import static teamtreehouse.com.stormy.R.id.recyclerView;

public class DailyFragment extends Fragment implements AdapterView.OnItemClickListener {
private Day [] mDays;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String dayOfTheWeek = mDays[i].getDayOfTheWeek();
        String conditions = mDays[i].getSummary();
        String highTemp = mDays[i].getTemperatureMax() + "";
        String message = String.format("On %s the high will be %s and it will be %s",
                dayOfTheWeek,
                highTemp,
                conditions);
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_forecast, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setEmptyView(view.findViewById(R.id.emptyTextView));
        Bundle bundle = getArguments();
        Parcelable[] parcelables =  bundle.getParcelableArray(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter adapter = new DayAdapter (mDays);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

}

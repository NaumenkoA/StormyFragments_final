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

public class DailyFragment extends BaseDailyFragment {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Day [] days = getDays();
        String dayOfTheWeek = days[i].getDayOfTheWeek();
        String conditions =days[i].getSummary();
        String highTemp = days[i].getTemperatureMax() + "";
        String message = String.format("On %s the high will be %s and it will be %s",
                dayOfTheWeek,
                highTemp,
                conditions);
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}

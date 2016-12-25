package teamtreehouse.com.stormy.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class DailyFragmentTablet extends BaseDailyFragment {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        onDailyForecastSelectedInterface listener = (onDailyForecastSelectedInterface) getParentFragment();
        listener.onDailyForecastSelected(i);
         }

    public interface onDailyForecastSelectedInterface {
            void onDailyForecastSelected (int index);
                }

}

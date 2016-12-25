package teamtreehouse.com.stormy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.ui.HourlyFragment;
import teamtreehouse.com.stormy.ui.HourlyFragmentTablet;
import teamtreehouse.com.stormy.weather.Hour;

public class HourTabletAdapter extends BaseHourAdapter {
    private Hour[] mHours;
    private HourlyFragmentTablet.onHourlyTabletForecastSelectedInterface mListener;

    public HourTabletAdapter(Hour[] hours, HourlyFragmentTablet.onHourlyTabletForecastSelectedInterface listener) {
        mHours = hours;
        mListener = listener;
    }


    @Override
    public void doOnItemClick(int position) {
        mListener.onHourlyForecastSelected(position);
    }

    @Override
    public Hour[] setHours() {
        return mHours;
    }
}


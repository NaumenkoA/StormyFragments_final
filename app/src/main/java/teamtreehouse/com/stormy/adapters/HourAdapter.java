package teamtreehouse.com.stormy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.ui.HourlyFragment;
import teamtreehouse.com.stormy.weather.Hour;

public class HourAdapter extends BaseHourAdapter {

    private Hour[] mHours;
    private HourlyFragment.onHourlyForecastSelectedInterface mListener;

    public HourAdapter(Hour[] hours, HourlyFragment.onHourlyForecastSelectedInterface listener) {
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











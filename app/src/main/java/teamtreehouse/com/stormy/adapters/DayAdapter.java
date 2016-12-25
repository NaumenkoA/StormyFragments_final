package teamtreehouse.com.stormy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.ui.DailyFragmentTablet;
import teamtreehouse.com.stormy.weather.Day;

public class DayAdapter extends BaseAdapter  {

    private Day[] mDays;
    private boolean mIsCold;



    public DayAdapter(Day[] days, boolean isCold) {

        mDays = days;
        mIsCold = isCold;
    }

    public DayAdapter(Day[] days,
                      DailyFragmentTablet.onDailyForecastSelectedInterface listener,
                      boolean isCold) {
        mDays = days;
        mIsCold = isCold;
         }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0; // we aren't going to use this. Tag items for easy reference
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // brand new
            convertView = LayoutInflater.from (parent.getContext()).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Day day = mDays[position];

        holder.iconImageView.setImageResource(day.getIconId());
        if (mIsCold) {
            holder.temperatureLabel.setTextColor(Color.parseColor("#3c5aee"));
        }
        holder.temperatureLabel.setText(day.getTemperatureMax() + "");

        if (position == 0) {
            holder.dayLabel.setText("Today");
        }
        else {
            holder.dayLabel.setText(day.getDayOfTheWeek());
        }

        return convertView;
    }


    private static class ViewHolder {
        ImageView iconImageView; // public by default
        TextView temperatureLabel;
        TextView dayLabel;
    }

}










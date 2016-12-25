package teamtreehouse.com.stormy.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Arrays;

import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.weather.Day;

public class DailyDualPaneFragment extends Fragment implements DailyFragmentTablet.onDailyForecastSelectedInterface {
    public static final String DAY_INDEX = "DAY_INDEX";
    public Day [] mDays;
    public static final String DAILY_FRAGMENT = "DAILY_FRAGMENT";
    public static final String DAILY_DETAILED_FRAGMENT = "DAILY_DETAILED_FRAGMENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_dualpane, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        Bundle bundle = getArguments();
        Parcelable[] parcelables = bundle.getParcelableArray(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DailyFragmentTablet savedFragment = (DailyFragmentTablet) fragmentManager.findFragmentByTag(DAILY_FRAGMENT);

        if (savedFragment == null) {
            final DailyFragmentTablet dailyFragment = new DailyFragmentTablet();
            bundle.putParcelableArray(MainActivity.DAILY_FORECAST, parcelables);
            dailyFragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.leftPlaceholder, dailyFragment, DAILY_FRAGMENT).commit();
        }

        DailyDetailedFragment savedDetailedFragment = (DailyDetailedFragment) fragmentManager.findFragmentByTag(DAILY_DETAILED_FRAGMENT);
        if (savedDetailedFragment == null) {
            DailyDetailedFragment detailedFragment = new DailyDetailedFragment();
            bundle.putInt(DAY_INDEX, 0);
            detailedFragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.rightPlaceholder, detailedFragment, DAILY_DETAILED_FRAGMENT).commit();
        }
        return view;
    }

    @Override
    public void onDailyForecastSelected(int index) {
        FragmentManager fragmentManager = getChildFragmentManager();
        DailyDetailedFragment detailedFragment = new DailyDetailedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(MainActivity.DAILY_FORECAST, mDays);
        bundle.putInt(DAY_INDEX, index);
        detailedFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.rightPlaceholder, detailedFragment, DAILY_DETAILED_FRAGMENT).commit();
    }
}

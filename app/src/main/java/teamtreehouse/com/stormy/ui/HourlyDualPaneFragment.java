package teamtreehouse.com.stormy.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.weather.Day;
import teamtreehouse.com.stormy.weather.Hour;

public class HourlyDualPaneFragment extends Fragment
        implements HourlyFragmentTablet.onHourlyTabletForecastSelectedInterface {

    public static final String HOUR_DETAILED = "HOUR_DETAILED";
    private static final String HOURLY_FRAGMENT = "HOURLY_FRAGMENT";
    private static final String HOURLY_DETAILED_FRAGMENT = "DETAILED_FRAGMENT";
    private Hour[] mHours;

    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            FragmentManager fragmentManager = getChildFragmentManager();
            Bundle bundle = getArguments();
            Parcelable[] parcelables = bundle.getParcelableArray(MainActivity.HOURLY_FORECAST);
            mHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);
            View view = inflater.inflate(R.layout.fragment_dualpane, container, false);
            HourlyFragmentTablet savedFragment = (HourlyFragmentTablet) fragmentManager.findFragmentByTag(HOURLY_FRAGMENT);

            if (savedFragment == null) {
                final HourlyFragmentTablet hourlyFragment = new HourlyFragmentTablet();
                bundle.putParcelableArray(MainActivity.HOURLY_FORECAST, mHours);
                hourlyFragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.leftPlaceholder, hourlyFragment, HOURLY_FRAGMENT).commit();
            }

                HourlyDetailedFragment savedDetailedFragment = (HourlyDetailedFragment) fragmentManager.findFragmentByTag(HOURLY_DETAILED_FRAGMENT);
             if (savedDetailedFragment == null) {
                HourlyDetailedFragment detailedFragment = new HourlyDetailedFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable(HOUR_DETAILED, mHours[0]);
                detailedFragment.setArguments(bundle1);
                fragmentManager.beginTransaction().add(R.id.rightPlaceholder, detailedFragment, HOURLY_DETAILED_FRAGMENT).commit();

            }
        return view;
        }


    @Override
    public void onHourlyForecastSelected(int index) {
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragmentToRemove = fragmentManager.findFragmentByTag(HOURLY_DETAILED_FRAGMENT);
        if (fragmentToRemove != null)
        {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragmentToRemove);
        }
        final HourlyDetailedFragment detailedFragment = new HourlyDetailedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(HOUR_DETAILED, mHours [index]);
        detailedFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.rightPlaceholder, detailedFragment, HOURLY_DETAILED_FRAGMENT).commit();
    }
}


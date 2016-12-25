package teamtreehouse.com.stormy.ui;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.weather.Current;
import teamtreehouse.com.stormy.weather.Day;
import teamtreehouse.com.stormy.weather.Forecast;
import teamtreehouse.com.stormy.weather.Hour;


public class MainActivity extends AppCompatActivity
        implements HourlyFragment.onHourlyForecastSelectedInterface {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "daily_forecast";
    public static final String HOURLY_FORECAST = "hourly_forecast";
    private static final String DAILY_FRAGMENT = "daily_fragment" ;
    private static final String HOURLY_FRAGMENT = "hourly_fragment";
    private static final String FRAGMENT_TAG = "fragment_tag" ;
    public static final String IS_COLD = "is_it_cold";

    private boolean mIsTablet;
    private Forecast mForecast;
    private boolean mIsCold;

    @InjectView(R.id.timeLabel)
    TextView mTimeLabel;
    @InjectView(R.id.temperatureLabel)
    TextView mTemperatureLabel;
    @InjectView(R.id.humidityValue)
    TextView mHumidityValue;
    @InjectView(R.id.precipValue)
    TextView mPrecipValue;
    @InjectView(R.id.summaryLabel)
    TextView mSummaryLabel;
    @InjectView(R.id.iconImageView)
    ImageView mIconImageView;
    @InjectView(R.id.refreshImageView)
    ImageView mRefreshImageView;
    @InjectView(R.id.progressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.mainLayout)
    RelativeLayout mRelativeLayout;
    @InjectView(R.id.placeholder)
    FrameLayout mPlaceholder;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Fragment fragment = getVisibleFragment();
        String fragmentTag;
        if (fragment == null) {
            fragmentTag = null;
        } else {
            fragmentTag = fragment.getTag();
        }
            outState.putString(FRAGMENT_TAG, fragmentTag);
            super.onSaveInstanceState(outState);
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mIsTablet = getResources().getBoolean(R.bool.is_tablet);

        mProgressBar.setVisibility(View.INVISIBLE);

        final double latitude = 37.8267;
        final double longitude = -122.423;

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longitude, null);
            }
        });
       getForecast(latitude, longitude, savedInstanceState);

        }



    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }


    private void getForecast(double latitude, double longitude, final Bundle bundle) {
        String apiKey = "7443e9aa512ca789f767aeec261b2b5c";
        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                "/" + latitude + "," + longitude;
        Log.v ("API_request: ", forecastUrl);
        if (isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay(bundle);
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
        else {
            Toast.makeText(this, getString(R.string.network_unavailable_message),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay(Bundle bundle) {
        Current current = mForecast.getCurrent();

        mTemperatureLabel.setText(current.getTemperature() + "");
        mTimeLabel.setText("At " + current.getFormattedTime() + " it will be");
        mHumidityValue.setText(current.getHumidity() + "");
        mPrecipValue.setText(current.getPrecipChance() + "%");
        mSummaryLabel.setText(current.getSummary());

        // Depreciated method getDrawable () Replaced
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), current.getIconId(), null);
        mIconImageView.setImageDrawable(drawable);
        setActivityBackground(current);

        if (bundle != null &&
                bundle.getString(FRAGMENT_TAG) != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            String fragmentTag = bundle.getString(FRAGMENT_TAG);
            Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.show(fragment);
            mPlaceholder.setVisibility(View.VISIBLE);
            transaction.commit();
        }
    }

    private void setActivityBackground(Current current) {
        Drawable drawable;
        if (current.getTemperature() < 41) {
            mIsCold = true;
        } else {
            mIsCold = false;
        }
        if (mIsCold) {
            drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_gradient_cold, null);
             } else {
            drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_gradient_warm, null);
            }
        mRelativeLayout.setBackground(drawable);
        mPlaceholder.setBackground(drawable);
        mPlaceholder.setVisibility(View.INVISIBLE);
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();

        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));

        return forecast;
    }

    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);
            day.setHumidity(jsonDay.getDouble("humidity"));
            day.setCloudCover(jsonDay.getDouble("cloudCover"));
            day.setWindSpeed(jsonDay.getDouble("windSpeed"));
            day.setPressure(jsonDay.getDouble("pressure"));
            day.setPrecipChance(jsonDay.getDouble("precipProbability"));
            day.setMoonPhase(jsonDay.getDouble("moonPhase"));
            day.setSunriseTime(jsonDay.getLong("sunriseTime"));
            day.setSunsetTime(jsonDay.getLong("sunsetTime"));

            days[i] = day;
        }

        return days;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);
            hour.setHumidity(jsonHour.getDouble("humidity"));
            hour.setCloudCover(jsonHour.getDouble("cloudCover"));
            hour.setVisibility(jsonHour.getDouble("visibility"));
            hour.setWindSpeed(jsonHour.getDouble("windSpeed"));
            hour.setPressure(jsonHour.getDouble("pressure"));
            hour.setPrecipChance(jsonHour.getDouble("precipProbability"));

            hours[i] = hour;
        }

        return hours;
    }


    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);

        Log.d(TAG, current.getFormattedTime());

        return current;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment activeFragment = fragmentManager.findFragmentByTag(DAILY_FRAGMENT);
        if (activeFragment == null){
            activeFragment = fragmentManager.findFragmentByTag(HOURLY_FRAGMENT);
        }
        if (activeFragment == null) {
            finish();
            return;
        }
        mPlaceholder.setVisibility(View.INVISIBLE);
        removeFragmentByTag(HOURLY_FRAGMENT);
        removeFragmentByTag(DAILY_FRAGMENT);
    }

    @OnClick (R.id.dailyButton)
    public void showDailyFragment(View view) {
        Fragment fragment;
        fragment = getSupportFragmentManager().findFragmentByTag(DAILY_FRAGMENT);
        removeFragmentByTag(HOURLY_FRAGMENT);
        if (fragment == null) {
            if (mIsTablet) {
                fragment = new DailyDualPaneFragment();
            } else {
                fragment = new DailyFragment();
            }
        }

            Bundle bundle = new Bundle();
            Day[] days = mForecast.getDailyForecast();
            bundle.putParcelableArray(DAILY_FORECAST, days);
            bundle.putBoolean(IS_COLD, mIsCold);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeholder, fragment, DAILY_FRAGMENT);
            mPlaceholder.setVisibility(View.VISIBLE);
            fragmentTransaction.commit();
            }

    private void removeFragmentByTag(String tag) {
        Fragment hourlyFragment = getSupportFragmentManager().findFragmentByTag (tag);
        if (hourlyFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(hourlyFragment).commit();
        }
    }

    @OnClick (R.id.hourlyButton)
    public void showHourlyFragment(View view) {
        Fragment fragment;
        fragment = getSupportFragmentManager().findFragmentByTag(HOURLY_FRAGMENT);
        removeFragmentByTag(DAILY_FRAGMENT);
        if (fragment == null) {
            if (mIsTablet) {
                fragment = new HourlyDualPaneFragment();
            } else {
                fragment = new HourlyFragment();
            }
            Bundle bundle = new Bundle();
            Hour[] hours = mForecast.getHourlyForecast();
            bundle.putParcelableArray(HOURLY_FORECAST, hours);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeholder, fragment, HOURLY_FRAGMENT);
            mPlaceholder.setVisibility(View.VISIBLE);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onHourlyForecastSelected(int index) {
        if (!mIsTablet) {
            Hour[] hours = mForecast.getHourlyForecast();
            String time = hours[index].getHour();
            String temperature = hours[index].getTemperature() + "";
            String summary = hours[index].getSummary();
            String message = String.format("At %s it will be %s and %s",
                    time,
                    temperature,
                    summary);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}















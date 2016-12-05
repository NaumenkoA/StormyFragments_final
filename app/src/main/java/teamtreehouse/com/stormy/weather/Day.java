package teamtreehouse.com.stormy.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by benjakuben on 2/5/15.
 */
public class Day implements Parcelable {
    private long mTime;
    private String mSummary;
    private double mTemperatureMax;
    private String mIcon;
    private String mTimezone;
    private double mHumidity;
    private long mSunriseTime;
    private long mSunsetTime;
    private double mCloudCover;
    private double mMoonPhase;
    private double mWindSpeed;
    private double mPressure;
    private double mPrecipChance;

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTemperatureMax() {
        return (int)Math.round(mTemperatureMax);
    }

    public void setTemperatureMax(double temperatureMax) {
        mTemperatureMax = temperatureMax;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public String getSunriseTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:m a");
        Date date = new Date(mSunriseTime * 1000);
        return formatter.format(date);

    }

    public void setSunriseTime(long sunriseTime) {
        mSunriseTime = sunriseTime;
    }

    public String getSunsetTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:m a");
        Date date = new Date(mSunsetTime * 1000);
        return formatter.format(date);
    }

    public void setSunsetTime(long sunsetTime) {
        mSunsetTime = sunsetTime;
    }

    public double getCloudCover() {
        return mCloudCover;
    }

    public void setCloudCover(double cloudCover) {
        mCloudCover = cloudCover;
    }

    public double getMoonPhase() {
        return mMoonPhase;
    }

    public void setMoonPhase(double moonPhase) {
        mMoonPhase = moonPhase;
    }

    public double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        mWindSpeed = windSpeed;
    }

    public String getPressure() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(mPressure);
    }

    public void setPressure(double pressure) {
        mPressure = pressure;
    }

    public double getPrecipChance() {
        return mPrecipChance;
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public int getIconId() {
        return Forecast.getIconId(mIcon);
    }

    public String getDayOfTheWeek() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));
        Date dateTime = new Date(mTime * 1000);
        return formatter.format(dateTime);
    }

    public Day() { }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mTime);
        dest.writeString(this.mSummary);
        dest.writeDouble(this.mTemperatureMax);
        dest.writeString(this.mIcon);
        dest.writeString(this.mTimezone);
        dest.writeDouble(this.mHumidity);
        dest.writeLong(this.mSunriseTime);
        dest.writeLong(this.mSunsetTime);
        dest.writeDouble(this.mCloudCover);
        dest.writeDouble(this.mMoonPhase);
        dest.writeDouble(this.mWindSpeed);
        dest.writeDouble(this.mPressure);
        dest.writeDouble(this.mPrecipChance);
    }

    protected Day(Parcel in) {
        this.mTime = in.readLong();
        this.mSummary = in.readString();
        this.mTemperatureMax = in.readDouble();
        this.mIcon = in.readString();
        this.mTimezone = in.readString();
        this.mHumidity = in.readDouble();
        this.mSunriseTime = in.readLong();
        this.mSunsetTime = in.readLong();
        this.mCloudCover = in.readDouble();
        this.mMoonPhase = in.readDouble();
        this.mWindSpeed = in.readDouble();
        this.mPressure = in.readDouble();
        this.mPrecipChance = in.readDouble();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}












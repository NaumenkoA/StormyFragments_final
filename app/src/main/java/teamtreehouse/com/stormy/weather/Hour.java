package teamtreehouse.com.stormy.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by benjakuben on 2/5/15.
 */
public class Hour implements Parcelable {
    private long mTime;
    private String mSummary;
    private double mTemperature;
    private String mIcon;
    private String mTimezone;
    private double mHumidity;
    private double mCloudCover;
    private double mVisibility;
    private double mWindSpeed;
    private double mPressure;
    private double mPrecipChance;

    public double getPrecipChance() {
        return mPrecipChance;
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getCloudCover() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(mCloudCover*100);
    }

    public void setCloudCover(double cloudCover) {
        mCloudCover = cloudCover;
    }

    public double getVisibility() {
        return mVisibility;
    }

    public void setVisibility(double visibility) {
        mVisibility = visibility;
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

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }




    public Hour() { }

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

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public String getIcon() {
        return mIcon;
    }

    public int getIconId() {
        return Forecast.getIconId(mIcon);
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

    public String getHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("h a");
        Date date = new Date(mTime * 1000);
        return formatter.format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mTime);
        dest.writeString(this.mSummary);
        dest.writeDouble(this.mTemperature);
        dest.writeString(this.mIcon);
        dest.writeString(this.mTimezone);
        dest.writeDouble(this.mHumidity);
        dest.writeDouble(this.mCloudCover);
        dest.writeDouble(this.mVisibility);
        dest.writeDouble(this.mWindSpeed);
        dest.writeDouble(this.mPressure);
        dest.writeDouble(this.mPrecipChance);
    }

    protected Hour(Parcel in) {
        this.mTime = in.readLong();
        this.mSummary = in.readString();
        this.mTemperature = in.readDouble();
        this.mIcon = in.readString();
        this.mTimezone = in.readString();
        this.mHumidity = in.readDouble();
        this.mCloudCover = in.readDouble();
        this.mVisibility = in.readDouble();
        this.mWindSpeed = in.readDouble();
        this.mPressure = in.readDouble();
        this.mPrecipChance = in.readDouble();
    }

    public static final Parcelable.Creator <Hour> CREATOR = new Parcelable.Creator <Hour>() {
        @Override
        public Hour createFromParcel(Parcel source) {
            return new Hour(source);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };
}














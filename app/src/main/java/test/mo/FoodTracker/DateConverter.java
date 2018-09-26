package test.mo.FoodTracker;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

 //

public class DateConverter {
    private static final String TAG = "DateConverter";
    private SimpleDateFormat simpleDateFormat;
    private Date currentDate;

    public DateConverter(SimpleDateFormat simpleDateFormat){
        this.simpleDateFormat = simpleDateFormat;
        currentDate = new Date(); // setups to todays date.
    }


    public long convertToLong(String string_date){
        Long to_return = 0L;
        try{
            to_return = this.simpleDateFormat.parse(string_date).getTime();
        } catch (ParseException pe){
            Log.e(TAG, "convertToLong: missing date / wrong simpledateformart");
            pe.printStackTrace();
        } finally {
            return to_return;
        }
    }

    public String convertToStringDate(long dateInLong){
        return simpleDateFormat.format(new Date(dateInLong));
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }


    public String getExpiryString(long d){
        if(isExpired(d)){
            return "Expired!!";
        } else if (isWeekLeft(d)){
            return getWeekStrings(getDaysTo(d));
        } else if (isSameYear(d)){
            return getSameYearString(d);
        } else {
            return getMonthYearString(d);
        }
    }

    public String getAddedString(long dateInLong){
        // TODO: Display Today Yesterday or days if same month
        // TODO: else display  day + month
        switch (getDaysTo(dateInLong)){
            case  0: return "Today!";
            case -1: return "Yesterday";
            default: return Math.abs(getDaysTo(dateInLong)) + " days ago";
        }
    }

    private int getDaysTo(long date){
        // cast since long doesn't work in switch statement
        return (int) (TimeUnit.MILLISECONDS.toDays(date) - TimeUnit.MILLISECONDS.toDays(currentDate.getTime()));
    }

    private boolean isWeekLeft(long dateInLong){
        Long diff = TimeUnit.MILLISECONDS.toDays(dateInLong) - TimeUnit.MILLISECONDS.toDays(currentDate.getTime());
        return diff >= 0&& diff <= 7 ;
    }

    private boolean isSameYear(long d){
        Calendar current = Calendar.getInstance();
        Calendar compare = Calendar.getInstance();
        current.setTime(currentDate);
        compare.setTime(new Date(d));
        return current.get(Calendar.YEAR) == compare.get(Calendar.YEAR);

    }

    private boolean isExpired(long d){
        return getDaysTo(d) < 0;
    }

    private String getWeekStrings(int days){
        switch (days){
            case 0: return "Today";
            case 1: return "Tomorrow";
            default:return days + " days";
        }
    }

    private String getSameYearString(long d){
        // TODO: add suffixes for the days 2nd,rd,th,st
        return new SimpleDateFormat("dd MMMM").format(new Date(d));
    }

    private String getMonthYearString(long d){
        return new SimpleDateFormat("MMMM YYYY").format(new Date(d));
    }


}

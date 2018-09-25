package test.mo.FoodTracker;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private static final String TAG = "DateConverter";
    private SimpleDateFormat simpleDateFormat;

    public DateConverter(SimpleDateFormat simpleDateFormat){
        this.simpleDateFormat = simpleDateFormat;
    }


    public Long convertToLong(String string_date){
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

    public String convertToStringDate(Long dateInLong){
        return simpleDateFormat.format(new Date(dateInLong));
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }
}

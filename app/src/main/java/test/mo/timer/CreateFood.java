package test.mo.timer;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateFood extends AppCompatActivity {

    private static final String TAG = "CreateFood";

    private EditText foodName;
    private Date expiryDate;
    private CalendarView calendarView;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food);

        // initialises components
        initC();

        if(saveButton != null){
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: save the expiryDate and foodName to database.
                    // TODO: once saved close current activity and reopen main DB.



                }
            });
        }



        if(calendarView != null){
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    Calendar c = Calendar.getInstance();
                    c.set(year,month,dayOfMonth);
                    expiryDate = c.getTime();


                }
            });
        }

    }


    void initC(){
        foodName = (EditText) findViewById(R.id.food_name);
        calendarView = (CalendarView) findViewById(R.id.food_date);
        saveButton = findViewById(R.id.save);

        // default expiry date is current Date;
        expiryDate = new Date(calendarView.getDate());

        Toast.makeText(this, "default date: " + expiryDate, Toast.LENGTH_SHORT).show();



//        testDate = calendarView.getDate();

//        Date second = new Date(calendarView.getDate());
//
//
//        // default value for expiry date is current date:
//        Date date = Calendar.getInstance().getTime();
//
//        // format date
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
//
//        expiryDate = simpleDateFormat.format(date);


    }
}

package test.mo.FoodTracker;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateFood extends AppCompatActivity {

    private static final String TAG = "CreateFood";

    private EditText foodName;
    private Date currentDate;
    private Date expiryDate;
    private CalendarView calendarView;
    private Button saveButton;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food);

        // initialises components
        initComponents();

    }


    void initComponents(){
        foodName = (EditText) findViewById(R.id.food_name);
        calendarView = (CalendarView) findViewById(R.id.food_date);
        saveButton = findViewById(R.id.save);
        currentDate = Calendar.getInstance().getTime();

        // default expiry date is current Date until user selects a different day.
        expiryDate = currentDate;

        // setup saveButton Listener
        setButtonListener(saveButton);

        // setup calender listener;
        setCalenderListener(calendarView);

    }

    void setButtonListener(@NonNull Button button){
        final foodDatabase db = Room.databaseBuilder(getApplicationContext(),foodDatabase.class,"foodDatabase")
                .allowMainThreadQueries()
                .build();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get CurrentDate;
                // get ExpiryDate
                // Get foodName;

                String simplifiedStart = simpleDateFormat.format(currentDate);
                String simplifiedEnd = simpleDateFormat.format(expiryDate);
                String name = foodName.getText().toString();


                // check if name is empty and reject if it is
                if(name.isEmpty()){
                    Toast.makeText(CreateFood.this, "Please Enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // at this point all values are non null so can safely add
                db.foodDao().insertAll(new Food(name,simplifiedStart,simplifiedEnd));
                startActivity(new Intent(CreateFood.this,MainActivity.class));

            }
        });
    }


    void setCalenderListener(@NonNull CalendarView calendarView){
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

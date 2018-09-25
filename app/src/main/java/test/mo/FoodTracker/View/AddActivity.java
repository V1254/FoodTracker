package test.mo.FoodTracker.View;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import test.mo.FoodTracker.Model.Food;
import test.mo.FoodTracker.R;
import test.mo.FoodTracker.ViewModel.AddFoodViewModel;

public class AddActivity extends AppCompatActivity {

    EditText editText;
    CalendarView calendarView;
    FloatingActionButton floatingActionButton;
    AddFoodViewModel addFoodViewModel;

    Long todaysDate;
    Long expiryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // initialise fields/components
        initComponents();

        // date change listener on the calender view.
        setListener(calendarView);

        // listener to save to the database;
        setListener(floatingActionButton);
    }

    void initComponents(){
        editText = findViewById(R.id.food_name);
        calendarView = findViewById(R.id.expiry_date);
        floatingActionButton = findViewById(R.id.save_food);
        todaysDate = Calendar.getInstance().getTimeInMillis();
        addFoodViewModel = ViewModelProviders.of(this).get(AddFoodViewModel.class);
    }

    void setListener(CalendarView calendarView){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                expiryDate = calendar.getTimeInMillis();
            }
        });
    }

    void setListener(FloatingActionButton floatingActionButton){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make sure editText/ expiryDate not null
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(AddActivity.this, "Missing Name!", Toast.LENGTH_SHORT).show();
                } else if(expiryDate == null){
                    expiryDate = Calendar.getInstance().getTimeInMillis();
                } else {
                    addFoodViewModel.addFood(new Food(
                        editText.getText().toString(),
                        todaysDate,
                        expiryDate
                    ));
                    finish();
                }
            }
        });
    }

}

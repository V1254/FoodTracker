package test.mo.FoodTracker.View;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import test.mo.FoodTracker.Model.Food;
import test.mo.FoodTracker.R;
import test.mo.FoodTracker.ViewModel.UpdateFoodViewModel;

public class UpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    EditText editText;
    CalendarView calendarView;
    FloatingActionButton saveChangesButton;
    UpdateFoodViewModel updateFoodViewModel;
    Long expiryDate;
    Spinner spinner;
    String category; // populate to the default value.
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // initialise components
        initComponents();

        /**
         * Populates spinner and sets click listener.
         */

        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this,R.array.categories,android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinAdapter);
        spinner.setOnItemSelectedListener(this);

        // check if intents are available
        if (allIntentsAvailable()) {
            editText.setText(getIntent().getStringExtra("name"));
            calendarView.setDate(getIntent().getLongExtra("expiry", 0L));
            category = getIntent().getStringExtra("category");
            spinner.setSelection(spinAdapter.getPosition(category));
        } else {
            finish();
        }

        // date change listener
        setDateListener(calendarView);

        // listener to update db via model
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make sure name is not empty
                if (editText.getText().toString().isEmpty()) {
                    toast.setText(R.string.missing);
                    toast.show();
                } else {
                    // if the date wasn't changed use the intent expiry date;
                    if (expiryDate == null) {
                        expiryDate = getIntent().getLongExtra("expiry", 0L);
                    }
                    updateFoodViewModel.updateFood(new Food(
                            getIntent().getIntExtra("id", 0),
                            editText.getText().toString(),
                            getIntent().getLongExtra("added", 0L),
                            expiryDate,
                            category
                    ));
                    finish();
                }
            }
        });
    }

    private void initComponents() {
        editText = findViewById(R.id.update_editText);
        calendarView = findViewById(R.id.update_calender);
        saveChangesButton = findViewById(R.id.update_fab);
        spinner = findViewById(R.id.spinner2);
        updateFoodViewModel = ViewModelProviders.of(this).get(UpdateFoodViewModel.class);
        toast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);

    }


    private boolean allIntentsAvailable() {
        return getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("added") && getIntent().hasExtra("expiry");
    }


    void setDateListener(CalendarView calendarView) {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                expiryDate = calendar.getTimeInMillis();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        category = getResources().getResourceName(R.string.default_category);
    }
}

package test.mo.FoodTracker.View;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import test.mo.FoodTracker.Model.Food;
import test.mo.FoodTracker.R;
import test.mo.FoodTracker.ViewModel.AddFoodViewModel;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editText;
    DatePicker datePicker;
    FloatingActionButton floatingActionButton;
    AddFoodViewModel addFoodViewModel;
    Spinner spinner;

    Long todaysDate;
    String category;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // initialise fields/components
        initComponents();

        // listener to save to the database;
        setListener(floatingActionButton);

        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinAdapter);
        spinner.setOnItemSelectedListener(this);


    }

    private void initComponents() {
        editText = findViewById(R.id.food_name);
        datePicker = findViewById(R.id.expiry_date);
        floatingActionButton = findViewById(R.id.save_food);
        todaysDate = Calendar.getInstance().getTimeInMillis();
        toast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);
        spinner = findViewById(R.id.spinner);
        addFoodViewModel = ViewModelProviders.of(this).get(AddFoodViewModel.class);
    }


    private void setListener(FloatingActionButton floatingActionButton) {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make sure editText/ expiryDate not null
                if (editText.getText().toString().isEmpty()) {
                    toast.setText(R.string.missing);
                    toast.show();
                } else {
                    addFoodViewModel.addFood(new Food(
                            editText.getText().toString(),
                            todaysDate,
                            getLongFromPicker(),
                            category
                    ));
                    finish();
                }
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

    private Long getLongFromPicker() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        c.set(Calendar.MONTH, datePicker.getMonth());
        c.set(Calendar.YEAR, datePicker.getYear());

        return c.getTimeInMillis();
    }
}

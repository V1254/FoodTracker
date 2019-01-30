package test.mo.FoodTracker.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import test.mo.FoodTracker.Model.Food;
import test.mo.FoodTracker.NotificationManager;
import test.mo.FoodTracker.R;
import test.mo.FoodTracker.ThemeUtils;
import test.mo.FoodTracker.ViewModel.AddFoodViewModel;
import test.mo.FoodTracker.ViewModel.UpdateFoodViewModel;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editText;
    DatePicker datePicker;
    FloatingActionButton floatingActionButton;
    AddFoodViewModel addFoodViewModel;
    UpdateFoodViewModel updateFoodViewModel;
    Spinner spinner;

    Long todaysDate;
    String category;
    Toast toast;

    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentTheme = getCurrentThemeFromPreferences();
        ThemeUtils.changeTheme(AddActivity.this,currentTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // initialise fields/components
        initComponents();


        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.categories, R.layout.spinner);
        spinAdapter.setDropDownViewResource(R.layout.spinner);
        spinner.setAdapter(spinAdapter);
        spinner.setOnItemSelectedListener(this);

        // check to see what intents are available and set Listener accordingly.
        if (isUpdate()) {
            // use updateViewModel to save
            setUpdateListener(floatingActionButton);
            populateComponentsWIthIntent();
            spinner.setSelection(spinAdapter.getPosition(category));
        } else {
            // use addViewModel to save
            setAddListener(floatingActionButton);
        }

    }

    private int getCurrentThemeFromPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return pref.getInt("theme",0);
    }

    private void initComponents() {
        editText = findViewById(R.id.food_name);
        datePicker = findViewById(R.id.expiry_date);
        floatingActionButton = findViewById(R.id.save_food);
        todaysDate = Calendar.getInstance().getTimeInMillis();
        toast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);
        spinner = findViewById(R.id.spinner);
        addFoodViewModel = ViewModelProviders.of(this).get(AddFoodViewModel.class);
        updateFoodViewModel = ViewModelProviders.of(this).get(UpdateFoodViewModel.class);
        notificationManager = new NotificationManager(getApplicationContext(),"foodtracker-id");
    }


    private void setAddListener(FloatingActionButton floatingActionButton) {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make sure editText/ expiryDate not null
                if (isNullEditText()) {
                    showMissingNameToast();
                } else {
                    Food f = new Food(editText.getText().toString(),todaysDate,getLongFromPicker(),category);

                    addFoodViewModel.addFood(f);
                    notificationManager.scheduleNotification(f);
                    finish();
                }
            }
        });
    }

    private void setUpdateListener(FloatingActionButton fb) {
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make sure name is not empty
                if (isNullEditText()) {
                    showMissingNameToast();
                } else {
                    updateFoodViewModel.updateFood(new Food(
                            getIntent().getIntExtra("id", 0),
                            editText.getText().toString(),
                            getIntent().getLongExtra("added", 0L),
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

    private boolean isUpdate() {
        return getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("added") && getIntent().hasExtra("expiry");
    }

    private boolean isNullEditText() {
        return editText.getText().toString().isEmpty();
    }

    private void showMissingNameToast() {
        toast.setText(R.string.missing);
        toast.show();
    }

    private void populateComponentsWIthIntent() {
        editText.setText(getIntent().getStringExtra("name"));
        category = getIntent().getStringExtra("category");

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getIntent().getLongExtra("expiry", 0L));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, null);
    }


}

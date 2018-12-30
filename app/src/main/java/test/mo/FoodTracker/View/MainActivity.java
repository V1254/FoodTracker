package test.mo.FoodTracker.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import test.mo.FoodTracker.Adapter;
import test.mo.FoodTracker.DateConverter;
import test.mo.FoodTracker.Model.Food;
import test.mo.FoodTracker.R;
import test.mo.FoodTracker.ViewModel.DeleteViewModel;
import test.mo.FoodTracker.ViewModel.FoodListViewModel;

public class MainActivity extends AppCompatActivity {

     RecyclerView recyclerView;
     Adapter adapter;
     FloatingActionButton fab;
     DateConverter dateConverter;
     FoodListViewModel foodListViewModel;
     DeleteViewModel deleteViewModel;
     Toast toast;
    // size of the cards with the margin added (120 + 8)
     private final int cardWidth = 128;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise fields
        initComponents();

        if(recyclerView != null){
            recyclerView.setLayoutManager(new GridLayoutManager(this,3));
            recyclerView.setAdapter(adapter);
            ViewTreeObserver observer = recyclerView.getViewTreeObserver();
            // dynamically sets span count for the LayoutManager in the recyclerView.
            setListener(observer);
        }

        // get any updates to the data
        foodListViewModel.getFoodList().observe(MainActivity.this, new Observer<List<Food>>() {
            @Override
            public void onChanged(@Nullable List<Food> foods) {
                adapter.addItems(foods);
            }
        });

        // listener to create new foods
        setFoodCreateListener(fab);


        // swipe to delete function on recyclerview
        setDeleteListener(recyclerView);
    }

    private void setListener(ViewTreeObserver observer) {
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculateColumnSize();
            }
        });
    }

    private void calculateColumnSize() {
        int spanCount = (int) Math.floor(recyclerView.getWidth() / convertToPixels(cardWidth));
        ((GridLayoutManager)recyclerView.getLayoutManager()).setSpanCount(spanCount);
    }

    /**
     * Converts the passedInValue to Pixels.
     * @param cardWidth
     * @return
     */

    private float convertToPixels(int cardWidth) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return cardWidth * metrics.density;
    }


    private void initComponents(){
        recyclerView = findViewById(R.id.recyclerView);
        fab =findViewById(R.id.fab_Add);
        dateConverter =  new DateConverter(new SimpleDateFormat("dd-MM-YYYY"));
        adapter = new Adapter(new ArrayList<Food>(),dateConverter, this);
        foodListViewModel = ViewModelProviders.of(this).get(FoodListViewModel.class);
        deleteViewModel = ViewModelProviders.of(this).get(DeleteViewModel.class);
        toast = Toast.makeText(getApplicationContext(),null,Toast.LENGTH_SHORT);
    }

    private void setFoodCreateListener(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }

    private void setDeleteListener(RecyclerView recyclerView){
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Food myFood = adapter.getFoodAtPosition(position);
                        deleteViewModel.deleteFood(myFood);
                    }
                }
        );
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_button,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_All:
                if(adapter.getItemCount() == 0){
                    toast.setText(R.string.nothing_to_delete);
                    toast.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.ic_warning_black_24dp)
                            .setTitle(R.string.delete_all_items)
                            .setMessage(R.string.toast_message)
                            .setPositiveButton(R.string.positive_proceed, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteViewModel.deleteAll();
                                    toast.setText(R.string.deleted_success);
                                    toast.show();
                                }})
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   toast.setText(R.string.cancelled);
                                   toast.show();
                                }});
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return true;

            case R.id.theme_switch:
                System.out.println("Clicked the theme icon");
                startActivity(new Intent(this,ThemeSelect.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

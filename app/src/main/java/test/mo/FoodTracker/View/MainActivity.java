package test.mo.FoodTracker.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

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

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        if(recyclerView != null){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

        // hide/show animation for the floating action button
        setScrollListener(recyclerView);

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

    private void initComponents(){
        recyclerView = findViewById(R.id.recyclerView);
        fab =findViewById(R.id.fab_Add);
        dateConverter =  new DateConverter(new SimpleDateFormat("dd-MM-YYYY"));
        adapter = new Adapter(new ArrayList<Food>(),dateConverter);
        foodListViewModel = ViewModelProviders.of(this).get(FoodListViewModel.class);
        deleteViewModel = ViewModelProviders.of(this).get(DeleteViewModel.class);

    }

    private void setScrollListener(RecyclerView recyclerView){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(dy < 0){
                    // scrolled up
                    fab.show();
                } else if (dy > 0){
                    // scrolled down
                    fab.hide();
                }
            }
        });
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
}

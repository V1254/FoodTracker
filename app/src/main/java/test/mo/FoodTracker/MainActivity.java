package test.mo.FoodTracker;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // remove AllowMainThreadQueries and use background thread/ rxjava
        final FoodDatabase db = Room.databaseBuilder(getApplicationContext(),FoodDatabase.class,"FoodDatabase")
                            .allowMainThreadQueries()
                            .build();


       final List<Food> foods = db.foodDao().getAllFoods();

        recyclerView = findViewById(R.id.recyclerView);
        fab =findViewById(R.id.fab_Add);
        final Adapter adapter = new Adapter(foods);

        // handles swiping on the recycler view.
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
                        foods.remove(myFood);
                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        db.foodDao().deleteAll(myFood);
                    }
                }
        );

        helper.attachToRecyclerView(recyclerView);

        if(recyclerView != null){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

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
        
        
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: pressed!");
                startActivity(new Intent(MainActivity.this,CreateFood.class));
            }
        });

    }
}

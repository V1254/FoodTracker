package test.mo.timer;

import android.arch.persistence.room.Room;
import android.content.ClipData;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private FloatingActionButton fab;
    private static final String TAG = "MainActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // remove AllowMainThreadQueries and use background thread/ rxjava
        final foodDatabase db = Room.databaseBuilder(getApplicationContext(),foodDatabase.class,"foodDatabase")
                            .allowMainThreadQueries()
                            .build();


       final List<Food> foods = db.foodDao().getAllFoods();



        rv = findViewById(R.id.recyclerView);
        fab =findViewById(R.id.fab_Add);
        final Adapter adapter = new Adapter(foods);


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

        helper.attachToRecyclerView(rv);




        if(rv != null){
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(adapter);
        }

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(dy < 0){
                    fab.show();
                } else if (dy > 0){
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

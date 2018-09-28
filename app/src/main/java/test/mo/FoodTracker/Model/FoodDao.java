package test.mo.FoodTracker.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

// Used to access the data from the database.

@Dao
public interface FoodDao {

    @Insert(onConflict = REPLACE)
    void insertAll(Food...foods);

    @Query("SELECT * FROM Food")
    List<Food> getAllFoods();

    @Delete
    void deleteAll(Food...foods);

    @Update
    void updateFood(Food food);

    @Query("SELECT * FROM Food")
    LiveData<List<Food>> getLiveFoods();

    @Query("DELETE FROM Food")
    void nukeData();


}

package test.mo.FoodTracker;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

// Used to access the data from the database.

@Dao
public interface FoodDao {

    @Insert
    void insertAll(Food...foods);

    @Query("SELECT * FROM Food")
    List<Food> getAllFoods();

    @Delete
    void deleteAll(Food...foods);

    @Update
    void updateFood(Food food);

    @Query("SELECT * FROM Food")
    LiveData<List<Food>> getLiveFoods();


}

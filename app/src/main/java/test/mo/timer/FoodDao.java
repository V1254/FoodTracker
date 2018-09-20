package test.mo.timer;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

// Used to access the data from the database.

@Dao
public interface FoodDao {

    @Insert
    void insertAll(Food...foods);



    @Query("SELECT * from Food")
    List<Food> getAllFoods();

}

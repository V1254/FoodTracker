package test.mo.timer;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Food.class}, version = 1)
public abstract class foodDatabase extends RoomDatabase {
    // the interface that can access the database.
    public abstract FoodDao foodDao();

}

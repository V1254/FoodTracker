package test.mo.FoodTracker.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Food.class}, version = 1)
public abstract class FoodDatabase extends RoomDatabase {
    // the interface that can access the database.
    public abstract FoodDao foodDao();

    private static FoodDatabase INSTANCE;

    public static FoodDatabase getINSTANCE(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,FoodDatabase.class,"food)db").build();
        }
        return INSTANCE;
    }

}

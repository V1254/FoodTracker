package test.mo.FoodTracker;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

// Each row in the database

@Entity
public class Food {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "food_name")
    private String foodName;

    @ColumnInfo(name = "date_added")
    private Long startDate;

    @ColumnInfo(name = "expiry_date")
    private Long expiryDate;

    public Food(String foodName, Long startDate, Long expiryDate) {
        this.foodName = foodName;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }

    @Ignore
    public Food(int id, String name, Long startDate, Long expiryDate){
        this.id = id;
        this.foodName = name;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }
}

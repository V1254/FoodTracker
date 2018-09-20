package test.mo.timer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

// Each row in the database

@Entity
public class Food {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "food_name")
    private String foodName;

    @ColumnInfo(name = "date_added")
    private String startDate;

    @ColumnInfo(name = "expiry_date")
    private String expiryDate;

    public Food(String foodName, String startDate, String expiryDate) {
        this.foodName = foodName;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}

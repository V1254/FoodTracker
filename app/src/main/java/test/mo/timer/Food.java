package test.mo.timer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

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


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd,MM,YYYY");

    // Simplify Date probably to a string.

    public Food(String foodName, Date startDate, Date expiryDate) {
        this.foodName = foodName;
        this.startDate = simpleDateFormat.format(startDate);
        this.expiryDate = simpleDateFormat.format(expiryDate);
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

    public void setStartDate(Date startDate) {
        this.startDate = simpleDateFormat.format(startDate);
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = simpleDateFormat.format(expiryDate);
    }
}

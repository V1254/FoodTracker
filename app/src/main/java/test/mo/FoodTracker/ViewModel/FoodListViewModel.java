package test.mo.FoodTracker.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import test.mo.FoodTracker.Model.Food;
import test.mo.FoodTracker.Model.FoodDatabase;

// viewmodel for the main activity displaying all the recipes.

public class FoodListViewModel extends AndroidViewModel {

    private final LiveData<List<Food>> foodList;
    private FoodDatabase foodDatabase;


    public FoodListViewModel(@NonNull Application application) {
        super(application);
        foodDatabase = FoodDatabase.getINSTANCE(this.getApplication());
        foodList = foodDatabase.foodDao().getLiveFoods();
    }

    public LiveData<List<Food>> getFoodList() {
        return this.foodList;
    }
}

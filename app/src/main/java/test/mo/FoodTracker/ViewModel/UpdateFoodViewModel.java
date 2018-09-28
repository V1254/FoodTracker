package test.mo.FoodTracker.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import test.mo.FoodTracker.Model.Food;
import test.mo.FoodTracker.Model.FoodDatabase;

// vmodel for the update activity/fragment.

public class UpdateFoodViewModel extends AndroidViewModel {
    private FoodDatabase foodDatabase;

    public UpdateFoodViewModel(@NonNull Application application) {
        super(application);
        foodDatabase = FoodDatabase.getINSTANCE(this.getApplication());
    }

    public void updateFood(Food food) {
        new UpdateFoodTask(foodDatabase).execute(food);
    }

    private static class UpdateFoodTask extends AsyncTask<Food, Void, Void> {

        private FoodDatabase foodDatabase;

        public UpdateFoodTask(FoodDatabase foodDatabase) {
            this.foodDatabase = foodDatabase;
        }


        @Override
        protected Void doInBackground(Food... foods) {
            foodDatabase.foodDao().updateFood(foods[0]);
            return null;
        }
    }
}

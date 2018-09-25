package test.mo.FoodTracker.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import test.mo.FoodTracker.Model.Food;
import test.mo.FoodTracker.Model.FoodDatabase;

public class DeleteViewModel extends AndroidViewModel {
    private FoodDatabase foodDatabase;

    public DeleteViewModel(@NonNull Application application) {
        super(application);
        foodDatabase = FoodDatabase.getINSTANCE(this.getApplication());
    }

    public void deleteFood(Food food){
        new DeleteFoodTask(foodDatabase).execute(food);
    }

    private static class DeleteFoodTask extends AsyncTask<Food,Void,Void>{

        private FoodDatabase foodDatabase;

        public DeleteFoodTask(FoodDatabase foodDatabase){
            this.foodDatabase = foodDatabase;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDatabase.foodDao().deleteAll(foods[0]);
            return null;
        }
    }
}

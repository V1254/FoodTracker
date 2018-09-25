package test.mo.FoodTracker.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import test.mo.FoodTracker.Model.Food;
import test.mo.FoodTracker.Model.FoodDatabase;

public class AddFoodViewModel extends AndroidViewModel {

    private FoodDatabase foodDatabase;

    public AddFoodViewModel(@NonNull Application application) {
        super(application);
        foodDatabase = FoodDatabase.getINSTANCE(this.getApplication());
    }

    public void addFood(Food food){
        new AddFoodTask(foodDatabase).execute(food);
    }


    private static class AddFoodTask extends AsyncTask<Food,Void,Void>{
        private FoodDatabase foodDatabase;

        public AddFoodTask(FoodDatabase foodDatabase){
            this.foodDatabase = foodDatabase;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDatabase.foodDao().insertAll(foods);
            return null;
        }
    }
}

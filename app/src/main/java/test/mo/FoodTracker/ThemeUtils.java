package test.mo.FoodTracker;

import android.app.Activity;

public class ThemeUtils {
    public static void changeTheme(Activity activity, int theme){
        switch (theme){
            case 1 :activity.setTheme(R.style.DarkTheme);
                break;
            default:
                activity.setTheme(R.style.AppTheme);
        }
    }
}

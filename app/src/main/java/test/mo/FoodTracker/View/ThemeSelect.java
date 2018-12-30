package test.mo.FoodTracker.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import test.mo.FoodTracker.R;
import test.mo.FoodTracker.ThemeUtils;


public class ThemeSelect extends AppCompatActivity {

    CardView lightCard;
    CardView darkCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int currentTheme = getCurrentThemeFromPreferences();
        ThemeUtils.changeTheme(ThemeSelect.this,currentTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_select);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lightCard = findViewById(R.id.light_theme_card);
        darkCard = findViewById(R.id.dark_theme_card);
        lightCard.setOnClickListener(new View.OnClickListener() {
            TextView textView;

            @Override
            public void onClick(View v) {
                textView = v.findViewById(R.id.light_Text);
                textView.append(" ✔");
                darkCard.setEnabled(true);
                lightCard.setEnabled(false);
                storeTheme("theme",0);
                restartApp();
            }
        });

        darkCard.setOnClickListener(new View.OnClickListener() {
            TextView textView;

            @Override
            public void onClick(View v) {
                textView = v.findViewById(R.id.dark_Text);
                textView.append(" ✔");
                darkCard.setEnabled(false);
                lightCard.setEnabled(true);
                storeTheme("theme",1);
                restartApp();
            }
        });
    }


    void restartApp(){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        // relaunching main activity so clears previous one.
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    private void storeTheme(String theme, int themeId){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(theme,themeId);
        editor.apply();
    }

    private int getCurrentThemeFromPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return pref.getInt("theme",0);
    }
}

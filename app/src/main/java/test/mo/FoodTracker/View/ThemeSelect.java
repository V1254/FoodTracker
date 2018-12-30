package test.mo.FoodTracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import test.mo.FoodTracker.R;


public class ThemeSelect extends AppCompatActivity {

    CardView lightCard;
    CardView darkCard;


    // TODO: use sharedpreferences for the multiple activities

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(inDarkMode()){
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_select);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lightCard = findViewById(R.id.light_theme_card);
        darkCard = findViewById(R.id.dark_theme_card);

        if(inDarkMode()){
            darkCard.setEnabled(false);
        } else {
            lightCard.setEnabled(false);
        }

        lightCard.setOnClickListener(new View.OnClickListener() {
            TextView textView;

            @Override
            public void onClick(View v) {
                textView = v.findViewById(R.id.light_Text);
                textView.append(" ✔");
                darkCard.setEnabled(true);
                lightCard.setEnabled(false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                restartApp();
            }
        });
    }


    void restartApp(){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

    boolean inDarkMode(){
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    }
}

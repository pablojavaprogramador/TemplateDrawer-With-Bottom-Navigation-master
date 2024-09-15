package com.touchizen.drawerwithbottomnavigation.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.touchizen.drawerwithbottomnavigation.MainActivity;
import com.touchizen.drawerwithbottomnavigation.R;
import com.touchizen.drawerwithbottomnavigation.ui.terms.TermsAndConditionsActivity;
import android.preference.PreferenceManager;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 4000; // 4 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            boolean termsAccepted = preferences.getBoolean("termsAccepted", false);

            Intent intent;
            if (termsAccepted) {
                // Redirigir a MainActivity si los términos ya fueron aceptados
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            } else {
                // Redirigir a TermsAndConditionsActivity si los términos no fueron aceptados
                intent = new Intent(SplashScreenActivity.this, TermsAndConditionsActivity.class);
            }
            startActivity(intent);
            finish();
        }, SPLASH_TIME_OUT);
    }
}

package com.touchizen.drawerwithbottomnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.LoadAdError;
import com.touchizen.drawerwithbottomnavigation.ui.terms.TermsAndConditionsActivity;

public class MainActivity extends AppCompatActivity {

    private static final float END_SCALE = 0.85f;
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavView;
    private CoordinatorLayout contentView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean termsAccepted = preferences.getBoolean("termsAccepted", false);

        if (!termsAccepted) {
            Intent intent = new Intent(MainActivity.this, TermsAndConditionsActivity.class);
            startActivity(intent);
            finish();
            return; // No continuar con la inicialización de MainActivity
        }

        initToolbar();
        initFab();
        initNavigation();

        // Inicializa el SDK de AdMob
        MobileAds.initialize(this, initializationStatus -> {});
        //showInterstitialAdIfNeeded();
        // Cargar el anuncio intersticial
        loadInterstitialAd();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mostrar anuncio intersticial cuando se haga clic en el FAB
                if (mInterstitialAd != null) {
                   // mInterstitialAd.show(MainActivity.this);
                    Snackbar.make(view,"Anuncio deshabilitado",Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "No hay anuncio cargado", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initNavigation() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavView = findViewById(R.id.bottom_nav_view);
        contentView = findViewById(R.id.content_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_terms,
                R.id.bottom_home, R.id.bottom_dashboard, R.id.bottom_notifications)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        animateNavigationDrawer();
        // Listener para detectar cambios de fragmentos
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_home || destination.getId() == R.id.bottom_home) {
                showInterstitialAdIfNeeded();
            }
        });

    }

    private void showInterstitialAdIfNeeded() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(MainActivity.this);
            // Opcional: Puedes recargar el anuncio después de mostrarlo si planeas usarlo más adelante.
            loadInterstitialAd();
        } else {
            Toast.makeText(MainActivity.this, "Anuncio no cargado", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // El anuncio está cargado
                        mInterstitialAd = interstitialAd;
                        Toast.makeText(MainActivity.this, "Anuncio cargado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        // Error al cargar el anuncio
                        mInterstitialAd = null;
                        Toast.makeText(MainActivity.this, "Error al cargar anuncio: " + adError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void animateNavigationDrawer() {
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

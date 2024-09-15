package com.touchizen.drawerwithbottomnavigation.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_ACCEPTED_TERMS = "accepted_terms";

    // Guardar si el usuario ha aceptado los términos
    public static void saveAcceptedTerms(Context context, boolean accepted) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_ACCEPTED_TERMS, accepted);
        editor.apply();
    }

    // Obtener si el usuario ya aceptó los términos
    public static boolean hasAcceptedTerms(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_ACCEPTED_TERMS, false);
    }
}

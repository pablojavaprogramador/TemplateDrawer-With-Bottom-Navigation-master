package com.touchizen.drawerwithbottomnavigation.ui.terms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.touchizen.drawerwithbottomnavigation.MainActivity;
import com.touchizen.drawerwithbottomnavigation.R;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TermsAndConditionsActivity extends AppCompatActivity {

    private CheckBox acceptTermsCheckBox;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        acceptTermsCheckBox = findViewById(R.id.checkbox_accept_terms);
        continueButton = findViewById(R.id.button_continue);

        continueButton.setOnClickListener(v -> {
            if (acceptTermsCheckBox.isChecked()) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("termsAccepted", true);
                editor.putString("termsText", "Términos y condiciones aceptados."); // Puedes guardar aquí el texto de los términos si es necesario
                editor.apply();

                Intent intent = new Intent(TermsAndConditionsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(TermsAndConditionsActivity.this, "Debe aceptar los términos para continuar.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

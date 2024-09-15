package com.touchizen.drawerwithbottomnavigation.ui.terms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.touchizen.drawerwithbottomnavigation.MainActivity;
import com.touchizen.drawerwithbottomnavigation.R;

import java.io.InputStream;
import java.io.IOException;

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
                // Leer los términos desde un archivo de recursos
                String termsText = readTermsFromFile();

                // Guardar los términos aceptados en SharedPreferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("termsAccepted", true);
                editor.putString("termsText", termsText);
                editor.apply();

                // Continuar con la aplicación
                Intent intent = new Intent(TermsAndConditionsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(TermsAndConditionsActivity.this, "Debe aceptar los términos para continuar.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para leer los términos desde un archivo de recursos
    private String readTermsFromFile() {
        StringBuilder terms = new StringBuilder();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.terms_and_conditionss); // Asegúrate de tener este archivo en res/raw
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            terms.append(new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return terms.toString();
    }
}

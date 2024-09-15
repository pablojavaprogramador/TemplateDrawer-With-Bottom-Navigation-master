package com.touchizen.drawerwithbottomnavigation.ui.terms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.touchizen.drawerwithbottomnavigation.R;
import com.touchizen.drawerwithbottomnavigation.ui.splash.SplashScreenActivity;

public class TermsFragment extends Fragment {

    private Button revokeButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_terms, container, false);

        // Inicializar Views
        revokeButton = root.findViewById(R.id.button_revoke);
        final TextView textView = root.findViewById(R.id.termsContentTextView);

        // Cargar y mostrar los términos aceptados
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String termsText = preferences.getString("termsText", "No terms found");
        textView.setText(termsText);
//
//        // Manejar la reversión de la aceptación de los términos
//        revokeButton.setOnClickListener(v -> {
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("termsAccepted", false);
//            editor.putString("termsText", "Aceptación de términos revocada."); // Opcional
//            editor.apply();
//
//            // Mostrar mensaje y redirigir a la pantalla de presentación
//            Toast.makeText(requireContext(), "Aceptación de términos revocada.", Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(requireContext(), SplashScreenActivity.class);
//            startActivity(intent);
//            requireActivity().finish();
//        });

        return root;
    }
}

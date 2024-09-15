package com.touchizen.drawerwithbottomnavigation.ui.terms;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.touchizen.drawerwithbottomnavigation.R;

public class TermsFragment extends Fragment {

    private TermsViewModel termsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        termsViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_terms, container, false);
        final TextView textView = root.findViewById(R.id.text_terms);

        // Cargar y mostrar los términos aceptados
        SharedPreferences preferences = requireActivity().getSharedPreferences("AppPreferences", 0);
        String termsText = preferences.getString("termsText", "No terms found");
        textView.setText(termsText);

        return root;
    }
}

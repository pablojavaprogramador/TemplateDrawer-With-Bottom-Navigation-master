package com.touchizen.drawerwithbottomnavigation.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider; // Cambiado a ViewModelProvider

import com.touchizen.drawerwithbottomnavigation.R;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                new ViewModelProvider(this).get(SendViewModel.class); // Cambiado a ViewModelProvider
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        sendViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() { // Cambiado a getViewLifecycleOwner()
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}

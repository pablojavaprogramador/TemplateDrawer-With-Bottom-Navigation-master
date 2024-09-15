package com.touchizen.drawerwithbottomnavigation.ui.terms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TermsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TermsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("No terms found");
    }

    public void setTermsText(String termsText) {
        mText.setValue(termsText);
    }

    public LiveData<String> getText() {
        return mText;
    }
}

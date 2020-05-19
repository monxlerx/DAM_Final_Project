package com.example.dam_project.ui.menuBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenuBarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MenuBarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is menu fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}

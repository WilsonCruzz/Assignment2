package com.example.assignment2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    int counter = 0;
    private MutableLiveData<Integer> myValue;

    public MainActivityViewModel(){
        myValue = new MutableLiveData<>();
        myValue.setValue(counter);

    }

    public void addOne(){
        myValue.setValue(++counter);

    }
    public LiveData<Integer> getNumber(){
        return myValue;
    }
}

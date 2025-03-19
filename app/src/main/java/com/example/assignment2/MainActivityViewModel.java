package com.example.assignment2;

import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    int counter = 0;

    public MainActivityViewModel(){

    }

    public void addOne(){
        ++counter;
    }
    public int getNumber(){
        return counter;
    }
}

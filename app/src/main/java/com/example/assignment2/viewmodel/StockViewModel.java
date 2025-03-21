package com.example.assignment2.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment2.model.StockModel;
import com.example.assignment2.utils.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class StockViewModel extends ViewModel {

    private final MutableLiveData<StockModel> stockData = new MutableLiveData<StockModel>();
    StockModel stockModel = new StockModel();

    public LiveData<StockModel> getStockData(){
        return stockData;
    }
    public void Refresh(){
        String urlString = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD,JPY,EUR";

        ApiClient.get(urlString, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                assert response.body() !=null;
                String responseData = response.body().string();

                JSONObject json = null;
                try{
                    json = new JSONObject(responseData);
                    String strUSD = "US$" + json.getString("USD");
                    String strJPY = "JP$" + json.getString("JPY");
                    String strEUR = "EUR$" + json.getString("EUR");

                    stockModel.setUsdPrice(strUSD);
                    stockModel.setJpyPrice(strJPY);
                    stockModel.setEuroPrice(strEUR);
                    stockData.postValue(stockModel);


                }catch(JSONException e){
                    throw new RuntimeException(e);

                }

            }
        });
    }
}

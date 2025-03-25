package com.example.assignment2.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment2.model.MovieModel;
import com.example.assignment2.model.StockModel;
import com.example.assignment2.utils.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<List<MovieModel>> movieData = new MutableLiveData<List<MovieModel>>();
    MovieModel movieModel = new MovieModel();

    public LiveData<List<MovieModel>> getMovieData(){
        return movieData;
    }
    public void Search(String title){
        String urlString = "https://www.omdbapi.com/?apikey=8057235a&s="+title;

        Log.i("tag",urlString);

        ApiClient.get(urlString, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("API Error", "Request failed", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                assert response.body() !=null;
                String responseData = response.body().string();


                try{
                    JSONObject json = new JSONObject(responseData);
                    List<MovieModel> movieList = new ArrayList<>();
                    for (int i = 0; i < json.getJSONArray("Search").length(); i++) {
                        JSONObject movieJson = json.getJSONArray("Search").getJSONObject(i);
                        MovieModel movieModel = new MovieModel();

                        movieModel.setPoster(movieJson.getString("Poster"));
                        movieModel.setTitle(movieJson.getString("Title"));
                        movieModel.setYear(movieJson.getString("Year"));
                        movieModel.setType(movieJson.getString("Type"));

                        movieList.add(movieModel);
                    }

                    movieData.postValue(movieList);

                }catch(JSONException e){
                    throw new RuntimeException(e);
                }

            }
        });
    }
}

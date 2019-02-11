package com.kevlarcodes.bakingapp.network;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kevlarcodes.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkApi {

    @SuppressWarnings("WeakerAccess")
    final MutableLiveData<List<Recipe>> mRecipeList = new MutableLiveData<>();
    private final static String RecipeBaseURL = "https://d17h27t6h515a5.cloudfront.net/";


    public NetworkApi(){

    }

    public MutableLiveData<List<Recipe>> getRecipeList() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(RecipeBaseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retro = builder.build();

        RecipeService recipeService = retro.create(RecipeService.class);


        Call<List<Recipe>> call = recipeService.getRecipeList();


        Log.d( "netAPI", "calling");


        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                //mRecipeList.setValue(response.body());
                if (response.isSuccessful() ) {
                    mRecipeList.setValue(response.body());
                    Log.e("netAPI", "onResponseSuccess: " + response.body().toString());
                }
                else {
                    Log.e("netAPI", "onResponseError: " + response.errorBody().toString());
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.d("netAPI", "RecipeService Unavailable");
                //TODO throw exception back to caller, so UI can provide a toast.
            }
        });

        return  mRecipeList;
    }

}


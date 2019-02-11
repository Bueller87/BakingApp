package com.kevlarcodes.bakingapp.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.kevlarcodes.bakingapp.network.NetworkApi;

import java.util.List;

public class RecipeViewModel extends ViewModel {
     //private LiveData<List<Recipe>> mRecipeList;
     private List<Recipe> mRecipeList;
     private final NetworkApi networkApi = new NetworkApi();
    private Recipe mCurrentRecipe;

     /*public RecipeViewModel (Application application) {
         super(application);

         //mRecipeList = networkApi.getRecipeList();
     }*/

    public LiveData<List<Recipe>> getRecipeList() {
        MutableLiveData<List<Recipe>> mutableRecipeList = new MutableLiveData<>();
        if(mRecipeList == null) {
            mutableRecipeList = networkApi.getRecipeList();
        } else{
            mutableRecipeList.setValue(mRecipeList);
        }

        if(mutableRecipeList == null) {
            mutableRecipeList = networkApi.getRecipeList();
        }
        return mutableRecipeList;
    }

    public void setSavedRecipeList(List<Recipe> savedRecipeList) {
        this.mRecipeList = savedRecipeList;
    }
    public void setCurrentRecipe(Recipe recipe) {
         this.mCurrentRecipe = recipe;
    }

    public Recipe getCurentRecipe() {
         return mCurrentRecipe;
    }
}

package com.kevlarcodes.bakingapp.network;


import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;
import com.kevlarcodes.bakingapp.models.Recipe;

interface RecipeService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipeList();
}
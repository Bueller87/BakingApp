package com.kevlarcodes.bakingapp.widgets;


import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.kevlarcodes.bakingapp.R;
import com.kevlarcodes.bakingapp.adapters.RecipeListAdapter;
import com.kevlarcodes.bakingapp.models.Ingredient;
import com.kevlarcodes.bakingapp.models.Recipe;
import com.kevlarcodes.bakingapp.models.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The configuration screen for the {@link BakingWidget BakingWidget} AppWidget.
 */
public class BakingWidgetConfigureActivity extends AppCompatActivity implements RecipeListAdapter.OnWidgetSelectRecipeHandler {

    private static final String PREFS_NAME = "com.kevlarcodes.bakingapp.widgets.BakingWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private RecipeListAdapter mAdapter;
    private List<Recipe> recipeList = new ArrayList<>();

    public BakingWidgetConfigureActivity() {

        super();
    }


    public static void deleteSharedTitle(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);


        prefs.apply();
    }

    public static String loadSharedIngredients(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String json = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (json != null) {
            return json;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }


    public static void saveSharedIngredients(Context context, int appWidgetId, List<Ingredient> ingredients) {

        Gson gson = new Gson();
        String json = gson.toJson(ingredients);


        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, json);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);


        setResult(RESULT_CANCELED);


        setContentView(R.layout.baking_widget_configure);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }


        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        RecipeViewModel model = ViewModelProviders.of(this)
                .get(RecipeViewModel.class);


        recipeList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rv_widget);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new RecipeListAdapter(recipeList, this);
        mAdapter.setWidgetHandler(this);
        mAdapter.fromWidget(true);
        recyclerView.setAdapter(mAdapter);


        model.getRecipeList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                recipeList.clear();
                recipeList = recipes;
                mAdapter.updateAdapter(recipeList);
            }
        });

    }


    @Override
    public void onWidgetSelectorClick(Recipe recipe) {

        final Context context = BakingWidgetConfigureActivity.this;
        List<Ingredient> ingredients = recipe.getIngredients();
        saveSharedIngredients(context, mAppWidgetId, ingredients);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        BakingWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();

    }
}


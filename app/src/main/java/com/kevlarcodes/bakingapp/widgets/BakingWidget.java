package com.kevlarcodes.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.kevlarcodes.bakingapp.R;
import com.kevlarcodes.bakingapp.models.Ingredient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class BakingWidget extends AppWidgetProvider {
    public static final String TAG = "BakingWidget";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        String widgetText =context.getString(R.string.widget_error_no_ingredients);
        String json ="";
        try {
            json = BakingWidgetConfigureActivity.loadSharedIngredients(context, appWidgetId);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Ingredient>>(){}.getType();
            List<Ingredient> ingredients;
            ingredients = gson.fromJson(json,type);
            widgetText ="";
            for (Ingredient in : ingredients) {
                widgetText += in.getIngredient() + "\n";
            }
        } catch (JsonSyntaxException e) {
            Log.d(TAG, "Malformed JSON:\n %s" +  json);
        } catch (Exception e) {
            Log.e(TAG, "Unexpected Error: " + e.getMessage() );
        }
        //update remote view's TestView
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            BakingWidgetConfigureActivity.deleteSharedTitle(context, appWidgetId);
        }
    }


}


package com.kevlarcodes.bakingapp.utils;

import android.webkit.MimeTypeMap;

import com.kevlarcodes.bakingapp.adapters.StepsTitleBar;
import com.kevlarcodes.bakingapp.models.Ingredient;
import com.kevlarcodes.bakingapp.models.IngredientTitleBar;
import com.kevlarcodes.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StepsOrganizer {


    public StepsOrganizer() {

    }


    public ArrayList<Object> getItems(List<Ingredient> ingredients, List<Step> steps) {
        ArrayList<Object> itemsList = new ArrayList<>();


        StepsTitleBar stepsTitleBar = new StepsTitleBar(steps.size() -1);


        IngredientTitleBar ingredientTitleBar = new IngredientTitleBar(ingredients.size());

        itemsList.add(ingredientTitleBar);


        itemsList.addAll(ingredients);
        itemsList.add(stepsTitleBar);
        itemsList.addAll(steps);

        return itemsList;
    }

    public Step prepareVideo (Step step) {
        if(step.getVideoURL().isEmpty()){
            step.setVideoURL("");
        }
        else {
            step.setVideoURL(step.getVideoURL());
        }



        String thumbnailURL = step.getThumbnailURL();
        String mimeType = MimeTypeMap.getFileExtensionFromUrl(thumbnailURL);

        if(mimeType.equals("mp4")){
            step.setVideoURL(thumbnailURL);
            step.setThumbnailURL("");
        }
        return step;

    }
}

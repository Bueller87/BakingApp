package com.kevlarcodes.bakingapp.models;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.kevlarcodes.bakingapp.utils.StepsOrganizer;

import java.util.ArrayList;
import java.util.List;

public class DetailsViewModel extends ViewModel {

    private final MutableLiveData<List<Ingredient>> mIngredientsList = new MutableLiveData<>();
    private final MutableLiveData<List<Step>> mStepsList = new MutableLiveData<>();
    private Step step;
    private final StepsOrganizer mStepsOrganizer = new StepsOrganizer();


    public void setRecipeMutableLiveData(Recipe recipe){

        mIngredientsList.setValue(recipe.getIngredients());
        mStepsList.setValue(recipe.getSteps());

    }

    public ArrayList<Object> getRecyclerModel(){

        List<Step> steps = mStepsList.getValue();
        List<Ingredient> ingredients = mIngredientsList.getValue();
        ArrayList<Object> itemsList;

        itemsList = mStepsOrganizer.getItems(ingredients,steps);

        return itemsList;
    }

    public Step getStep() {
        return step;
    }

    public void setStep (Step step) {
        this.step = step;
    }

}

package com.kevlarcodes.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SavedRecipes implements Parcelable {

    private List<Recipe> savedRecipe;

    public List<Recipe> getSavedRecipe() {
        return savedRecipe;
    }

    public void setSavedRecipe(List<Recipe> savedRecipe) {
        this.savedRecipe = savedRecipe;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.savedRecipe);
    }

    public SavedRecipes() {
    }

    private SavedRecipes(Parcel in) {
        this.savedRecipe = in.createTypedArrayList(Recipe.CREATOR);
    }

    public static final Parcelable.Creator<SavedRecipes> CREATOR = new Parcelable.Creator<SavedRecipes>() {
        @Override
        public SavedRecipes createFromParcel(Parcel source) {
            return new SavedRecipes(source);
        }

        @Override
        public SavedRecipes[] newArray(int size) {
            return new SavedRecipes[size];
        }
    };
}

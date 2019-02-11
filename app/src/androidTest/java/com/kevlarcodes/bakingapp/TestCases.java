package com.kevlarcodes.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.kevlarcodes.bakingapp.models.Ingredient;
import com.kevlarcodes.bakingapp.models.Recipe;
import com.kevlarcodes.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

public class TestCases implements Parcelable {

    private Recipe recipe;
    private Ingredient ingredient;
    private Step step;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();

    TestCases(){

    }
    private Step stepLoad(){
        step = new Step(1,"introduction", "introduction video", "", "" );
        return step;
    }



    private Ingredient ingredientLoad(){

        ingredient = new Ingredient(2,"cup", "Apples");
        return ingredient;
    }

    public Recipe testDataLoad(){

        ingredients.add(ingredientLoad());
        steps.add(stepLoad());


        recipe = new Recipe(1,"Apple Pie",ingredients,steps,2,"" );

        return recipe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.recipe, flags);
        dest.writeParcelable(this.ingredient, flags);
        dest.writeParcelable(this.step, flags);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
    }

    private TestCases(Parcel in) {
        this.recipe = in.readParcelable(Recipe.class.getClassLoader());
        this.ingredient = in.readParcelable(Ingredient.class.getClassLoader());
        this.step = in.readParcelable(Step.class.getClassLoader());
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.steps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Parcelable.Creator<TestCases> CREATOR = new Parcelable.Creator<TestCases>() {
        @Override
        public TestCases createFromParcel(Parcel source) {
            return new TestCases(source);
        }

        @Override
        public TestCases[] newArray(int size) {
            return new TestCases[size];
        }
    };
}

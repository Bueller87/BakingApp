package com.kevlarcodes.bakingapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevlarcodes.bakingapp.R;
import com.kevlarcodes.bakingapp.adapters.RecipeListAdapter;
import com.kevlarcodes.bakingapp.models.Recipe;
import com.kevlarcodes.bakingapp.models.RecipeViewModel;
import com.kevlarcodes.bakingapp.models.SavedRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RecipeListFragment extends Fragment {

    private RecipeListAdapter mAdapter;
    private RecipeViewModel mViewModel;
    private List<Recipe> mRecipeList = new ArrayList<>();

    //private List<Recipe> recipeList = new ArrayList<>();
    public RecipeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        //FragmentActivity
        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);
        }
        if(savedInstanceState != null) {
            SavedRecipes recipeList;
            recipeList = savedInstanceState.getParcelable("savedData");


            List<Recipe> recipes = new ArrayList<>(Objects.requireNonNull(recipeList).getSavedRecipe());
            mViewModel.setSavedRecipeList(recipes);


        }
        RecyclerView recyclerView = view.findViewById(R.id.rv_recipes);
        GridLayoutManager glm = new GridLayoutManager(getContext(), 1);
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(glm);
        mAdapter = new RecipeListAdapter(mRecipeList, getActivity());
        recyclerView.setAdapter(mAdapter);

        mViewModel.getRecipeList().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipeList) {
                mRecipeList.clear();
                mRecipeList = recipeList;
                mAdapter.refreshAdapter(mRecipeList);
            }
        });
        return view;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        //save recipes  for offline use
        List<Recipe> recipes;
        SavedRecipes savedRecipes = new SavedRecipes();


        recipes = mViewModel.getRecipeList().getValue();
        savedRecipes.setSavedRecipe(recipes);
        bundle.putParcelable("savedData",savedRecipes);
    }

}

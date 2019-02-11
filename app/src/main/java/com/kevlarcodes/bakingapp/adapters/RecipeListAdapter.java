package com.kevlarcodes.bakingapp.adapters;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevlarcodes.bakingapp.activities.DetailsActivity;
import com.kevlarcodes.bakingapp.models.Recipe;
import com.kevlarcodes.bakingapp.R;

public class RecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Recipe> recipeList = new ArrayList<>();
    private Context context;
    private TextView mRecipeTextView;
    private OnWidgetSelectRecipeHandler onWidgetClick;
    private boolean isFromWidget = false;

    public RecipeListAdapter(List<Recipe> recipeList, Context context) {

        if (null != recipeList) {
            this.recipeList = recipeList;
            this.context = context;
        }
    }

    public void refreshAdapter(List<Recipe> newData) {
        this.recipeList.clear();
        recipeList.addAll(newData);

        notifyDataSetChanged();
    }

    public void updateAdapter(List<Recipe> newRecipes) {
        this.recipeList.clear();
        this.recipeList.addAll(newRecipes);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View recipeView = inflater.inflate(R.layout.list_item_recipe, parent, false);
        viewHolder = new RecipeViewHolder(recipeView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecipeViewHolder recipeViewHolder = (RecipeViewHolder) holder;
        final Recipe thisRecipe = recipeList.get(position);
        recipeViewHolder.getNameTextView().setText(thisRecipe.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFromWidget) {
                    onWidgetClick.onWidgetSelectorClick(thisRecipe);
                } else {
                    Recipe recipe = recipeList.get(position);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("thisRecipe", thisRecipe);
                    context.startActivity(intent);
                }


            }
        });
    }

    public interface OnWidgetSelectRecipeHandler {
        void onWidgetSelectorClick(Recipe recipe);
    }

    public void setWidgetHandler(OnWidgetSelectRecipeHandler handler) {
        onWidgetClick = handler;
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void fromWidget(boolean wiget) {
        isFromWidget = wiget;
    }
}

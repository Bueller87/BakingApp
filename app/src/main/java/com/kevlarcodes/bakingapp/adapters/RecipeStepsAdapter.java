package com.kevlarcodes.bakingapp.adapters;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevlarcodes.bakingapp.R;
import com.kevlarcodes.bakingapp.models.Ingredient;
import com.kevlarcodes.bakingapp.models.IngredientTitleBar;
import com.kevlarcodes.bakingapp.models.*;

import java.text.Format;
import java.util.ArrayList;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Object> sortedList;
    private OnNextStepClickHandler mStepHandler;
    private static final int INGREDIENTS_HEADER = 0;
    private static final int INGREDIENTS = 1;
    private static final int STEPS_HEADER = 2;
    private static final int STEPS = 3;


    public interface OnNextStepClickHandler{
        void onNextStep(Step step);
    }

    public void setOnStepClickHandler(OnNextStepClickHandler clickHandler){
        this.mStepHandler = clickHandler;
    }

    public RecipeStepsAdapter(ArrayList<Object> sortedList){
        this.sortedList = sortedList;

    }

    @Override
    public int getItemViewType(int position){

        if( sortedList.get(position) instanceof IngredientTitleBar){
            return INGREDIENTS_HEADER;
        } else if(sortedList.get(position) instanceof Ingredient){
            return INGREDIENTS;
        } else if(sortedList.get(position) instanceof StepsTitleBar){
            return STEPS_HEADER;
        } else if (sortedList.get(position)instanceof Step){
            return STEPS;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case INGREDIENTS_HEADER:
                View ingredientHeader = inflater.inflate(R.layout.list_item_ingred_title_bar, parent, false);
                viewHolder = new IngredientsTitleBarViewHolder(ingredientHeader);
                break;
            case INGREDIENTS:
                View ingredientsView = inflater.inflate(R.layout.list_item_ingred, parent, false);
                viewHolder = new IngredientsViewHolder(ingredientsView);
                break;
            case STEPS_HEADER:
                View stepsHeader = inflater.inflate(R.layout.list_item_steps_title_bar, parent, false);
                viewHolder = new StepsTitleBarViewHolder(stepsHeader);
                break;
            default:
                View stepsView = inflater.inflate(R.layout.list_item_step, parent, false);
                viewHolder = new StepsViewHolder(stepsView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case INGREDIENTS_HEADER:
                IngredientsTitleBarViewHolder ingredTitleViewHolder = (IngredientsTitleBarViewHolder) holder;
                configIngredientsHeaderViewHolder(ingredTitleViewHolder, position);
                break;
            case INGREDIENTS:
                IngredientsViewHolder ingredientsViewHolder = (IngredientsViewHolder) holder;
                configIngredientViewHolder(ingredientsViewHolder, position);
                break;
            case STEPS_HEADER:
                StepsTitleBarViewHolder stepsHeaderViewHolder = (StepsTitleBarViewHolder) holder;
                configStepsHeaderViewHolder(stepsHeaderViewHolder,position);
                break;
            case STEPS:
                StepsViewHolder stepsViewHolder = (StepsViewHolder) holder;
                configStepsViewHolder(stepsViewHolder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    private void configIngredientsHeaderViewHolder(final IngredientsTitleBarViewHolder holder, int position){

        final IngredientTitleBar ingredientHeader = (IngredientTitleBar) sortedList.get(position);
        holder.getTitleBar().setText(ingredientHeader.getHeader());
    }

    private void configStepsHeaderViewHolder(final StepsTitleBarViewHolder holder, int position){

        final StepsTitleBar stepsHeader = (StepsTitleBar) sortedList.get(position);

        String titleBarText = stepsHeader.getHeader();
        holder.getStepsTitleBar().setText(titleBarText);
    }

    private void configIngredientViewHolder(final IngredientsViewHolder holder, int position){
        final Ingredient ingredient = (Ingredient) sortedList.get(position);
        holder.getIngredients().setText(ingredient.getIngredient());
        holder.getQuantity().setText(String.valueOf(ingredient.getQuantity()));
        holder.getMeasure().setText(ingredient.getMeasure());
    }

    private void configStepsViewHolder(final StepsViewHolder holder, final int position){
        final Step step = (Step) sortedList.get(position);
        String label = step.getShortDescription();
        int id = step.getId();
        if (id > 0) {
            label = String.format("%d. %s", id, label);
        }
        holder.getSteps().setText(label);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mStepHandler != null){
                    mStepHandler.onNextStep(step);
                }
            }
        });
    }
}

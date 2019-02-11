package com.kevlarcodes.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kevlarcodes.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsViewHolder extends RecyclerView.ViewHolder {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_ingred)
    TextView ingredTextView;

    public TextView getIngredients() {
        return ingredTextView;
    }

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_amount)
    TextView quantityTextView;

    public TextView getQuantity() {
        return quantityTextView;
    }

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_measuring)
    TextView measureTextView;

    public TextView getMeasure() {
        return measureTextView;
    }

    public IngredientsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}


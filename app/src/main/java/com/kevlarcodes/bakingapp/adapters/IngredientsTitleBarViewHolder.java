package com.kevlarcodes.bakingapp.adapters;

import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.kevlarcodes.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
@SuppressWarnings("WeakerAccess")
public class IngredientsTitleBarViewHolder extends RecyclerView.ViewHolder {

    //@SuppressWarnings("WeakerAccess")
    //@BindView(R.id.tv_ingred_title_bar)
    TextView titleBarTextView;


    public IngredientsTitleBarViewHolder(View itemView) {
        super(itemView);
        titleBarTextView = itemView.findViewById(R.id.tv_ingred_title_bar);
        //ButterKnife.bind(this, titleBarTextView);
    }

    public TextView getTitleBar() {
        return titleBarTextView;
    }
}
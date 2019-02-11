package com.kevlarcodes.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kevlarcodes.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsViewHolder extends RecyclerView.ViewHolder {
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_step)
    TextView stepsTextView;
    public TextView getSteps() {
        return stepsTextView;
    }

    public StepsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
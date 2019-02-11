package com.kevlarcodes.bakingapp.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kevlarcodes.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsTitleBarViewHolder extends RecyclerView.ViewHolder{
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_steps_title_bar)
    TextView stepsTitleBar;
    public TextView getStepsTitleBar() {
        return stepsTitleBar;
    }

    public StepsTitleBarViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}

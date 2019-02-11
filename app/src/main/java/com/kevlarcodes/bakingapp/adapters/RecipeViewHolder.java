package com.kevlarcodes.bakingapp.adapters;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.kevlarcodes.bakingapp.R;
public class RecipeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_recipe_name)
    TextView nameTextView;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getNameTextView() {
        return nameTextView;
    }
}

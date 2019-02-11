package com.kevlarcodes.bakingapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.kevlarcodes.bakingapp.R;
import com.kevlarcodes.bakingapp.fragments.RecipeDetailFragment;
import com.kevlarcodes.bakingapp.fragments.StepsFragment;
import com.kevlarcodes.bakingapp.models.DetailsViewModel;
import com.kevlarcodes.bakingapp.models.Step;

import java.util.Objects;

//step_detail_container
public class DetailsActivity extends AppCompatActivity implements StepsFragment.SelectStep, StepsFragment.TitleBarChange{
    private final StepsFragment mStepsFragment = new StepsFragment();
    private final FragmentManager mFragmentManager = getSupportFragmentManager();
    private boolean isTwoPane = false;
    private static final String CURR_STEP = "savedStep";
    private static final String READY = "whenReady";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mStepsFragment.setSelectStep(this);

        if (findViewById(R.id.step_detail_container) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
            setBackStackOnChange();
        }

        if (savedInstanceState == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.steps_container, mStepsFragment)
                    .addToBackStack(null)
                    .commit();
        }


    }


    private void setBackStackOnChange(){
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                if (getSupportFragmentManager().getBackStackEntryCount()> 0) {
                    Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);

                }
            }

        });
    }






    @Override
    public void stepSelected() {

        RecipeDetailFragment stepDetailView = new RecipeDetailFragment();
        if(isTwoPane){

            mFragmentManager.beginTransaction()
                    .replace(R.id.step_detail_container, stepDetailView)
                    .commit();

        }else{
            mFragmentManager.beginTransaction()
                    .replace(R.id.steps_container, stepDetailView)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(!isTwoPane){
            switch (item.getItemId()) {
                case android.R.id.home:
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    if (fragmentManager.getBackStackEntryCount() > 0) {
                        fragmentManager.popBackStack();
                    }
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            return super.onOptionsItemSelected(item);
        }
    }







    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        DetailsViewModel detailViewModel = new DetailsViewModel();
        ViewModelProviders.of(this).get(DetailsViewModel.class);
        Step step = savedInstanceState.getParcelable(CURR_STEP);
        detailViewModel.setStep(step);

        boolean playWhenReady = savedInstanceState.getBoolean(READY);
        Bundle bundle = new Bundle();

        bundle.putBoolean(READY,playWhenReady);
    }

    @Override
    public void setTitleText(String title) {
        if(getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(title);
        }
    }
}

package com.kevlarcodes.bakingapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevlarcodes.bakingapp.R;
import com.kevlarcodes.bakingapp.adapters.RecipeStepsAdapter;
import com.kevlarcodes.bakingapp.models.DetailsViewModel;
import com.kevlarcodes.bakingapp.models.Recipe;
import com.kevlarcodes.bakingapp.models.RecipeViewModel;
import com.kevlarcodes.bakingapp.models.Step;

import java.util.Objects;


public class StepsFragment extends Fragment implements RecipeStepsAdapter.OnNextStepClickHandler {
    private static final String LAST_POSITION = "prevIndex";
    private static final String LAST_RECIPE = "prevRecipe";

    private DetailsViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private TitleBarChange mTitleBar;
    private SelectStep selectStep;

    private Recipe recipe;


    public StepsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        selectStep = (SelectStep) context;
        mTitleBar = (TitleBarChange) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        if (savedInstanceState == null) {
            Intent intent = Objects.requireNonNull(getActivity()).getIntent();
            recipe = intent.getParcelableExtra("thisRecipe");
        } else {
            recipe = savedInstanceState.getParcelable(LAST_RECIPE);
        }
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DetailsViewModel.class);
        mViewModel.setRecipeMutableLiveData(recipe);

        mTitleBar.setTitleText(recipe.getName());


        mRecyclerView = rootView.findViewById(R.id.rv_recipe_details_list);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),LinearLayoutManager.VERTICAL));
        RecipeStepsAdapter mAdapter = new RecipeStepsAdapter(mViewModel.getRecyclerModel());
        mAdapter.setOnStepClickHandler(this);
        mRecyclerView.setAdapter(mAdapter);

        if(savedInstanceState != null){
            int position = savedInstanceState.getInt(LAST_POSITION);
            mRecyclerView.scrollToPosition(position);
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            int position = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstVisibleItemPosition();

            outState.putInt(LAST_POSITION, position);
            outState.putParcelable(LAST_RECIPE, recipe);
        } catch (Exception e) {
            Log.e("StepsFragment", e.toString());
        }

    }

    @Override
    public void onNextStep(Step step) {
        mViewModel.setStep(step);
        selectStep.stepSelected();
    }

    public interface TitleBarChange {
        void setTitleText(String title);
    }


    public interface SelectStep {
        void stepSelected();
    }

    public void setSelectStep(SelectStep selectStep) {
        this.selectStep = selectStep;
    }






    /*
    // d: Rename and change types and number of parameters
    public static StepsFragment newInstance(String param1, String param2) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/



    /*// d: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}

package com.kevlarcodes.bakingapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.kevlarcodes.bakingapp.R;
import com.kevlarcodes.bakingapp.models.DetailsViewModel;
import com.kevlarcodes.bakingapp.models.Step;
import com.kevlarcodes.bakingapp.utils.StepsOrganizer;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class RecipeDetailFragment extends Fragment {
    @BindView(R.id.no_video_placeholder)
    ImageView ivNoVideo;
    @BindView(R.id.tvStepDescription)
    TextView tvStepDescription;

    private PlayerView mPlayerView;
    private static final String PREV_POSITION = "lastPosition";
    private SimpleExoPlayer mediaPlayer;
    private static final String WINDOW = "currentWindow";
    private Uri videoAddress;

    private static final String STEP = "savedStep";
    private static final String READY = "whenReady";
    private boolean playWhenReady = true;
    private Step mCurrStep;
    private long playbackPosition = 0;
    private int window = 0;

    @BindView(R.id.tvStepsHeader)
    TextView tvStepsHeader;


    public RecipeDetailFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = new Bundle();
        if (bundle.containsKey(READY)) {
            playWhenReady = bundle.getBoolean(READY);
        }

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PREV_POSITION);
            window = savedInstanceState.getInt(WINDOW);
            playWhenReady = savedInstanceState.getBoolean(READY);
        }

        mPlayerView = rootView.findViewById(R.id.video_view);
        DetailsViewModel model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DetailsViewModel.class);
        StepsOrganizer listSorter = new StepsOrganizer();

        if (savedInstanceState != null) {
            mCurrStep = savedInstanceState.getParcelable(STEP);
        } else {
            mCurrStep = model.getStep();
        }

        mCurrStep = listSorter.prepareVideo(mCurrStep);


        tvStepsHeader.setText(mCurrStep.getShortDescription());
        tvStepDescription.setText(mCurrStep.getDescription());


        if (!mCurrStep.getVideoURL().isEmpty()) {

            videoAddress = Uri.parse(mCurrStep.getVideoURL());
            ivNoVideo.setVisibility(View.GONE);

        } else {
            mPlayerView.setVisibility(View.GONE);
            ivNoVideo.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(R.drawable.no_video_placeholder)
                    .into(ivNoVideo);
        }


        return rootView;
    }

    private void initExoPlayer() {

        mediaPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());
        mPlayerView.setPlayer(mediaPlayer);
        mediaPlayer.setPlayWhenReady(playWhenReady);
        mediaPlayer.seekTo(window, playbackPosition);

        if (videoAddress != null) {
            MediaSource mediaSource = createMediaSource(videoAddress);
            mediaPlayer.prepare(mediaSource, false, false);
        }
    }

    private MediaSource createMediaSource(Uri videoUri) {

        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("ExoPlayer"))
                .createMediaSource(videoUri);
    }

    private void savePlayerState() {
        if (mediaPlayer != null) {
            playbackPosition = mediaPlayer.getCurrentPosition();
            window = mediaPlayer.getCurrentWindowIndex();
            playWhenReady = mediaPlayer.getPlayWhenReady();
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //Note: Android version call these events if differently
    // Ver <= 23  Create/Release on Resume/Pause
    // Ver 24-27 Pause, SaveInstance, Stop
    // Ver 28+  Pause, Stop, SaveInstance
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initExoPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUI();
        if ((Util.SDK_INT <= 23 || mediaPlayer == null)) {
            initExoPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //save state here for all Android flavors
        savePlayerState();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PREV_POSITION, playbackPosition);
        outState.putInt(WINDOW, window);
        outState.putBoolean(READY, playWhenReady);
        outState.putParcelable(STEP, mCurrStep);
    }

    private void unBundleSavedState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PREV_POSITION);
            window = savedInstanceState.getInt(WINDOW);
            mCurrStep = savedInstanceState.getParcelable(STEP);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unBundleSavedState(savedInstanceState);
    }
}

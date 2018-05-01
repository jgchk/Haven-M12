package com.jgchk.haven.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.jgchk.haven.BR;
import com.jgchk.haven.R;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.databinding.ActivityDetailBinding;
import com.jgchk.haven.ui.base.BaseActivity;
import com.jgchk.haven.utils.AppLogger;

import javax.inject.Inject;

public class DetailActivity extends BaseActivity<ActivityDetailBinding, DetailViewModel> implements DetailNavigator {

    private static final String INTENT_SHELTER = "INTENT_SHELTER";

    @Inject
    DetailViewModel mDetailViewModel;
    private ActivityDetailBinding mActivityDetailBinding;

    public static Intent newIntent(Context context, Shelter shelter) {
        return new Intent(context, DetailActivity.class)
                .putExtra(INTENT_SHELTER, shelter);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public DetailViewModel getViewModel() {
        return mDetailViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailBinding = getViewDataBinding();
        mDetailViewModel.setNavigator(this);

        setUp();
    }

    private void setUp() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Shelter shelter = (Shelter) bundle.getSerializable(INTENT_SHELTER);
            mDetailViewModel.onShelterLoaded(shelter);
        }

        mDetailViewModel.setNumReservations(mActivityDetailBinding.npVacancyClaim.getValue());

        setSupportActionBar(mActivityDetailBinding.toolbar);
    }

    @Override
    public void handleError(Throwable throwable) {
        AppLogger.d(throwable, getString(R.string.error_unhandled));
    }

    @Override
    public void showReserveView(int numReservations) {
        mActivityDetailBinding.npVacancyClaim.setVisibility(View.VISIBLE);
        mActivityDetailBinding.npVacancyClaim.setValue(numReservations);
        mActivityDetailBinding.tvVacancySpots.setVisibility(View.VISIBLE);
        mActivityDetailBinding.btnVacancyClaim.setVisibility(View.VISIBLE);

        mActivityDetailBinding.tvVacancyRelease.setVisibility(View.GONE);
        mActivityDetailBinding.btnVacancyRelease.setVisibility(View.GONE);

        mActivityDetailBinding.executePendingBindings();
    }

    @Override
    public void showReleaseView() {
        mActivityDetailBinding.npVacancyClaim.setVisibility(View.GONE);
        mActivityDetailBinding.tvVacancySpots.setVisibility(View.GONE);
        mActivityDetailBinding.btnVacancyClaim.setVisibility(View.GONE);

        mActivityDetailBinding.tvVacancyRelease.setVisibility(View.VISIBLE);
        mActivityDetailBinding.btnVacancyRelease.setVisibility(View.VISIBLE);

        mActivityDetailBinding.executePendingBindings();
    }

    @Override
    public void onCapacityError() {
        Snackbar.make(mActivityDetailBinding.activityMain, "Not enough capacity", Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onReservationError() {
        Snackbar.make(mActivityDetailBinding.activityMain, "You must release your other reservations first", Snackbar.LENGTH_SHORT)
                .show();
    }
}

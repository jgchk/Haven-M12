package com.jgchk.haven.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jgchk.haven.BR;
import com.jgchk.haven.R;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.databinding.ActivityMainBinding;
import com.jgchk.haven.ui.base.BaseActivity;
import com.jgchk.haven.ui.detail.DetailActivity;
import com.jgchk.haven.ui.login.LoginActivity;
import com.jgchk.haven.ui.main.filter.FilterDialog;
import com.jgchk.haven.ui.main.results.ResultsAdapter;
import com.jgchk.haven.utils.AppLogger;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements MainNavigator, HasSupportFragmentInjector {

    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 0;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    ResultsAdapter mResultsAdapter;
    @Inject
    MainViewModel mMainViewModel;
    private ActivityMainBinding mActivityMainBinding;

    private GoogleMap mGoogleMap;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        AppLogger.d(throwable, getString(R.string.error_unhandled));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        CustomSearchView searchView = (CustomSearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new CustomSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMainViewModel.setNameFilter(query);
                mMainViewModel.loadShelters();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        switch (item.getItemId()) {
            case R.id.action_filter:
                mMainViewModel.onFilterClick();
                return true;
            case R.id.action_logout:
                mMainViewModel.logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    @Override
    public void openFilterDialog() {
        FilterDialog.newInstance().show(getSupportFragmentManager());
    }

    @Override
    public void openDetailActivity(Shelter shelter) {
        startActivityForResult(DetailActivity.newIntent(this, shelter), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMainViewModel.loadShelters();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        setSupportActionBar(mActivityMainBinding.toolbar);

        setupLocationServices();

        setUpResultsView();
        subscribeToLiveData();
    }

    private void setupLocationServices() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setMessage("Haven needs your location to show the closest shelters to you!")
                    .setNegativeButton("OK", (dialog, which) -> {
                        dialog.dismiss();
                        requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
                    })
                    .create()
                    .show();
        } else {
            requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_COARSE_LOCATION) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission granted
            } else {
                // Permission denied
                // TODO: disable functionality related to location
            }
        }

        setUpMap();
        mMainViewModel.loadShelters();
    }

    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMapAsync(googleMap -> {
                    mGoogleMap = googleMap;

                    if (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                });
    }

    private void setUpResultsView() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityMainBinding.rvResults.setLayoutManager(mLayoutManager);
        mActivityMainBinding.rvResults.setItemAnimator(new DefaultItemAnimator());
        mActivityMainBinding.rvResults.setAdapter(mResultsAdapter);
        mMainViewModel.subscribeToShelterClicks(mResultsAdapter.getPositionClicks());
    }

    private void subscribeToLiveData() {
        mMainViewModel.getShelterData().observe(
                this, shelters -> {
                    mMainViewModel.setShelterDataList(shelters);
                    setMarkers(shelters);
                });
    }

    private void setMarkers(List<Shelter> shelters) {
        mGoogleMap.clear();
        if (shelters == null || shelters.isEmpty()) return;

        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (Shelter shelter : shelters) {
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(shelter.location.getLatitude(), shelter.location.getLongitude()))
                    .title(shelter.name)
                    .snippet(shelter.phone);
            mGoogleMap.addMarker(marker);
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        int padding = 10;
        CameraUpdate cu;
        if (shelters.size() == 1) {
            cu = CameraUpdateFactory.newLatLng(new LatLng(shelters.get(0).location.getLatitude(),
                    shelters.get(0).location.getLongitude()));
        } else {
            cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        }

        mGoogleMap.moveCamera(cu);
        mGoogleMap.animateCamera(cu);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mMainViewModel.setVacanciesFilter();
        mMainViewModel.setRestrictionsFilter();
        mMainViewModel.loadShelters();
    }
}

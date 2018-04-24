package com.jgchk.haven.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.SearchView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.jgchk.haven.BR;
import com.jgchk.haven.R;
import com.jgchk.haven.databinding.ActivityMainBinding;
import com.jgchk.haven.ui.base.BaseActivity;
import com.jgchk.haven.ui.login.LoginActivity;
import com.jgchk.haven.ui.main.filter.FilterDialog;
import com.jgchk.haven.ui.main.results.ResultsAdapter;
import com.jgchk.haven.utils.AppLogger;

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

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMainViewModel.loadShelters();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMainViewModel.setNameFilter(newText);
                return true;
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

        setUpMap();
        setUpResultsView();

        subscribeToLiveData();
    }

    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMapAsync(googleMap -> {
                    mGoogleMap = googleMap;

                    if (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        enableLocation();
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                            // Show explanation asychronously
                            // Try to request permission again once user sees
                            // TODO: remove this from activity code
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage("Haven needs your location to show the closest shelters to you!")
                                    .setNegativeButton("OK", (dialog, which) -> {
                                        dialog.dismiss();
                                        requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
                                    });
                            builder.create().show();
                        } else {
                            // No explanation needed
                            requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
                        }
                    }
                });
    }

    private void setUpResultsView() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityMainBinding.rvResults.setLayoutManager(mLayoutManager);
        mActivityMainBinding.rvResults.setItemAnimator(new DefaultItemAnimator());
        mActivityMainBinding.rvResults.setAdapter(mResultsAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_COARSE_LOCATION) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission granted
                enableLocation();
            } else {
                // Permission denied
                // TODO: disable functionality related to location
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void enableLocation() {
        mGoogleMap.setMyLocationEnabled(true);
    }

    private void subscribeToLiveData() {
        mMainViewModel.getShelterData().observe(
                this, shelters -> {
                    mMainViewModel.setShelterDataList(shelters);
                    AppLogger.d(shelters.toString());
                });
    }

//    private void subscribeToLiveData() {
//        mMainViewModel.getShelterData().
//        mMainViewModel.getShelterData().observe(this, shelters -> {
//            AppLogger.d("UPDATE!");
//            setMapVisibleShelters(shelters);
//            setResultsShelters(shelters);
//        });
//    }

//    private void setMapVisibleShelters(Iterable<Shelter> shelters) {
//        if (shelters != null && mGoogleMap != null) {
//            mGoogleMap.clear();
//            for (Shelter shelter : shelters) {
//                mGoogleMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(shelter.location.getLatitude(), shelter.location.getLongitude()))
//                        .title(shelter.name).snippet(shelter.phone));
//            }
//
//            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(
//                    getResources().getInteger(R.integer.googleMaps_zoom_default)));
//        }
//    }

//    private void setResultsShelters(List<Shelter> shelters) {
//        if (shelters != null) {
//            mResultsAdapter.setItems(mMainViewModel.getViewModelList(shelters));
//        }
//    }
}

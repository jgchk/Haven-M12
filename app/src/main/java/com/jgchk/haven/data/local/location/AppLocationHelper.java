package com.jgchk.haven.data.local.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

import javax.inject.Inject;

import io.reactivex.Single;

public class AppLocationHelper implements LocationHelper {

    private Context mContext;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Inject
    public AppLocationHelper(Context context, FusedLocationProviderClient fusedLocationProviderClient) {
        mContext = context;
        mFusedLocationProviderClient = fusedLocationProviderClient;
    }

    @Override
    public Single<Location> getLastLocation() {
        return Single.create(emitter -> mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onSuccess(task.getResult());
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }
}

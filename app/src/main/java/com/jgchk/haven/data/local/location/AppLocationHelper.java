package com.jgchk.haven.data.local.location;

import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;

import javax.inject.Inject;

import io.reactivex.Single;

public class AppLocationHelper implements LocationHelper {

    @Inject
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    public Single<Location> getLastLocation() {
        return Single.create(emitter -> mFusedLocationClient.getLastLocation().addOnSuccessListener(emitter::onSuccess));
    }
}

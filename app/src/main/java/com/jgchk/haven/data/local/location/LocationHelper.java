package com.jgchk.haven.data.local.location;

import android.location.Location;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface LocationHelper {

    Single<Location> getLastLocation();
}

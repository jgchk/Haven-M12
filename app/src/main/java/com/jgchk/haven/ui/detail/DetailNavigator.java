package com.jgchk.haven.ui.detail;

public interface DetailNavigator {

    void handleError(Throwable throwable);

    void showReserveView(int numReservations);

    void showReleaseView();

    void onCapacityError();

    void onReservationError();
}

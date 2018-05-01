package com.jgchk.haven.ui.main;

import android.content.DialogInterface;

import com.jgchk.haven.data.model.db.Shelter;

public interface MainNavigator extends DialogInterface.OnDismissListener {

    void handleError(Throwable throwable);

    void openLoginActivity();

    void openFilterDialog();

    void openDetailActivity(Shelter shelter);
}

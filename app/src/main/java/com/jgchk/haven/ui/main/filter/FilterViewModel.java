package com.jgchk.haven.ui.main.filter;

import android.widget.CompoundButton;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.ui.base.BaseViewModel;
import com.jgchk.haven.utils.rx.SchedulerProvider;

public class FilterViewModel extends BaseViewModel<FilterNavigator> {

    public FilterViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onMaleCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // TODO: move search state to prefs
        }
    }
}

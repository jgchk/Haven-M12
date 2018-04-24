package com.jgchk.haven.ui.main.results;

import android.databinding.ObservableField;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.ui.base.BaseViewModel;
import com.jgchk.haven.utils.rx.SchedulerProvider;

public class ResultsItemViewModel extends BaseViewModel<ResultsNavigator> {

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableField<String> distance = new ObservableField<>();

    public final ObservableField<Integer> vacancies = new ObservableField<>();

    public ResultsItemViewModel(Shelter shelter, DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        name.set(shelter.name);
        vacancies.set();
    }

    public static ResultsItemViewModel fromShelter(Shelter shelter) {
        // TODO: fix distance and getVacancies
        return new ResultsItemViewModel(shelter.name, "0", 0);
    }
}

package com.jgchk.haven.ui.main.results;

import android.databinding.ObservableField;

import java.util.Locale;

public class ResultsItemViewModel {

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableField<String> distance = new ObservableField<>();

    public final ObservableField<String> vacancies = new ObservableField<>();

    public ResultsItemViewModel(String name, double distance, int vacancies) {
        this.name.set(name);
        if (distance == -1) {
            this.distance.set("?");
        } else {
            this.distance.set(String.format(Locale.getDefault(), "%.1f", distance));
        }
        if (vacancies == -1) {
            this.vacancies.set("?");
        } else {
            this.vacancies.set(String.valueOf(vacancies));
        }
    }
}

package com.jgchk.haven.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.location.Location;

import com.jgchk.haven.R;
import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.data.model.others.Restriction;
import com.jgchk.haven.ui.base.BaseViewModel;
import com.jgchk.haven.utils.AppConstants;
import com.jgchk.haven.utils.AppLogger;
import com.jgchk.haven.utils.rx.SchedulerProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    private final MutableLiveData<List<Shelter>> shelterData = new MutableLiveData<>();

    private final ObservableList<Shelter> shelterDataList = new ObservableArrayList<>();

    private final ObservableField<String> mNameFilter =
            new ObservableField<>(AppConstants.FILTER_NAME_DEFAULT);

    private final ObservableField<Integer> mVacanciesFilter =
            new ObservableField<>(AppConstants.FILTER_VACANCIES_DEFAULT);

    private final ObservableField<Set<Restriction>> mRestrictionsFilter =
            new ObservableField<>(AppConstants.FILTER_RESTRICTIONS_DEFAULT);

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        loadShelters();
    }

    public MutableLiveData<List<Shelter>> getShelterData() {
        return shelterData;
    }

    public ObservableList<Shelter> getShelterDataList() {
        return shelterDataList;
    }

    public void setShelterDataList(List<Shelter> shelterData) {
        AppLogger.d("MAINVIEWMODEL");
        shelterDataList.clear();
        shelterDataList.addAll(shelterData);
    }

    public void setNameFilter(String name) {
        mNameFilter.set(name);
    }

    public void setVacanciesFilter(int vacancies) {
        mVacanciesFilter.set(vacancies);
    }

    public void addRestrictionFilter(Restriction restriction) {
        Set<Restriction> restrictions = mRestrictionsFilter.get();
        if (restrictions == null) {
            restrictions = new HashSet<>();
        }
        restrictions.add(restriction);
        mRestrictionsFilter.set(restrictions);
    }

    public void removeRestrictionFilter(Restriction restriction) {
        Set<Restriction> restrictions = mRestrictionsFilter.get();
        if (restrictions != null) {
            restrictions.remove(restriction);
        }
        mRestrictionsFilter.set(restrictions);
    }

    public void loadShelters() {
        getCompositeDisposable().add(getDataManager()
                .getLastLocation()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .flatMap(location -> {
                    return getDataManager()
                            .getSheltersMatching(mNameFilter.get(), mVacanciesFilter.get(), mRestrictionsFilter.get())
                            .subscribe(new Consumer<List<Shelter>>() {
                                @Override
                                public void accept(List<Shelter> shelters) throws Exception {
                                    for (Shelter shelter : shelters) {
                                        shelter.
                                    }
                                }
                            });
                })
                );
        getCompositeDisposable().add(getDataManager()
                .getSheltersMatching(mNameFilter.get(), mVacanciesFilter.get(), mRestrictionsFilter.get())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(shelters -> {
                    if (shelters != null) {
                        shelterData.setValue(shelters);
                    }
                }, throwable -> getNavigator().handleError(throwable)));
    }

    public void logout() {
        // TODO: test for optimization
        setIsLoading(true);
        getDataManager().setUserAsLoggedOut();
        setIsLoading(false);
        getNavigator().openLoginActivity();
    }

    public void onFilterClick() {
        getNavigator().openFilterDialog();
    }
}

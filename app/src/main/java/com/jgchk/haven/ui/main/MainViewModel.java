package com.jgchk.haven.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.databinding.ObservableMap;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.data.model.others.Restriction;
import com.jgchk.haven.ui.base.BaseViewModel;
import com.jgchk.haven.utils.AppConstants;
import com.jgchk.haven.utils.AppLogger;
import com.jgchk.haven.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    private final MutableLiveData<List<Shelter>> shelterData = new MutableLiveData<>();

    private final ObservableList<Shelter> shelterDataList = new ObservableArrayList<>();

//    private final ObservableMap<Shelter, Integer> shelterDataList = new ObservableArrayMap<>();

    private final ObservableField<String> mNameFilter =
            new ObservableField<>(AppConstants.FILTER_NAME_DEFAULT);

    private final ObservableField<Integer> mVacanciesFilter =
            new ObservableField<>(AppConstants.FILTER_VACANCIES_DEFAULT);

    private final ObservableField<Set<Restriction>> mRestrictionsFilter =
            new ObservableField<>(AppConstants.FILTER_RESTRICTIONS_DEFAULT);

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
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

    public void setVacanciesFilter() {
        mVacanciesFilter.set(getDataManager().getVacanciesQuery());
    }

    public void setRestrictionsFilter() {
        mRestrictionsFilter.set(getDataManager().getRestrictionsQuery());
    }

    public void loadShelters() {
        AppLogger.d("Name: " + mNameFilter.get());
        AppLogger.d("Vacancies: " + mVacanciesFilter.get());
        AppLogger.d("Restrictions: " + mRestrictionsFilter.get());
        getCompositeDisposable().add(getDataManager()
                .getAllShelters()
                .flatMapIterable(shelters -> shelters)
                .filter(shelter -> {
                    shelter.vacancies = getDataManager().getVacancies(shelter).blockingFirst();
                    return shelter.vacancies >= mVacanciesFilter.get();
                })
                .toList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(shelters -> {
                    if (shelters != null) {

                        getCompositeDisposable().add(getDataManager()
                                .getLastLocation()
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(location -> {
                                    for (Shelter shelter : shelters) {
                                        shelter.distance = shelter.getDistanceInMiles(location);
                                    }
                                    shelterData.setValue(shelters);
                                }, throwable -> {
                                    if (throwable instanceof SecurityException) {
                                        for (Shelter shelter : shelters) {
                                            shelter.distance = -1;
                                        }
                                        shelterData.setValue(shelters);
                                    } else {
                                        getNavigator().handleError(throwable);
                                    }
                                }));
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

    public void subscribeToShelterClicks(Observable<Integer> clickObservable) {
        getCompositeDisposable().add(clickObservable
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(integer -> getNavigator().openDetailActivity(shelterDataList.get(integer))));
    }
}

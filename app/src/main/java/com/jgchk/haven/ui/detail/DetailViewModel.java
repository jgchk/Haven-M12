package com.jgchk.haven.ui.detail;

import android.database.sqlite.SQLiteConstraintException;
import android.databinding.ObservableField;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.ui.base.BaseViewModel;
import com.jgchk.haven.utils.rx.SchedulerProvider;
import com.shawnlin.numberpicker.NumberPicker;

public class DetailViewModel extends BaseViewModel<DetailNavigator> {

    private ObservableField<Shelter> mShelter = new ObservableField<>();

//    private ObservableField<Integer> mVacancies = new ObservableField<>();

    private ObservableField<Integer> mNumReservations = new ObservableField<>();

    public DetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onShelterLoaded(Shelter shelter) {
        getCompositeDisposable().add(getDataManager()
                .getVacancies(shelter)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(integer -> {
                    shelter.vacancies = integer;
                    mShelter.set(shelter);
                }, throwable -> getNavigator().handleError(throwable)));

        getCompositeDisposable().add(getDataManager()
                .getReservations(shelter, getDataManager().getCurrentUser())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(integer -> {
                            mNumReservations.set(integer);
                            getNavigator().showReleaseView();
                        },
                        throwable -> getNavigator().handleError(throwable)));

//        getCompositeDisposable().add(getDataManager()
//                .getVacancies(shelter)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(vacancies -> mVacancies.set(vacancies),
//                        throwable -> getNavigator().handleError(throwable)));
    }

    public ObservableField<Shelter> getShelter() {
        return mShelter;
    }

//    public ObservableField<Integer> getVacancies() {
//        return mVacancies;
//    }

    public void setNumReservations(int numReservations) {
        this.mNumReservations.set(numReservations);
    }

    public ObservableField<Integer> getNumReservations() {
        return mNumReservations;
    }

    public NumberPicker.OnValueChangeListener getReservationsValueChangeListener() {
        return (picker, oldVal, newVal) -> mNumReservations.set(newVal);
    }

    public void onReserveClick() {
        getCompositeDisposable().add(getDataManager()
                .getVacancies(mShelter.get())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(vacancies -> {
                    if (mNumReservations.get() > vacancies) {
                        getNavigator().onCapacityError();
                        return;
                    }

                    getCompositeDisposable().add(getDataManager()
                            .reserve(mShelter.get(), getDataManager().getCurrentUser(), mNumReservations.get())
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(successful -> {
                                        if (successful) {
                                            getNavigator().showReleaseView();
                                        }
                                    }, throwable -> {
                                        if (throwable instanceof SQLiteConstraintException) {
                                            getNavigator().onReservationError();
                                        } else {
                                            getNavigator().handleError(throwable);
                                        }
                                    }));
                }));
    }

    public void onReleaseClick() {
        getCompositeDisposable().add(getDataManager()
                .release(mShelter.get(), getDataManager().getCurrentUser())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(successful -> {
                    if (successful) {
                        getNavigator().showReserveView(mNumReservations.get());
                    }
                }, throwable -> getNavigator().handleError(throwable)));
    }
}

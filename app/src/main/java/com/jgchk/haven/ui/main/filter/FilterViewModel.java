package com.jgchk.haven.ui.main.filter;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.data.model.others.Restriction;
import com.jgchk.haven.ui.base.BaseViewModel;
import com.jgchk.haven.utils.rx.SchedulerProvider;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Set;

public class FilterViewModel extends BaseViewModel<FilterNavigator> {

    private int vacanciesRestriction;

    private boolean maleRestriction = false;

    private boolean femaleRestriction = false;

    private boolean childrenRestriction = false;

    private boolean youngAdultsRestriction = false;

    private boolean familiesRestriction = false;

    private boolean familiesWithNewbornsRestriction = false;

    private boolean veteransRestriction = false;

    public FilterViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        vacanciesRestriction = getDataManager().getVacanciesQuery();

        Set<Restriction> restrictionsQuery = getDataManager().getRestrictionsQuery();
        for (Restriction restriction : restrictionsQuery) {
            switch (restriction) {
                case MEN:
                    maleRestriction = true;
                    break;
                case WOMEN:
                    femaleRestriction = true;
                    break;
                case CHILDREN:
                    childrenRestriction = true;
                    break;
                case YOUNG_ADULTS:
                    youngAdultsRestriction = true;
                    break;
                case FAMILIES:
                    familiesRestriction = true;
                    break;
                case FAMILIES_WITH_CHILDREN:
                    familiesWithNewbornsRestriction = true;
                    break;
                case VETERANS:
                    veteransRestriction = true;
                    break;
            }
        }
    }

    public int getVacanciesRestriction() {
        return vacanciesRestriction;
    }

    public boolean isMaleRestriction() {
        return maleRestriction;
    }

    public boolean isFemaleRestriction() {
        return femaleRestriction;
    }

    public boolean isChildrenRestriction() {
        return childrenRestriction;
    }

    public boolean isYoungAdultsRestriction() {
        return youngAdultsRestriction;
    }

    public boolean isFamiliesRestriction() {
        return familiesRestriction;
    }

    public boolean isFamiliesWithNewbornsRestriction() {
        return familiesWithNewbornsRestriction;
    }

    public boolean isVeteransRestriction() {
        return veteransRestriction;
    }

    public NumberPicker.OnValueChangeListener getVacanciesValueChangeListener() {
        return (picker, oldVal, newVal) -> {
            vacanciesRestriction = newVal;
            getDataManager().setVacanciesQuery(newVal);
        };
    }

    public void onMaleCheckedChanged(boolean isChecked) {
        maleRestriction = isChecked;
        if (isChecked) {
            getDataManager().addRestrictionQuery(Restriction.MEN);
        } else {
            getDataManager().removeRestrictionQuery(Restriction.MEN);
        }
    }

    public void onFemaleCheckedChanged(boolean isChecked) {
        femaleRestriction = isChecked;
        if (isChecked) {
            getDataManager().addRestrictionQuery(Restriction.WOMEN);
        } else {
            getDataManager().removeRestrictionQuery(Restriction.WOMEN);
        }
    }

    public void onChildrenCheckedChanged(boolean isChecked) {
        childrenRestriction = isChecked;
        if (isChecked) {
            getDataManager().addRestrictionQuery(Restriction.CHILDREN);
        } else {
            getDataManager().removeRestrictionQuery(Restriction.CHILDREN);
        }
    }

    public void onYoungAdultsCheckedChanged(boolean isChecked) {
        youngAdultsRestriction = isChecked;
        if (isChecked) {
            getDataManager().addRestrictionQuery(Restriction.YOUNG_ADULTS);
        } else {
            getDataManager().removeRestrictionQuery(Restriction.YOUNG_ADULTS);
        }
    }

    public void onFamiliesCheckedChanged(boolean isChecked) {
        familiesRestriction = isChecked;
        if (isChecked) {
            getDataManager().addRestrictionQuery(Restriction.FAMILIES);
        } else {
            getDataManager().removeRestrictionQuery(Restriction.FAMILIES);
        }
    }

    public void onFamiliesWithNewbornsCheckedChanged(boolean isChecked) {
        familiesWithNewbornsRestriction = isChecked;
        if (isChecked) {
            getDataManager().addRestrictionQuery(Restriction.FAMILIES_WITH_CHILDREN);
        } else {
            getDataManager().removeRestrictionQuery(Restriction.FAMILIES_WITH_CHILDREN);
        }
    }

    public void onVeteransCheckedChanged(boolean isChecked) {
        veteransRestriction = isChecked;
        if (isChecked) {
            getDataManager().addRestrictionQuery(Restriction.VETERANS);
        } else {
            getDataManager().removeRestrictionQuery(Restriction.VETERANS);
        }
    }
}

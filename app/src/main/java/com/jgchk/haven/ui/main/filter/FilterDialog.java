package com.jgchk.haven.ui.main.filter;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgchk.haven.R;
import com.jgchk.haven.databinding.DialogFilterBinding;
import com.jgchk.haven.ui.base.BaseDialog;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class FilterDialog extends BaseDialog implements FilterNavigator {

    private static final String TAG = FilterDialog.class.getSimpleName();

    @Inject
    FilterViewModel mFilterViewModel;
    private DialogFilterBinding mDialogFilterBinding;

    public static FilterDialog newInstance() {
        FilterDialog fragment = new FilterDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDialogFilterBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_filter, container, false);
        View view = mDialogFilterBinding.getRoot();

        AndroidSupportInjection.inject(this);

        mDialogFilterBinding.setViewModel(mFilterViewModel);

        mFilterViewModel.setNavigator(this);

        setUp();

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getBaseActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    private void setUp() {
        mDialogFilterBinding.npVacancies.setValue(mFilterViewModel.getVacanciesRestriction());
        mDialogFilterBinding.cbMale.setChecked(mFilterViewModel.isMaleRestriction());
        mDialogFilterBinding.cbFemale.setChecked(mFilterViewModel.isFemaleRestriction());
        mDialogFilterBinding.cbChildren.setChecked(mFilterViewModel.isChildrenRestriction());
        mDialogFilterBinding.cbYoungAdults.setChecked(mFilterViewModel.isYoungAdultsRestriction());
        mDialogFilterBinding.cbFamilies.setChecked(mFilterViewModel.isFamiliesRestriction());
        mDialogFilterBinding.cbFamiliesWithNewborns.setChecked(mFilterViewModel.isFamiliesWithNewbornsRestriction());
        mDialogFilterBinding.cbVeterans.setChecked(mFilterViewModel.isVeteransRestriction());
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }
}

package com.jgchk.haven.ui.main.filter;

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

    public static FilterDialog newInstance() {
        FilterDialog fragment = new FilterDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogFilterBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_filter, container, false);
        View view = binding.getRoot();

        AndroidSupportInjection.inject(this);

        binding.setViewModel(mFilterViewModel);

        mFilterViewModel.setNavigator(this);

        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }
}

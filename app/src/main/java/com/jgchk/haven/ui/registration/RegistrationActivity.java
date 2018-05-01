package com.jgchk.haven.ui.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.jgchk.haven.BR;
import com.jgchk.haven.R;
import com.jgchk.haven.data.model.db.User;
import com.jgchk.haven.databinding.ActivityRegistrationBinding;
import com.jgchk.haven.ui.base.BaseActivity;
import com.jgchk.haven.ui.login.LoginActivity;
import com.jgchk.haven.ui.main.MainActivity;
import com.jgchk.haven.utils.AppConstants;
import com.jgchk.haven.utils.AppLogger;
import com.jgchk.haven.utils.CommonUtils;

import javax.inject.Inject;

public class RegistrationActivity
        extends BaseActivity<ActivityRegistrationBinding, RegistrationViewModel>
        implements RegistrationNavigator {

    @Inject
    RegistrationViewModel mRegistrationViewModel;
    private ActivityRegistrationBinding mActivityRegistrationBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    public RegistrationViewModel getViewModel() {
        return mRegistrationViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        AppLogger.d(throwable, getString(R.string.error_unhandled));
    }

    @Override
    public void register() {
        String email = mActivityRegistrationBinding.etEmail.getText().toString();
        String password = mActivityRegistrationBinding.etPassword.getText().toString();
        User.AccountType accountType = (User.AccountType) mActivityRegistrationBinding.spnAccountType.getSelectedItem();
        if (CommonUtils.isEmailAndPasswordValid(email, password)) {
            hideKeyboard();
            mRegistrationViewModel.register(email, password, accountType);
        } else {
            onFailedRegistration();
        }
    }

    @Override
    public void onFailedRegistration() {
        mActivityRegistrationBinding.etEmail.setError(
                getString(R.string.registration_error_invalidRegistration));
        mActivityRegistrationBinding.etEmail.requestFocus();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegistrationBinding = getViewDataBinding();
        mRegistrationViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        ArrayAdapter<User.AccountType> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                AppConstants.ACCOUNT_TYPES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mActivityRegistrationBinding.spnAccountType.setAdapter(adapter);
    }
}

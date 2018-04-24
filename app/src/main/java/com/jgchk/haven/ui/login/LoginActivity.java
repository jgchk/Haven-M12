package com.jgchk.haven.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jgchk.haven.BR;
import com.jgchk.haven.R;
import com.jgchk.haven.databinding.ActivityLoginBinding;
import com.jgchk.haven.ui.base.BaseActivity;
import com.jgchk.haven.ui.main.MainActivity;
import com.jgchk.haven.ui.registration.RegistrationActivity;
import com.jgchk.haven.utils.AppLogger;
import com.jgchk.haven.utils.CommonUtils;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel>
        implements LoginNavigator {

    @Inject LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mActivityLoginBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        return mLoginViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        AppLogger.d(throwable, getString(R.string.error_unhandled));
    }

    @Override
    public void login() {
        String email = mActivityLoginBinding.etEmail.getText().toString();
        String password = mActivityLoginBinding.etPassword.getText().toString();
        if (CommonUtils.isEmailAndPasswordValid(email, password)) {
            hideKeyboard();
            mLoginViewModel.login(email, password);
        } else {
            onFailedLogin();
        }
    }

    @Override
    public void onFailedLogin() {
        mActivityLoginBinding.etEmail.setError(getString(R.string.login_error_invalidLogin));
        mActivityLoginBinding.etEmail.requestFocus();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openRegistrationActivity() {
        Intent intent = RegistrationActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mLoginViewModel.setNavigator(this);
    }
}

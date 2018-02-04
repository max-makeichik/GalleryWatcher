package com.mm.gallerywatcher.presentation.ui.activity;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.mm.gallerywatcher.R;

import butterknife.BindView;

/**
 * Created by Maksim Makeychik on 04.02.2018.
 */

public abstract class BaseActivity extends MvpAppCompatActivity {

    @BindView(R.id.root_layout)
    ViewGroup rootLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    View progressBar;

    protected void setToolbar(@StringRes int stringResId) {
        setSupportActionBar(toolbar);
        toolbar.setTitle(stringResId);
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    public void showError(@StringRes int errorMessageStringResId) {
        showError(getString(errorMessageStringResId));
    }

    public void showError(String errorMessage) {
        Snackbar.make(rootLayout, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    public void hideError() {
    }


}

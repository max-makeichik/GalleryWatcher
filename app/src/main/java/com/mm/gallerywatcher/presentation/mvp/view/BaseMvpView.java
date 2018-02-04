package com.mm.gallerywatcher.presentation.mvp.view;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Interface representing a View that will use to load data.
 */
public interface BaseMvpView extends MvpView {

    /**
     * Show a view with a progress bar indicating a pic_spinner_orange process.
     */
    void showLoading();

    /**
     * Hide a pic_spinner_orange view.
     */
    void hideLoading();

    /**
     * Error view
     *
     * @param errorMessageStringResId - error message string id
     */
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showError(@StringRes int errorMessageStringResId);

    /**
     * Error view
     *
     * @param errorMessage - error message
     */
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showError(String errorMessage);

    /**
     * Error view hide.
     */
    void hideError();
}

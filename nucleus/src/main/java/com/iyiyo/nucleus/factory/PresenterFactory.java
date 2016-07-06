package com.iyiyo.nucleus.factory;


import com.iyiyo.nucleus.presenter.Presenter;

public interface PresenterFactory<P extends Presenter> {
    P createPresenter();
}

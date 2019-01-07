package com.zhaojy.onlineanswer.mvp.presenter;

import com.zhaojy.onlineanswer.mvp.contract.MainActivityContract;
import com.zhaojy.onlineanswer.mvp.model.MainActivityModel;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public class MainActivityPresenter implements MainActivityContract.Presenter {
    private MainActivityContract.View view;
    private MainActivityContract.Model model;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void initData() {
        model = new MainActivityModel();

    }

    @Override
    public void process() {

    }

}

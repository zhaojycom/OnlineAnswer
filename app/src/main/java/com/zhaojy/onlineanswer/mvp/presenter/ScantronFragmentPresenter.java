package com.zhaojy.onlineanswer.mvp.presenter;

import com.zhaojy.onlineanswer.mvp.contract.ScantronFragmentContract;

/**
 * @author: zhaojy
 * @data:On 2018/12/28.
 */
public class ScantronFragmentPresenter implements ScantronFragmentContract.Presenter {
    private ScantronFragmentContract.View view;
    private ScantronFragmentContract.Model model;

    public ScantronFragmentPresenter(ScantronFragmentContract.View view) {
        this.view = view;
        this.initData();
    }

    @Override
    public void initData() {

    }

    @Override
    public void process() {

    }
}

package com.zhaojy.onlineanswer.mvp.model;

import com.zhaojy.onlineanswer.mvp.contract.ScantronFragmentContract;

/**
 * @author: zhaojy
 * @data:On 2018/12/28.
 */
public class ScantronFragmentModel implements ScantronFragmentContract.Model {
    private ScantronFragmentContract.Presenter presenter;

    public ScantronFragmentModel(ScantronFragmentContract.Presenter presenter) {
        this.presenter = presenter;

    }
}

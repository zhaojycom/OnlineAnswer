package com.zhaojy.onlineanswer.mvp.presenter;

import com.zhaojy.onlineanswer.bean.SearchHistory;
import com.zhaojy.onlineanswer.mvp.contract.SortSearchActivityContract;
import com.zhaojy.onlineanswer.mvp.model.SortSearchActivityModel;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/29.
 */
public class SortSearchActivityPresenter implements SortSearchActivityContract.Presenter {
    private SortSearchActivityContract.View view;
    private SortSearchActivityContract.Model model;

    public SortSearchActivityPresenter(SortSearchActivityContract.View view) {
        this.view = view;
        this.initData();
    }

    @Override
    public void initData() {
        model = new SortSearchActivityModel(this);
    }

    @Override
    public void process() {

    }

    @Override
    public void saveSearchHistory(SearchHistory searchHistory) {
        model.saveSearchHistory(searchHistory);
    }

    @Override
    public List<SearchHistory> readSearchHistory() {
        return model.readSearchHistory();
    }

    @Override
    public void clearRecords() {
        model.clearRecords();
    }
}

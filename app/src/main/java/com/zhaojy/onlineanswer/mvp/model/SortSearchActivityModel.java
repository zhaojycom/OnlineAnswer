package com.zhaojy.onlineanswer.mvp.model;

import com.zhaojy.onlineanswer.bean.SearchHistory;
import com.zhaojy.onlineanswer.data.objectbox.SearchHistoryObjectBox;
import com.zhaojy.onlineanswer.mvp.contract.SortSearchActivityContract;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/29.
 */
public class SortSearchActivityModel implements SortSearchActivityContract.Model {
    private SortSearchActivityContract.Presenter presenter;
    private SearchHistoryObjectBox searchHistoryObjectBox;

    public SortSearchActivityModel(SortSearchActivityContract.Presenter presenter) {
        this.presenter = presenter;
        searchHistoryObjectBox = SearchHistoryObjectBox.getInstance();
    }

    @Override
    public void saveSearchHistory(SearchHistory searchHistory) {
        searchHistoryObjectBox.getSearchHistoryBox().put(searchHistory);

    }

    @Override
    public List<SearchHistory> readSearchHistory() {
        List<SearchHistory> searchHistories = searchHistoryObjectBox
                .getSearchHistoryBox().getAll();

        return searchHistories;
    }

    @Override
    public void clearRecords() {
        searchHistoryObjectBox.getSearchHistoryBox().removeAll();
    }
}

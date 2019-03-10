package com.zhaojy.onlineanswer.mvp.contract;

import com.zhaojy.onlineanswer.bean.SearchHistory;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/29.
 */
public interface SortSearchActivityContract {
    interface View {

        void getIntentInfo();

        void setSearchHistory();

        void saveSearchHistory(String keyword);

        void readSearchHistory();

        void clearRecords();

        void setSearchResult();

        void excuteSearch(String keyword);
    }

    interface Presenter {
        void initData();

        void process();

        void saveSearchHistory(SearchHistory searchHistory);

        List<SearchHistory> readSearchHistory();

        void clearRecords();
    }

    interface Model {
        void saveSearchHistory(SearchHistory searchHistory);

        List<SearchHistory> readSearchHistory();

        void clearRecords();
    }
}

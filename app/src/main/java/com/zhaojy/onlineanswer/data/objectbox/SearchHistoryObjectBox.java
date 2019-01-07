package com.zhaojy.onlineanswer.data.objectbox;

import com.zhaojy.onlineanswer.MyApplication;
import com.zhaojy.onlineanswer.bean.SearchHistory;
import com.zhaojy.onlineanswer.helper.IExitLoginObserver;

import io.objectbox.Box;

import static com.mob.tools.utils.DeviceHelper.getApplication;

/**
 * @author: zhaojy
 * @data:On 2018/12/30.
 */
public class SearchHistoryObjectBox implements IExitLoginObserver {
    private static SearchHistoryObjectBox searchHistoryObjectBox;
    private Box<SearchHistory> searchHistoryBox;

    public static SearchHistoryObjectBox getInstance() {
        if (searchHistoryObjectBox == null) {
            synchronized (SearchHistoryObjectBox.class) {
                if (searchHistoryObjectBox == null) {
                    searchHistoryObjectBox = new SearchHistoryObjectBox();
                }
            }
        }

        return searchHistoryObjectBox;
    }

    private SearchHistoryObjectBox() {
        searchHistoryBox = ((MyApplication) getApplication()).getBoxStore()
                .boxFor(SearchHistory.class);
    }

    public Box<SearchHistory> getSearchHistoryBox() {
        return searchHistoryBox;
    }

    @Override
    public void exitLoginUpdate() {
        getSearchHistoryBox().removeAll();
    }

}

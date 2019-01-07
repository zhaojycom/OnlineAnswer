package com.zhaojy.onlineanswer.mvp.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.SearchHistory;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/29.
 */
public class SearchHistoryAdapter extends BaseQuickAdapter<SearchHistory, BaseViewHolder> {
    private final static String TAG = SearchHistoryAdapter.class.getSimpleName();
    private Context context;

    public SearchHistoryAdapter(List<SearchHistory> data, Context context) {
        super(R.layout.search_history_item, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, SearchHistory history) {
        helper.setText(R.id.keyword, history.getKeyword());
    }

}

package com.zhaojy.onlineanswer.mvp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.MyRecyclerBean;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/20.
 */
public class MyRecyclerAdapter extends BaseQuickAdapter<MyRecyclerBean, BaseViewHolder> {
    private final static String TAG = MoreSortsAdapter.class.getSimpleName();

    public MyRecyclerAdapter(List<MyRecyclerBean> data) {
        super(R.layout.my_recycler_item, data);

    }

    @Override
    protected void convert(final BaseViewHolder helper, MyRecyclerBean item) {
        helper.setImageResource(R.id.icon, item.getIcon());
        helper.setText(R.id.title, item.getTitle());
    }

}
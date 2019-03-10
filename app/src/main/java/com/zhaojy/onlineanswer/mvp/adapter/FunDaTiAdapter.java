package com.zhaojy.onlineanswer.mvp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.FunDaTiBean;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2019/2/25.
 */
public class FunDaTiAdapter extends BaseQuickAdapter<FunDaTiBean, BaseViewHolder> {
    private final static String TAG = FunDaTiAdapter.class.getSimpleName();

    public FunDaTiAdapter(List<FunDaTiBean> data) {
        super(R.layout.fundati_item, data);

    }

    @Override
    protected void convert(final BaseViewHolder helper, FunDaTiBean item) {
        helper.setImageResource(R.id.icon, item.getIcon());
        helper.setText(R.id.title, item.getTitle());
        helper.setImageResource(R.id.icon, item.getIcon());
    }

}

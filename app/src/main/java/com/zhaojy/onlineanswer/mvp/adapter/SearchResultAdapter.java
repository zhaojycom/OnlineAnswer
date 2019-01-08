package com.zhaojy.onlineanswer.mvp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.constant.SiteInfo;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2019/1/7.
 */
public class SearchResultAdapter extends BaseQuickAdapter<QuestionSort, BaseViewHolder> {
    private final static String TAG = SearchHistoryAdapter.class.getSimpleName();
    private Context context;

    public SearchResultAdapter(List<QuestionSort> data, Context context) {
        super(R.layout.search_result_item, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, QuestionSort questionSort) {
        helper.setText(R.id.sortName, questionSort.getName());

        if (questionSort.getIconUrl() != null) {
            ImageView sortIcon = helper.getView(R.id.icon);
            final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>
                    (sortIcon);
            Glide.with(context)
                    .load(SiteInfo.HOST_URL + questionSort.getIconUrl())
                    .asBitmap()
                    .placeholder(R.mipmap.icon)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            imageViewWeakReference.get().setImageBitmap(resource);
                        }
                    });
        }
    }

}
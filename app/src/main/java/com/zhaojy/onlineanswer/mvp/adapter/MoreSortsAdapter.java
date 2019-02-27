package com.zhaojy.onlineanswer.mvp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.User;
import com.zhaojy.onlineanswer.constant.SiteInfo;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/10/18.
 */

public class MoreSortsAdapter extends BaseQuickAdapter<QuestionSort, BaseViewHolder> {
    private final static String TAG = MoreSortsAdapter.class.getSimpleName();
    private Context context;

    public MoreSortsAdapter(List<QuestionSort> data, Context context) {
        super(R.layout.more_sort_item, data);
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
        //右侧
        View right = helper.getView(R.id.right);
        if (User.getInstance().getPhone() == null) {
            //没有登录用户
            right.setVisibility(View.GONE);
        } else {
            right.setVisibility(View.VISIBLE);
        }

        //设置显示的操作图标（删除或者添加）
        View delete = helper.getView(R.id.delete);
        View add = helper.getView(R.id.add);
        if (questionSort.isAdded()) {
            delete.setVisibility(View.VISIBLE);
            add.setVisibility(View.GONE);
        } else {
            delete.setVisibility(View.GONE);
            add.setVisibility(View.VISIBLE);
        }
        //设置监听器
        helper.addOnClickListener(R.id.delete)
                .addOnClickListener(R.id.add);

    }

}
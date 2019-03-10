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
import com.zhaojy.onlineanswer.bean.ErrorsSort;
import com.zhaojy.onlineanswer.constant.SiteInfo;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2019/1/26.
 */
public class MyErrorsAdapter extends BaseQuickAdapter<ErrorsSort, BaseViewHolder> {
    private final static String TAG = MyErrorsAdapter.class.getSimpleName();
    private Context context;

    public MyErrorsAdapter(List<ErrorsSort> data, Context context) {
        super(R.layout.my_errors_item, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, ErrorsSort errorsSort) {
        helper.setText(R.id.sortName, errorsSort.getName());
        helper.setText(R.id.sum, "共" + errorsSort.getSum() + "道错题");
        if (errorsSort.getIconUrl() != null) {
            ImageView sortIcon = helper.getView(R.id.icon);
            final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>
                    (sortIcon);
            Glide.with(context)
                    .load(SiteInfo.HOST_URL + errorsSort.getIconUrl())
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

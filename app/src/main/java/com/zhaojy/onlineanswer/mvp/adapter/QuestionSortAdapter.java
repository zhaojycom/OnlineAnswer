package com.zhaojy.onlineanswer.mvp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.constant.SiteInfo;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhaojy
 * @data:On 2018/10/14.
 */

public class QuestionSortAdapter extends BaseAdapter {
    private Context context;
    private List<QuestionSort> data;
    private GridView gridView;
    private Map<Integer, View> viewMap = new HashMap<>();

    public QuestionSortAdapter(Context context, List<QuestionSort> data, GridView gridView) {
        this.context = context;
        this.data = data;
        this.gridView = gridView;
    }

    private class Holder {
        ImageView icon;
        TextView name;

        public Holder(View view) {
            icon = view.findViewById(R.id.icon);
            name = view.findViewById(R.id.name);
        }

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;
        QuestionSort questionSort = data.get(position);

        if (!viewMap.containsKey(position) || viewMap.get(position) == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.question_sort_item, viewGroup, false);
            holder = new Holder(view);
            final WeakReference<ImageView> imageViewReference = new WeakReference<>(holder.icon);
            holder.name.setText(questionSort.getName());

            if (questionSort.getIconUrl() != null) {
                Glide.with(context)
                        .load(SiteInfo.HOST_URL + questionSort.getIconUrl())
                        .asBitmap()
                        .placeholder(R.mipmap.moresort)
                        .centerCrop()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                imageViewReference.get().setImageBitmap(resource);
                            }
                        });
            }


            view.setTag(holder);
            viewMap.put(position, view);
        } else {
            view = viewMap.get(position);
            holder = (Holder) view.getTag();
        }

        //清理viewMap
        clearViewMap(view);
        return view;
    }

    /**
     * 清理viewMap
     */
    private void clearViewMap(View view) {
        if (viewMap.size() > 20) {
            synchronized (view) {
                for (int i = 1; i < gridView.getFirstVisiblePosition() - 3; i++) {
                    viewMap.remove(i);
                }
                for (int i = gridView.getLastVisiblePosition() + 3; i < getCount(); i++) {
                    viewMap.remove(i);
                }
            }
        }
    }

}

package com.zhaojy.onlineanswer.mvp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhaojy
 * @data:On 2018/10/20.
 */

public class ScantronAdapter extends BaseAdapter {
    private Context context;
    private List<Question> data;
    private GridView gridView;
    private Map<Integer, View> viewMap = new HashMap<>();

    public View getItemView(int pos) {
        return viewMap.get(pos);
    }

    public ScantronAdapter(Context context, List<Question> data, GridView gridView) {
        this.context = context;
        this.data = data;
        this.gridView = gridView;
    }

    private class Holder {
        TextView questionNum;
        RelativeLayout questionNumBox;

        public Holder(View view) {
            questionNum = view.findViewById(R.id.questionNum);
            questionNumBox = view.findViewById(R.id.questionNumBox);
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
        ScantronAdapter.Holder holder;
        Question question = data.get(position);

        if (!viewMap.containsKey(position) || viewMap.get(position) == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.scantron_item, viewGroup, false);
            holder = new ScantronAdapter.Holder(view);

            holder.questionNum.setText(String.valueOf(position + 1));
            if (question.getMyOption() != null) {
                //如果用户选择了答案
                holder.questionNum.setTextColor(context.getResources().getColor(R.color.theme));
                Drawable drawable = context.getDrawable(R.drawable.scantron_question_selectedshape);
                holder.questionNumBox.setBackground(drawable);
            } else {

            }

            view.setTag(holder);
            viewMap.put(position, view);
        } else {
            view = viewMap.get(position);
            holder = (ScantronAdapter.Holder) view.getTag();
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


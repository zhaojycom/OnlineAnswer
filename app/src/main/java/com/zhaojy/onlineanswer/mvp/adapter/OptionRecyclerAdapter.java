package com.zhaojy.onlineanswer.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.Question;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/10/18.
 */

public class OptionRecyclerAdapter extends RecyclerView.Adapter<OptionRecyclerAdapter.ViewHolder> implements
        View.OnClickListener {
    private List<Question.Options> data;
    private Context context;

    private OptionRecyclerAdapter.OnItemClickListener mOnItemClickListener = null;

    public OptionRecyclerAdapter(List<Question.Options> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public OptionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.option_item, parent,
                false);
        OptionRecyclerAdapter.ViewHolder viewHolder = new OptionRecyclerAdapter.ViewHolder(view);
        //设置监听事件
        view.setOnClickListener(this);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(OptionRecyclerAdapter.ViewHolder holder, int position) {
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);

        Question.Options option = data.get(position);
        holder.optionName.setText(option.getOptionName());
        holder.optionContent.setText(option.getOptionContent());
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    /**
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView optionName;
        TextView optionContent;

        private ViewHolder(View view) {
            super(view);
            optionName = view.findViewById(R.id.optionName);
            optionContent = view.findViewById(R.id.optionContent);
        }
    }

    public void setOnItemClickListener(OptionRecyclerAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * item事件监听接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}

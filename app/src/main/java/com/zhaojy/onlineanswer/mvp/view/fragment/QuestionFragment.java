package com.zhaojy.onlineanswer.mvp.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.mvp.adapter.OptionRecyclerAdapter;
import com.zhaojy.onlineanswer.mvp.contract.QuestionFragmentContract;
import com.zhaojy.onlineanswer.mvp.presenter.QuestionFragmentPresenter;
import com.zhaojy.onlineanswer.mvp.view.activity.DaTiActivity;

import butterknife.BindView;

/**
 * @author: zhaojy
 * @data:On 2018/10/20.
 */

public class QuestionFragment extends BaseFragment implements DaTiActivity.GradingPapers
        , QuestionFragmentContract.View {

    /**
     * 题干
     */
    @BindView(R.id.questionStem)
    public TextView questionStem;
    @BindView(R.id.optionRecycler)
    public RecyclerView optionRecycler;
    private OptionRecyclerAdapter optionRecyclerAdapter;
    private Question question;
    /**
     * 题目位置索引
     */
    private int questionPos;

    /**
     * 可以答题，提交完之后为false
     */
    private boolean canAnswer = true;
    @BindView(R.id.questionResult)
    public View questionResult;
    @BindView(R.id.correctAnswer)
    public TextView correctAnswer;
    @BindView(R.id.myAnswer)
    public TextView myAnswer;

    private QuestionFragmentContract.Presenter presenter;


    public void setQuestionPos(int questionPos) {
        this.questionPos = questionPos;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.question_layout;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        presenter = new QuestionFragmentPresenter(this);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //初始化页面
        initPage();
        //设置题目选项recycler
        setOptionRecycler();
    }

    @Override
    protected void process(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    /**
     * 初始化页面
     */
    @Override
    public void initPage() {
        questionStem.setText((questionPos + 1) + "、" + question.getQuestion());
    }

    /**
     * 设置题目选项recycler
     */
    @Override
    public void setOptionRecycler() {
        optionRecyclerAdapter = new OptionRecyclerAdapter(question.getOptions(), getActivity());
        optionRecyclerAdapter.setOnItemClickListener(new OptionRecyclerAdapter.OnItemClickListener() {

            /**
             * 点击事件
             * @param view
             * @param position
             */
            @Override
            public void onItemClick(View view, int position) {
                if (!canAnswer) {
                    return;
                }
                for (int i = 0; i < optionRecyclerAdapter.getItemCount(); i++) {
                    View itemView = optionRecycler.getLayoutManager()
                            .findViewByPosition(i);
                    TextView optionName = itemView.findViewById(R.id.optionName);
                    RelativeLayout optionNameBox = itemView.findViewById(R.id.optionNameBox);
                    TextView optionContent = itemView.findViewById(R.id.optionContent);

                    if (i == position) {
                        optionName.setTextColor(getActivity().getResources().getColor(R.color.theme));
                        Drawable drawable = getActivity().getResources()
                                .getDrawable(R.drawable.option_item_selected_shape);
                        optionNameBox.setBackground(drawable);
                        optionContent.setTextColor(getActivity().getResources().getColor(R.color.theme));
                    } else {
                        optionName.setTextColor(getActivity().getResources().getColor(R.color.option));
                        Drawable drawable = getActivity().getResources()
                                .getDrawable(R.drawable.option_item_box_shape);
                        optionNameBox.setBackground(drawable);
                        optionContent.setTextColor(getActivity().getResources().getColor(R.color.option));
                    }

                }
                //设置我的选择
                View itemView = optionRecycler.getLayoutManager()
                        .findViewByPosition(position);
                TextView optionName = itemView.findViewById(R.id.optionName);
                question.setMyOption(optionName.getText().toString());

                //通知答题卡更新
                DaTiActivity daTiActivity = (DaTiActivity) getActivity();
                if (daTiActivity != null) {
                    daTiActivity.scantronUpdate();
                }
            }

        });

        //设置布局管理器
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };

        optionRecycler.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        // viewHolder.optionRecycler.setHasFixedSize(true);
        optionRecycler.setAdapter(optionRecyclerAdapter);
    }

    /**
     * 批改
     */
    @Override
    public void gradingQuestion() {
        //设置为不能答题状态
        canAnswer = false;
        //显示结果栏
        questionResult.setVisibility(View.VISIBLE);
        //我的答案
        String myOption = question.getMyOption();
        //正确答案
        String correctOption = question.getCorrectOption();
        //正确选项位置
        char correct = correctOption.charAt(0);
        int correctPos = correct - 65;
        //我的选项位置
        char error = myOption.charAt(0);
        int errorPos = error - 65;
        String correctStr = Strings.CORRECT_ANSWER +
                "<font color=\"#25c27c\">" + correctOption + "</font>";
        correctAnswer.setText(Html.fromHtml(correctStr));
        if (!myOption.equals(correctOption)) {
            String myStr = Strings.MY_ANSWER +
                    "<font color=\"#ff0000\">" + myOption + Strings.ERROR + "</font>";
            myAnswer.setText(Html.fromHtml(myStr));

            //选择错误
            View itemView = optionRecycler.getLayoutManager()
                    .findViewByPosition(errorPos);
            TextView optionName = itemView.findViewById(R.id.optionName);
            RelativeLayout optionNameBox = itemView.findViewById(R.id.optionNameBox);
            TextView optionContent = itemView.findViewById(R.id.optionContent);
            optionName.setTextColor(getActivity().getResources().getColor(R.color.white));
            Drawable drawable = getActivity().getResources()
                    .getDrawable(R.drawable.error_option_shape);
            optionNameBox.setBackground(drawable);
            optionContent.setTextColor(getActivity().getResources().getColor(R.color.error));
        } else {
            //选择错误
            String myStr = Strings.MY_ANSWER +
                    "<font color=\"#25c27c\">" + myOption + Strings.CORRECT + "</font>";

            myAnswer.setText(Html.fromHtml(myStr));
        }
        //设置正确选项
        View itemView = optionRecycler.getLayoutManager()
                .findViewByPosition(correctPos);
        TextView optionName = itemView.findViewById(R.id.optionName);
        RelativeLayout optionNameBox = itemView.findViewById(R.id.optionNameBox);
        TextView optionContent = itemView.findViewById(R.id.optionContent);
        optionName.setTextColor(getActivity().getResources().getColor(R.color.white));
        Drawable drawable = getActivity().getResources()
                .getDrawable(R.drawable.correct_option_shape);
        optionNameBox.setBackground(drawable);
        optionContent.setTextColor(getActivity().getResources().getColor(R.color.theme));

    }

}

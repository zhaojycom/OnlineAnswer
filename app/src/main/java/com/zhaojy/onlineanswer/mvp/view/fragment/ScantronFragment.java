package com.zhaojy.onlineanswer.mvp.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.mvp.adapter.ScantronAdapter;
import com.zhaojy.onlineanswer.mvp.contract.ScantronFragmentContract;
import com.zhaojy.onlineanswer.mvp.presenter.ScantronFragmentPresenter;
import com.zhaojy.onlineanswer.mvp.view.activity.DaTiActivity;

import java.util.List;

import butterknife.BindView;

/**
 * @author: zhaojy
 * @data:On 2018/10/20.
 */

public class ScantronFragment extends BaseFragment implements DaTiActivity.GradingPapers
        , ScantronFragmentContract.View {


    /**
     * 答题卡结果
     */
    @BindView(R.id.scantronResult)
    public GridView scantronResult;
    private ScantronAdapter scantronAdapter;
    private List<Question> questionList;
    private DaTiActivity daTiActivity;
    @BindView(R.id.finished)
    public TextView finished;

    private ScantronFragmentContract.Presenter presenter;

    @Override
    protected int getPageLayoutID() {
        return R.layout.scantron_page;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        presenter = new ScantronFragmentPresenter(this);
        daTiActivity = (DaTiActivity) getActivity();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //初始化页面
        initPage();
        //设置答题卡
        setScantron();
    }

    @Override
    protected void process(Bundle savedInstanceState) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @Override
    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    /**
     * 初始化页面
     */
    @Override
    public void initPage() {
        finished.setText(Strings.FINISHED + "(0/" + questionList.size() + ")");
    }

    /**
     * 设置答题卡
     */
    @Override
    public void setScantron() {
        scantronAdapter = new ScantronAdapter(getActivity(), questionList, scantronResult);
        scantronResult.setAdapter(scantronAdapter);
        scantronResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {

                //通过位置跳转到相应的问题
                daTiActivity.skipToQuestionByPos(position);
            }
        });
    }

    /**
     * 更新答题卡
     */
    @Override
    public void scantronUpdate() {
        scantronAdapter = null;
        scantronAdapter = new ScantronAdapter(getActivity(), questionList, scantronResult);
        scantronResult.setAdapter(scantronAdapter);

        //更新已完成题目数量
        int count = 0;
        for (Question question : questionList) {
            if (question.getMyOption() != null) {
                count++;
            }
        }
        finished.setText(Strings.FINISHED + "(" + count + "/" + questionList.size() + ")");
    }

    /**
     * 批改
     */
    @Override
    public void gradingQuestion() {
        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            //我的答案
            String myOption = question.getMyOption();
            //正确答案
            String correctOption = question.getCorrectOption();

            //选择错误
            View view = scantronAdapter.getItemView(i);
            TextView questionNum = view.findViewById(R.id.questionNum);
            RelativeLayout questionNumBox = view.findViewById(R.id.questionNumBox);
            questionNum.setTextColor(getActivity().getResources().getColor(R.color.white));
            if (myOption == null) {
                //未选择，错误
                Drawable drawable = getActivity().getDrawable(R.drawable.scantron_error_shape);
                questionNumBox.setBackground(drawable);
            } else if (myOption.equals(correctOption)) {
                //选择正确
                Drawable drawable = getActivity().getDrawable(R.drawable.scantron_correct_shape);
                questionNumBox.setBackground(drawable);
            } else {
                //选择错误
                Drawable drawable = getActivity().getDrawable(R.drawable.scantron_error_shape);
                questionNumBox.setBackground(drawable);
            }
        }
    }

}

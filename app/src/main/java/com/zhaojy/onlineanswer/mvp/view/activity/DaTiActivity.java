package com.zhaojy.onlineanswer.mvp.view.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingerth.supdialogutils.SYSDiaLogUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.bean.QuestionDifficult;
import com.zhaojy.onlineanswer.bean.ResponseBody;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.mvp.adapter.QuestionAdapter;
import com.zhaojy.onlineanswer.mvp.contract.DaTiActivityContract;
import com.zhaojy.onlineanswer.mvp.presenter.DaTiActivityPresenter;
import com.zhaojy.onlineanswer.mvp.view.fragment.QuestionFragment;
import com.zhaojy.onlineanswer.mvp.view.fragment.ScantronFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: zhaojy
 * @data:On 2018/10/18.
 */

public class DaTiActivity extends BaseActivity implements DaTiActivityContract.View {
    public final static String QUESTION_SORT_NAME = "questionSortName";
    public final static String QUESION_SORT_ID = "questionSortId";
    /**
     * 答题分类
     */
    public final static String DATI_SORT = "datiSort";
    /**
     * 普通分类答题
     */
    public final static int ORDINARY_SORT = 0x100;
    /**
     * 错题分类答题
     */
    public final static int ERROR_SORT = 0x101;
    /**
     * 百科英雄答题
     */
    public final static int BAIKEHERO = 0x102;
    @BindView(R.id.back)
    public ImageView back;
    @BindView(R.id.questionSortName)
    public TextView questionSortName;
    @BindView(R.id.difficlut)
    public TextView difficlut;
    private String questionSortNameStr;
    private int questionSortId;
    private int datiSort;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    private QuestionAdapter questionAdapter;
    private List<Question> questionList;
    /**
     * 题目fragment集合
     */
    private List<Fragment> questionFragmentList;

    @BindView(R.id.subTitle)
    public TextView subTitle;
    @BindView(R.id.page)
    public TextView page;
    @BindView(R.id.preQuestion)
    public TextView preQuestion;
    @BindView(R.id.nextQuestion)
    public TextView nextQuestion;
    @BindView(R.id.submitAnswer)
    public TextView submitAnswer;
    @BindView(R.id.chronometer)
    public Chronometer chronometer;
    @BindView(R.id.scantron)
    public ImageView scantron;
    @BindView(R.id.topBar)
    public RelativeLayout topBar;
    @BindView(R.id.bottomBar)
    public LinearLayout bottomBar;
    /**
     * 当前浏览的题目位置索引
     */
    private int curQuestionPos;

    private DaTiActivityContract.Presenter presenter;

    /**
     * 是否提交完毕
     */
    private boolean submited;

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (chronometer != null) {
            chronometer.stop();
        }
        if (viewPager != null) {
            viewPager.removeAllViews();
            viewPager = null;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            back();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initData() {
        presenter = new DaTiActivityPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initViewListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == questionFragmentList.size() - 1) {
                    //如果是最后一页，即答题卡页面
                    //隐藏topbar
                    topBar.setVisibility(View.GONE);
                    //显示答题卡页面
                    showScantronPage();
                } else {
                    page.setText((position + 1) + "/" + questionList.size());
                    //显示topbar
                    topBar.setVisibility(View.VISIBLE);
                    //显示题目页面
                    showQuestionPage();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void process() {
        //获取intent信息
        getIntentInfo();
        //设置题目分类名称
        setQuestionSortName();
        //设置题目viewPager
        setViewPager();

        //判断答题类型
        judgeDatiSort();

    }

    /**
     * 判断答题类型
     */
    private void judgeDatiSort() {

        switch (datiSort) {
            case ORDINARY_SORT:
                //普通答题模式
                getQuestions();
                break;
            case ERROR_SORT:
                //读取错题
                getErrorQuestions();
                break;
            case BAIKEHERO:
                //获取百科英雄题目
                getBaikeHeroQuestions();
                break;
            default:
                break;
        }
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.dati_layout;
    }

    /**
     * 获取intent信息
     */
    @Override
    public void getIntentInfo() {
        Intent intent = getIntent();
        questionSortNameStr = intent.getStringExtra(QUESTION_SORT_NAME);
        questionSortId = intent.getIntExtra(QUESION_SORT_ID, 0);
        datiSort = intent.getIntExtra(DATI_SORT, 0);
    }

    /**
     * 设置题目分类名称
     */
    @Override
    public void setQuestionSortName() {
        questionSortName.setText(questionSortNameStr);
        subTitle.setText(questionSortNameStr);
    }

    /**
     * 设置题目viewPager
     */
    @Override
    public void setViewPager() {
        if (questionList == null) {
            questionList = new ArrayList<>();
        }
        if (questionFragmentList == null) {
            questionFragmentList = new ArrayList<>();
        }
        questionAdapter = new QuestionAdapter(questionFragmentList, getSupportFragmentManager());
        viewPager.setAdapter(questionAdapter);

        viewPager.setOffscreenPageLimit(Strings.MAX_READ_QUESTION_SUM);
    }

    /**
     * 设置计时器
     */
    @Override
    public void setChronometer() {
        //计时器清零
        chronometer.setBase(SystemClock.elapsedRealtime());
        int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60);
        chronometer.setFormat("0" + String.valueOf(hour) + ":%s");
        chronometer.start();
    }

    /**
     * 显示答题卡页面
     */
    @Override
    public void showScantronPage() {
        preQuestion.setVisibility(View.GONE);
        nextQuestion.setVisibility(View.GONE);
        submitAnswer.setVisibility(View.VISIBLE);
    }

    /**
     * 显示题目页面
     */
    @Override
    public void showQuestionPage() {
        preQuestion.setVisibility(View.VISIBLE);
        nextQuestion.setVisibility(View.VISIBLE);
        submitAnswer.setVisibility(View.GONE);
    }

    /**
     * 更新答题卡
     */
    @Override
    public void scantronUpdate() {
        //取出答题卡界面fragment
        ScantronFragment scantronFragment = (ScantronFragment)
                questionFragmentList.get(questionFragmentList.size() - 1);
        scantronFragment.scantronUpdate();
        //选择完成进入下一题
        int curPage = viewPager.getCurrentItem();
        viewPager.setCurrentItem(curPage + 1);
    }

    /**
     * 通过位置跳转到相应的问题
     *
     * @param pos 位置
     */
    @Override
    public void skipToQuestionByPos(int pos) {
        viewPager.setCurrentItem(pos);
    }

    @Override
    public void getQuestions() {
        //获取难度
        presenter.getQuestionDifficult(this);
    }

    /**
     * 读取错题
     */
    @Override
    public void getErrorQuestions() {
        presenter.getErrorQuestions(this, questionSortId);
        //显示加载提示框
        SYSDiaLogUtils.showProgressDialog(this,
                SYSDiaLogUtils.SYSDiaLogType.IosType, Strings.GIVE_QUESTIONS,
                false, null);
    }

    /**
     * 获取百科英雄题目
     */
    @Override
    public void getBaikeHeroQuestions() {
        //获取难度
        presenter.getQuestionDifficult(this);
    }

    @Override
    public void updateQuestions(List<Question> questions) {
        if (questions.size() > 0) {
            page.setText("1/" + questions.size());

            questionList.addAll(questions);
            for (int i = 0; i < questions.size(); i++) {
                QuestionFragment questionFragment = new QuestionFragment();
                questionFragment.setQuestion(questions.get(i));
                questionFragment.setQuestionPos(i);
                questionFragmentList.add(questionFragment);
            }
            //添加答题卡界面
            ScantronFragment scantronFragment = new ScantronFragment();
            scantronFragment.setQuestionList(questionList);
            questionFragmentList.add(scantronFragment);
            questionAdapter.notifyDataSetChanged();

            //设置计时器
            setChronometer();
        }
        //隐藏加载提示框
        SYSDiaLogUtils.dismissProgress();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hiddeLoading() {

    }

    @Override
    public void submitResult(ResponseBody responseBody) {
        if (responseBody.getCode() == Strings.SUBMIT_SUCCESS) {
            //提交成功
            //遍历通知批改试卷
            for (Fragment fragment : questionFragmentList) {
                GradingPapers gradingPaper = (GradingPapers) fragment;
                gradingPaper.gradingQuestion();
            }

            //隐藏底部栏
            bottomBar.setVisibility(View.GONE);
        } else {
            //提交失败
            Log.e(TAG, "提交失败");
        }
    }

    @Override
    public void updateDifficult(String[] items, final List<QuestionDifficult> difficultList) {
        XPopup.get(DaTiActivity.this).asCenterList(Strings.SELECT_DIFFICULT,
                items,
                new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        QuestionDifficult difficult = difficultList.get(position);
                        //获取难度
                        int difficultId = difficult.getId();
                        //设置难度文字
                        difficlut.setText("难度:" + difficult.getName());

                        //显示加载提示框
                        SYSDiaLogUtils.showProgressDialog(DaTiActivity.this,
                                SYSDiaLogUtils.SYSDiaLogType.IosType, Strings.GIVE_QUESTIONS,
                                false, null);
                        //根据答题类型进行相应的调用
                        switch (datiSort) {
                            case ORDINARY_SORT:
                                //普通分类答题模式
                                presenter.getQuestions(DaTiActivity.this, questionSortId, difficultId);
                                break;
                            case BAIKEHERO:
                                //百科英雄
                                presenter.getBaikeHeroQuestions(
                                        DaTiActivity.this, difficultId);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }

    @Override
    public void giveQuestionFailure() {
        //隐藏加载提示框
        SYSDiaLogUtils.dismissProgress();
        XPopup.get(this).asConfirm(Strings.ERROR_TIP, Strings.GIVE_QUESTION_FAILURE
                , null)
                .show();
    }

    /**
     * 提交并查看结果
     */
    @OnClick(R.id.submitAnswer)
    @Override
    public void submitAnswer() {
        for (Question question : questionList) {
            if (question.getMyOption() == null) {
                Toast.makeText(this, Strings.PLEASE_FINISH_ALL_QUESTION, Toast.LENGTH_SHORT).show();
                return;
            }
        }

        switch (datiSort) {
            case ERROR_SORT:
                //提交错题更新
                presenter.submitErrorQuetstion(questionList, this);
                break;
            default:
                //提交普通类型答题结果
                presenter.submitQuestion(questionList, this);
                break;
        }

        //停止计时
        chronometer.stop();
    }

    @OnClick(R.id.scantron)
    @Override
    public void scantron() {
        //记录当前浏览的题目索引
        curQuestionPos = viewPager.getCurrentItem();
        //显示答题卡页面
        viewPager.setCurrentItem(questionFragmentList.size() - 1);
    }

    @OnClick(R.id.back)
    @Override
    public void back() {
        if (submited) {
            //如果提交完成直接退出
            this.finish();
        } else if (viewPager.getCurrentItem() == questionFragmentList.size() - 1) {
            //如果当前为答题卡页面,则返回至前面浏览的页面
            viewPager.setCurrentItem(curQuestionPos);
        } else {
            this.finish();
        }
    }

    @OnClick(R.id.preQuestion)
    @Override
    public void preQuestion() {
        int curPage = viewPager.getCurrentItem();
        int intentPage = curPage - 1;
        if (intentPage >= 0) {
            viewPager.setCurrentItem(intentPage);
        }
    }

    @OnClick(R.id.nextQuestion)
    @Override
    public void nextQuestion() {
        int curPage2 = viewPager.getCurrentItem();
        int intentPage2 = curPage2 + 1;
        if (intentPage2 < questionAdapter.getCount()) {
            viewPager.setCurrentItem(intentPage2);
        }
    }

    /**
     * 批改问题接口
     */
    public interface GradingPapers {
        void gradingQuestion();
    }

}

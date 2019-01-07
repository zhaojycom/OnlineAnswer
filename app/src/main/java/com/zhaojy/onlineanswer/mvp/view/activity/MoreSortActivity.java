package com.zhaojy.onlineanswer.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.User;
import com.zhaojy.onlineanswer.mvp.adapter.MoreSortsAdapter;
import com.zhaojy.onlineanswer.mvp.contract.MoreSortActivityContract;
import com.zhaojy.onlineanswer.mvp.presenter.MoreSortActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: zhaojy
 * @data:On 2018/10/15.
 */

public class MoreSortActivity extends BaseActivity implements View.OnClickListener
        , MoreSortActivityContract.View {
    @BindView(R.id.back)
    public ImageView back;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<QuestionSort> questionSortList;
    private MoreSortsAdapter moreSortsAdapter;

    private MoreSortActivityContract.Presenter presenter;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        questionSortList = new ArrayList<>();
        moreSortsAdapter = new MoreSortsAdapter(questionSortList, this);

        presenter = new MoreSortActivityPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initViewListener() {
        back.setOnClickListener(this);

        moreSortsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //如果用户未登录，跳转至登录界面
                if (User.getInstance().getPhone() == null) {
                    LoginActivity.newInstance(MoreSortActivity.this);
                    return;
                }
                Intent intent = new Intent(MoreSortActivity.this, DaTiActivity.class);
                intent.putExtra(DaTiActivity.QUESTION_SORT_NAME, questionSortList.get(position)
                        .getName());
                intent.putExtra(DaTiActivity.QUESION_SORT_ID, questionSortList.get(position)
                        .getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void process() {
        //设置RecyclerView
        setRecyclerView();
        //设置下拉刷新
        setSwipeRefreshLayout();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.more_sort_layout;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        recyclerView = null;
        moreSortsAdapter = null;

        presenter.clearPresenter();
    }

    /**
     * 设置下拉刷新
     */
    @Override
    public void setSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.theme);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getQuestionSortInfo();
            }
        });
        getQuestionSortInfo();
    }

    /**
     * 获取分类信息
     */
    @Override
    public void getQuestionSortInfo() {
        presenter.getQuestionSortInfo(this);
    }

    /**
     * 设置RecyclerView
     */
    @Override
    public void setRecyclerView() {

        //一行代码开启动画 默认CUSTOM动画
        moreSortsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        //设置布局管理器
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(moreSortsAdapter);

    }

    @Override
    public void refreshSort(List<QuestionSort> questionSorts) {
        //停止刷新
        swipeRefreshLayout.setRefreshing(false);
        if (questionSorts != null) {
            questionSortList.clear();
            questionSortList.addAll(questionSorts);
            moreSortsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    @OnClick(R.id.search)
    public void search() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SortSearchActivity.SORTS, questionSortList);
        SortSearchActivity.newInstance(this, bundle);
    }

}


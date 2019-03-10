package com.zhaojy.onlineanswer.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.User;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.helper.ExitLoginSubject;
import com.zhaojy.onlineanswer.helper.IExitLoginObserver;
import com.zhaojy.onlineanswer.mvp.adapter.MoreSortsAdapter;
import com.zhaojy.onlineanswer.mvp.contract.LoginActivityContract;
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

public class MoreSortActivity extends BaseActivity implements MoreSortActivityContract.View
        , IExitLoginObserver
        , LoginActivityContract.UpdateUserInfo {
    @BindView(R.id.back)
    public ImageView back;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<QuestionSort> questionSortList;
    private MoreSortsAdapter moreSortsAdapter;

    private MoreSortActivityContract.Presenter presenter;

    private static UpdateUserSort updateUserSort;

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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                intent.putExtra(DaTiActivity.DATI_SORT, DaTiActivity.ORDINARY_SORT);
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
        //获取题目分类
        getQuestionSortInfo();
        //注册退出登录更新接口
        ExitLoginSubject.getInstance().registerObserver(this);
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
    }

    /**
     * 获取分类信息
     */
    @Override
    public void getQuestionSortInfo() {
        presenter.getMoreSort(this);
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

        //设置监听器
        moreSortsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public synchronized void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                QuestionSort sort = questionSortList.get(position);
                //将view设置为不可用状态防止快速点击造成的错误
                // view.setEnabled(false);
                switch (view.getId()) {
                    case R.id.delete:
                        //删除首页分类
                        presenter.deleteUserSort(adapter, sort, position,
                                MoreSortActivity.this);
                        break;
                    case R.id.add:
                        //计算
                        int count = 0;
                        for (QuestionSort temp : questionSortList) {
                            if (temp.isAdded()) {
                                count++;
                            }
                        }
                        if (count < Strings.MAX_USER_SORT) {
                            //添加首页分类
                            presenter.addUserSort(adapter, sort, position,
                                    MoreSortActivity.this);
                        } else {
                            Toast.makeText(MoreSortActivity.this,
                                    Strings.OUT_MAX_USER_SORT, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

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

    @Override
    public void deleteSuccess(BaseQuickAdapter adapter, QuestionSort sort, int pos) {
        sort.setAdded(false);
        adapter.notifyItemChanged(pos);
        //通知首页更新
        updateUserSort.updateUserSort();
    }

    @Override
    public void deleteFailure() {
        Toast.makeText(this, Strings.DELETE_FAILURE_MSG, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addSuccess(BaseQuickAdapter adapter, QuestionSort sort, int pos) {
        sort.setAdded(true);
        adapter.notifyItemChanged(pos);
        //通知首页更新
        updateUserSort.updateUserSort();
    }

    @Override
    public void addFailure() {
        Toast.makeText(this, Strings.ADD_FAILURE_MSG, Toast.LENGTH_SHORT).show();
    }

    public static void setUpdateUserSort(MoreSortActivity.UpdateUserSort updateUserSort2) {
        updateUserSort = updateUserSort2;
    }

    @Override
    public void exitLoginUpdate() {
        //退出登录
        swipeRefreshLayout.setRefreshing(true);
        getQuestionSortInfo();
    }

    @Override
    public void setUserInfo() {
        //登录刷新
        swipeRefreshLayout.setRefreshing(true);
        getQuestionSortInfo();
    }

    public interface UpdateUserSort {
        void updateUserSort();
    }
}


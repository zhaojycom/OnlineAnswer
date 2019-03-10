package com.zhaojy.onlineanswer.mvp.view.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.ErrorsSort;
import com.zhaojy.onlineanswer.mvp.adapter.MyErrorsAdapter;
import com.zhaojy.onlineanswer.mvp.contract.ErrorsPracticeActivityContract;
import com.zhaojy.onlineanswer.mvp.presenter.ErrorsPracticeActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: zhaojy
 * @data:On 2019/1/26.
 */
public class ErrorsPracticeActivity extends BaseActivity implements
        ErrorsPracticeActivityContract.View {
    @BindView(R.id.back)
    public ImageView back;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;
    private MyErrorsAdapter errorsAdapter;
    private List<ErrorsSort> errorsSortList;

    private ErrorsPracticeActivityContract.Presenter presenter;

    @Override
    protected void initData() {
        presenter = new ErrorsPracticeActivityPresenter(this);
        errorsSortList = new ArrayList<>();
        errorsAdapter = new MyErrorsAdapter(errorsSortList, this);
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

        errorsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ErrorsSort errorsSort = errorsSortList.get(position);
                Intent intent = new Intent(ErrorsPracticeActivity.this
                        , DaTiActivity.class);
                intent.putExtra(DaTiActivity.QUESTION_SORT_NAME, errorsSort.getName());
                intent.putExtra(DaTiActivity.QUESION_SORT_ID, errorsSort.getId());
                intent.putExtra(DaTiActivity.DATI_SORT, DaTiActivity.ERROR_SORT);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void process() {
        setRecyclerView();
        setSwipeRefreshLayout();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.errors_practice_layout;
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
                //获取错题分类信息
                presenter.getMyErrorsSorts(ErrorsPracticeActivity.this);
            }
        });
        //获取错题分类信息
        presenter.getMyErrorsSorts(ErrorsPracticeActivity.this);
    }

    /**
     * 设置RecyclerView
     */
    @Override
    public void setRecyclerView() {
        //一行代码开启动画 默认CUSTOM动画
        errorsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

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
        recyclerView.setAdapter(errorsAdapter);
    }

    @Override
    public void updateMyErrorsSorts(List<ErrorsSort> errorsSorts) {
        errorsSortList.clear();
        if (errorsSorts != null) {
            errorsSortList.addAll(errorsSorts);
        }

        errorsAdapter.notifyDataSetChanged();
        //关闭刷新
        swipeRefreshLayout.setRefreshing(false);
    }

}

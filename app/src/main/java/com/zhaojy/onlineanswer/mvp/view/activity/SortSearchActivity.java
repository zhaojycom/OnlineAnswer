package com.zhaojy.onlineanswer.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.SearchHistory;
import com.zhaojy.onlineanswer.bean.User;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.data.objectbox.SearchHistoryObjectBox;
import com.zhaojy.onlineanswer.mvp.adapter.SearchHistoryAdapter;
import com.zhaojy.onlineanswer.mvp.adapter.SearchResultAdapter;
import com.zhaojy.onlineanswer.mvp.contract.SortSearchActivityContract;
import com.zhaojy.onlineanswer.mvp.presenter.SortSearchActivityPresenter;
import com.zhaojy.onlineanswer.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.objectbox.Box;

/**
 * @author: zhaojy
 * @data:On 2018/12/29.
 */
public class SortSearchActivity extends BaseActivity implements SortSearchActivityContract.View {
    public final static String SORTS = "sorts";
    @BindView(R.id.inputKeyWord)
    public EditText inputKeyWord;
    @BindView(R.id.searchHistory)
    public RecyclerView searchHistory;
    @BindView(R.id.searchResult)
    public RecyclerView searchResult;
    private SortSearchActivityContract.Presenter presenter;
    private ArrayList<QuestionSort> questionSorts;
    private List<SearchHistory> searchHistoryList;
    private SearchHistoryAdapter historyAdapter;
    private SearchResultAdapter resultAdapter;
    private List<QuestionSort> resultList;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (searchResult.getVisibility() == View.VISIBLE) {
                searchResult.setVisibility(View.GONE);
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void initData() {
        presenter = new SortSearchActivityPresenter(this);
        resultList = new ArrayList<>();
        resultAdapter = new SearchResultAdapter(resultList, this);

        getIntentInfo();
    }

    @Override
    protected void initView() {
        setSearchHistory();
    }

    @Override
    protected void initViewListener() {
        inputKeyWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {

                    //获取关键字
                    String keyword = inputKeyWord.getText().toString();
                    if (!TextUtils.isEmpty(keyword)) {
                        //执行搜索
                        excuteSearch(keyword);
                        //保存搜索历史
                        saveSearchHistory(keyword);
                    } else {
                        //输入为空提示
                        Toast.makeText(SortSearchActivity.this, Strings.INPUT_NO_EMPTY
                                , Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }

        });

        inputKeyWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //获取关键字
                String keyword = inputKeyWord.getText().toString();
                if (!TextUtils.isEmpty(keyword)) {
                    //执行搜索
                    excuteSearch(keyword);
                } else {
                    searchResult.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        resultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //非法分类
                if (resultList.get(position).getId() == 0) {
                    return;
                }
                //如果用户未登录，跳转至登录界面
                if (User.getInstance().getPhone() == null) {
                    LoginActivity.newInstance(SortSearchActivity.this);
                    return;
                }
                Intent intent = new Intent(SortSearchActivity.this, DaTiActivity.class);
                intent.putExtra(DaTiActivity.QUESTION_SORT_NAME, resultList.get(position)
                        .getName());
                intent.putExtra(DaTiActivity.QUESION_SORT_ID, resultList.get(position)
                        .getId());
                intent.putExtra(DaTiActivity.DATI_SORT, DaTiActivity.ORDINARY_SORT);
                startActivity(intent);
            }
        });

        historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchHistory searchHistory = searchHistoryList.get(position);
                String keyword = searchHistory.getKeyword();
                excuteSearch(keyword);
                //保存搜索历史
                saveSearchHistory(keyword);
            }
        });

    }

    @Override
    protected void process() {
        readSearchHistory();
        setSearchResult();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.sort_search;
    }

    public static void newInstance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, SortSearchActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void getIntentInfo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        questionSorts = (ArrayList<QuestionSort>) bundle.getSerializable(SORTS);

    }

    @Override
    public void setSearchHistory() {
        searchHistoryList = new ArrayList<>();
        historyAdapter = new SearchHistoryAdapter(searchHistoryList, this);
        //一行代码开启动画 默认CUSTOM动画
        historyAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        //设置布局管理器
        final FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        searchHistory.setLayoutManager(flowLayoutManager);
        //设置每一个item间距
        searchHistory.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(this
                , Strings.SEARCH_HISTORY_ITEM_GAP)));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        searchHistory.setHasFixedSize(true);
        searchHistory.setAdapter(historyAdapter);
    }

    @Override
    public void saveSearchHistory(String keyword) {
        //遍历数据库数据查找是否存在该关键字
        for (SearchHistory searchHistory : searchHistoryList) {
            if (keyword.equals(searchHistory.getKeyword())) {
                //如果存在该关键字，则删除
                Box<SearchHistory> searchHistoryBox = SearchHistoryObjectBox.getInstance()
                        .getSearchHistoryBox();
                searchHistoryBox.remove(searchHistory.getId());
                break;
            }
        }
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setKeyword(keyword);

        presenter.saveSearchHistory(searchHistory);
        searchHistoryList.add(0, searchHistory);
        historyAdapter.notifyItemInserted(0);

        //强制隐藏键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(inputKeyWord.getWindowToken(), 0);
        }
    }

    @Override
    public void readSearchHistory() {
        List<SearchHistory> searchHistories = presenter.readSearchHistory();
        if (searchHistories != null) {
            searchHistoryList.addAll(searchHistories);
            Collections.reverse(searchHistoryList);
            historyAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 清除历史
     */
    @Override
    @OnClick(R.id.clearRecords)
    public void clearRecords() {
        presenter.clearRecords();
        searchHistoryList.clear();
        readSearchHistory();
    }

    /**
     * 设置搜索结果RecyclerView
     */
    @Override
    public void setSearchResult() {
        //一行代码开启动画 默认CUSTOM动画
        resultAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        //设置布局管理器
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };

        searchResult.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        searchResult.setHasFixedSize(true);
        searchResult.setAdapter(resultAdapter);

    }

    @Override
    public void excuteSearch(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            searchResult.setVisibility(View.GONE);
            return;
        }
        //先清除之前的搜索结果
        resultList.clear();
        //遍历所有分类查找
        for (QuestionSort sort : questionSorts) {
            if (sort.getName().contains(keyword)) {
                resultList.add(sort);
            }
        }

        if (resultList.size() == 0) {
            //如果没有搜索结果则显示无结果
            QuestionSort sort = new QuestionSort();
            sort.setName(Strings.NO_SEARCH_RESULT);
            resultList.add(sort);
        }

        searchResult.setVisibility(View.VISIBLE);
        //通知更新
        resultAdapter.notifyDataSetChanged();
    }

}

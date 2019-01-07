package com.zhaojy.onlineanswer.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.SearchHistory;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.mvp.adapter.SearchHistoryAdapter;
import com.zhaojy.onlineanswer.mvp.contract.SortSearchActivityContract;
import com.zhaojy.onlineanswer.mvp.presenter.SortSearchActivityPresenter;
import com.zhaojy.onlineanswer.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    private SortSearchActivityContract.Presenter presenter;
    private ArrayList<QuestionSort> questionSorts;
    private List<SearchHistory> searchHistoryList;
    private SearchHistoryAdapter historyAdapter;


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void initData() {
        presenter = new SortSearchActivityPresenter(this);

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
                    //执行搜索
                    //获取关键字
                    String keyword = inputKeyWord.getText().toString();
                    if (!TextUtils.isEmpty(keyword)) {
                        //保存搜索历史
                        saveSearchHistory(keyword);
                    }
                }

                return false;
            }

        });

    }

    @Override
    protected void process() {
        readSearchHistory();
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
                return;
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

    @Override
    @OnClick(R.id.clearRecords)
    public void clearRecords() {
        presenter.clearRecords();
        searchHistoryList.clear();
        readSearchHistory();
    }

}

package com.zhaojy.onlineanswer.mvp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.FunDaTiBean;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.Slideshow;
import com.zhaojy.onlineanswer.bean.User;
import com.zhaojy.onlineanswer.constant.SiteInfo;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.helper.ExitLoginSubject;
import com.zhaojy.onlineanswer.helper.IExitLoginObserver;
import com.zhaojy.onlineanswer.mvp.adapter.FunDaTiAdapter;
import com.zhaojy.onlineanswer.mvp.adapter.QuestionSortAdapter;
import com.zhaojy.onlineanswer.mvp.contract.LoginActivityContract;
import com.zhaojy.onlineanswer.mvp.contract.QuestionBankFragmentContract;
import com.zhaojy.onlineanswer.mvp.presenter.QuestionBankPresenter;
import com.zhaojy.onlineanswer.mvp.view.activity.DaTiActivity;
import com.zhaojy.onlineanswer.mvp.view.activity.LoginActivity;
import com.zhaojy.onlineanswer.mvp.view.activity.MoreSortActivity;
import com.zhaojy.onlineanswer.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: zhaojy
 * @data:On 2018/10/14.
 */

public class QuestionBankFragment extends BaseFragment
        implements QuestionBankFragmentContract.View
        , IExitLoginObserver
        , LoginActivityContract.UpdateUserInfo
        , MoreSortActivity.UpdateUserSort {
    @BindView(R.id.banner)
    public Banner banner;
    /**
     * 轮播图图片地址
     */
    private List<Slideshow> images;
    @BindView(R.id.sortGridView)
    public GridView sortGridView;
    @BindView(R.id.funDaTi)
    public RecyclerView funDaTiRecycler;
    private List<QuestionSort> questionSortList;
    private QuestionSortAdapter questionSortAdapter;
    private FunDaTiAdapter funDaTiAdapter;
    private List<FunDaTiBean> funDaTiBeanList;

    private QuestionBankFragmentContract.Presenter presenter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (banner != null) {
            banner.releaseBanner();
        }
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.question_bank;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        presenter = new QuestionBankPresenter(this);
        //初始化趣味答题列表数据
        initFunDaTiData();
    }

    /**
     * 初始化趣味答题列表数据
     */
    private void initFunDaTiData() {
        funDaTiBeanList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            FunDaTiBean funDaTiBean = new FunDaTiBean();
            funDaTiBean.setTitle(Strings.ENCYCLOPEDIA_HERO);
            funDaTiBean.setIcon(R.mipmap.baikehero);
            funDaTiBeanList.add(funDaTiBean);
        }
        funDaTiAdapter = new FunDaTiAdapter(funDaTiBeanList);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //设置轮播图
        setBanner();
        //设置题目分类Gridview
        setSortGridView();
        //设置趣味答题RecyclerView
        setFunDaTi();
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        //获取题目分类信息
        presenter.getUserQuestionSort(getActivity());
        //获取轮播图
        presenter.getSlideshow(getActivity());
        //注册退出登录观察者
        ExitLoginSubject.getInstance().registerObserver(this);
        //设置登录更新接口
        LoginActivity.setUpdateUserInfo(this);
        //设置用户分类更新接口
        MoreSortActivity.setUpdateUserSort(this);
    }

    /**
     * 设置轮播图
     */
    private void setBanner() {
        //设置轮播图高度
        int screenWidth = ScreenUtils.getScreenWidth(getActivity());
        int height = (int) (screenWidth * Strings.BANNER_ASPECT_RATIO);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) banner.getLayoutParams();
        layoutParams.height = height;
    }

    /**
     * 设置题目分类Gridview
     */
    private void setSortGridView() {
        if (questionSortList == null) {
            questionSortList = new ArrayList<>();
        }

        questionSortAdapter = new QuestionSortAdapter(getActivity(), questionSortList, sortGridView);
        sortGridView.setAdapter(questionSortAdapter);
        sortGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {

                if (questionSortList.get(position).getName().equals(Strings.MORE_SORT)) {
                    //跳转到更多分类页面
                    Intent intent = new Intent(getActivity(), MoreSortActivity.class);
                    startActivity(intent);
                } else {
                    //如果用户未登录，跳转至登录界面
                    if (User.getInstance().getPhone() == null) {
                        LoginActivity.newInstance(getActivity());
                        return;
                    }
                    QuestionSort sort = questionSortList.get(position);
                    Intent intent = new Intent(getActivity(), DaTiActivity.class);
                    intent.putExtra(DaTiActivity.QUESTION_SORT_NAME, sort.getName());
                    intent.putExtra(DaTiActivity.QUESION_SORT_ID, sort.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void updateQuestionSort(List<QuestionSort> sortList) {
        questionSortList.clear();
        questionSortList.addAll(sortList);
        QuestionSort questionSort = new QuestionSort();
        questionSort.setIconUrl("");
        questionSort.setName(Strings.MORE_SORT);
        questionSortList.add(questionSort);
        questionSortAdapter = new QuestionSortAdapter(getActivity(), questionSortList, sortGridView);
        sortGridView.setAdapter(questionSortAdapter);
    }

    @Override
    public void updateSlideshow(List<Slideshow> bannerList) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.clear();
        images.addAll(bannerList);
        banner.setImages(images).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Slideshow slideshow = (Slideshow) path;
                String url = slideshow.getUrl();
                Glide.with(context)
                        .load(SiteInfo.HOST_URL + url)
                        .into(imageView);
            }
        });

        //设置轮播时间
        banner.setDelayTime(Strings.SLIDESHOW_GAP);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.start();
    }

    /**
     * 设置趣味答题recyclerview
     */
    @Override
    public void setFunDaTi() {
        //一行代码开启动画 默认CUSTOM动画
        funDaTiAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        //设置布局管理器
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };

        funDaTiRecycler.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        funDaTiRecycler.setHasFixedSize(true);
        funDaTiRecycler.setAdapter(funDaTiAdapter);

        //设置监听器
        funDaTiAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void exitLoginUpdate() {
        //退出登录
        //获取题目分类信息
        presenter.getUserQuestionSort(getActivity());
    }

    @Override
    public void setUserInfo() {
        //获取题目分类信息
        presenter.getUserQuestionSort(getActivity());
    }

    @Override
    public void updateUserSort() {
        //获取题目分类信息
        presenter.getUserQuestionSort(getActivity());
    }

}

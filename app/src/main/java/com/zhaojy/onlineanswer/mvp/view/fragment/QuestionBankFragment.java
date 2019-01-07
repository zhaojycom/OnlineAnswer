package com.zhaojy.onlineanswer.mvp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.User;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.mvp.adapter.QuestionSortAdapter;
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

public class QuestionBankFragment extends BaseFragment implements QuestionBankFragmentContract.View {
    @BindView(R.id.banner)
    public Banner banner;
    /**
     * 轮播图图片地址
     */
    private List<String> images;
    @BindView(R.id.sortGridView)
    public GridView sortGridView;
    private List<QuestionSort> questionSortList;
    private QuestionSortAdapter questionSortAdapter;

    private QuestionBankFragmentContract.Presenter presenter;

    @Override
    protected int getPageLayoutID() {
        return R.layout.question_bank;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        presenter = new QuestionBankPresenter(this);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //设置轮播图
        setBanner();
        //设置题目分类Gridview
        setSortGridView();
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        //获取题目分类信息
        presenter.getQuestionSort(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add("http://img.zcool.cn/community/0117ea577e1bb00000012e" +
                "7e9a2de2.jpg@1280w_1l_2o_100sh.jpg");
        images.add("http://img2.imgtn.bdimg.com/it/u=2003883170,1485855498&fm=26&gp=0.jpg");
        images.add("http://img3.imgtn.bdimg.com/it/u=2626473408,3175033639&fm=26&gp=0.jpg");

        banner.setImages(images).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                String url = (String) path;
                Glide.with(context)
                        .load(url)
                        .into(imageView);
            }
        });
        //设置轮播时间
        banner.setDelayTime(6000);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.start();
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
                    Intent intent = new Intent(getActivity(), DaTiActivity.class);
                    intent.putExtra(DaTiActivity.QUESTION_SORT_NAME, questionSortList.get(position)
                            .getName());
                    intent.putExtra(DaTiActivity.QUESION_SORT_ID, questionSortList.get(position)
                            .getId());
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void updateQuestionSort(List<QuestionSort> sortList) {
        questionSortList.addAll(sortList);
        QuestionSort questionSort = new QuestionSort();
        questionSort.setIconUrl("");
        questionSort.setName(Strings.MORE_SORT);
        questionSortList.add(questionSort);

        questionSortAdapter.notifyDataSetChanged();
    }
}

package com.zhaojy.onlineanswer.mvp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.bean.MyRecyclerBean;
import com.zhaojy.onlineanswer.bean.User;
import com.zhaojy.onlineanswer.constant.SiteInfo;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.data.user.UploadImages;
import com.zhaojy.onlineanswer.helper.ExitLoginSubject;
import com.zhaojy.onlineanswer.helper.IExitLoginObserver;
import com.zhaojy.onlineanswer.helper.IExitLoginSubject;
import com.zhaojy.onlineanswer.mvp.adapter.MyRecyclerAdapter;
import com.zhaojy.onlineanswer.mvp.contract.LoginActivityContract;
import com.zhaojy.onlineanswer.mvp.contract.MyFragmentContract;
import com.zhaojy.onlineanswer.mvp.presenter.MyFragmentPresenter;
import com.zhaojy.onlineanswer.mvp.view.activity.LoginActivity;
import com.zhaojy.onlineanswer.mvp.view.activity.MainActivity;
import com.zhaojy.onlineanswer.utils.ScreenUtils;
import com.zhaojy.onlineanswer.utils.SharePreferUtils;
import com.zhaojy.selectlibrary.control.PhotoSelectBuilder;
import com.zhaojy.selectlibrary.util.DisplayUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: zhaojy
 * @data:On 2018/10/14.
 */

public class MyFragment extends BaseFragment implements MyFragmentContract.View
        , IExitLoginObserver, LoginActivityContract.UpdateUserInfo {
    @BindView(R.id.personalInfoBox)
    public ConstraintLayout personalInfoBox;
    @BindView(R.id.avatar)
    public ImageView avatar;
    @BindView(R.id.nickname)
    public TextView nickname;
    private PhotoSelectBuilder builder;
    private MainActivity mainActivity;
    @BindView(R.id.alterNickname)
    public RelativeLayout alterNickname;
    @BindView(R.id.cancel)
    public TextView cancel;
    @BindView(R.id.asure)
    public TextView asure;
    @BindView(R.id.nicknameInput)
    public EditText nicknameInput;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    private String aboveAlterNickname;

    private MyFragmentContract.Presenter presenter;
    private IExitLoginSubject exitLoginSubject = ExitLoginSubject.getInstance();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clearPresenter();
    }

    /**
     * 退出登录
     */
    @OnClick(R.id.exitLogin)
    protected void exitLogin() {
        exitLoginSubject.notifyObserver();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.my;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        presenter = new MyFragmentPresenter(this);
        //注册退出登录观察者
        exitLoginSubject.registerObserver(this);
        //设置登录更新接口
        LoginActivity.setUpdateUserInfo(this);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void process(Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        //设置功能菜单
        setRecyclerView();
        //设置个人信息框
        setPersonalInfoBox();
        //登录
        login();
    }

    /**
     * 设置功能菜单
     */
    @Override
    public void setRecyclerView() {
        final List<MyRecyclerBean> data = new ArrayList<>();
        MyRecyclerBean recyclerBean = new MyRecyclerBean();
        recyclerBean.setIcon(R.mipmap.errors);
        recyclerBean.setTitle("错题练习");
        data.add(recyclerBean);

        MyRecyclerAdapter recyclerAdapter = new MyRecyclerAdapter(data);
        recyclerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = data.get(position).getIntent();
                if (intent != null) {
                    startActivity(intent);
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

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void alterNickNameSuccess(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        if (msg.equals(Strings.NICKNAME_ALTER_SUCCESS)) {
            nickname.setText(nicknameInput.getText().toString());
        } else {
            nickname.setText(aboveAlterNickname);
            User.getInstance().setNickname(aboveAlterNickname);
        }
        hiddenAlterNickname();
    }

    @Override
    public void alterNickNameFailure() {
        hiddenAlterNickname();
        nickname.setText(aboveAlterNickname);
        User.getInstance().setNickname(aboveAlterNickname);
    }

    /**
     * 设置个人信息框
     */
    @Override
    public void setPersonalInfoBox() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                personalInfoBox.getLayoutParams();
        //获取屏幕宽度
        int screenWidth = ScreenUtils.getScreenWidth(getActivity());
        double height = screenWidth * Strings.PERSONAL_INFO_BOX_ASPECT_RATIO;
        layoutParams.height = (int) height;
    }

    /**
     * 登录
     */
    @Override
    public void login() {
        final String phoneNumber = SharePreferUtils.getString(getActivity(),
                Strings.USER_PHONE);
        if (phoneNumber != null) {
            User user = User.getInstance();
            user.setPhone(phoneNumber);
            presenter.login(getActivity(), phoneNumber);
        }
    }

    @Override
    public void loginSuccess() {
        //设置用户信息
        setUserInfo();
    }

    @Override
    public void loginFailure() {
        //登录失败重新登录
        login();
    }

    /**
     * 选择头像
     */
    @Override
    public void selectAvatar() {
        if (builder == null) {
            builder = new PhotoSelectBuilder(getActivity());
        }
        builder
                //设置是否可多选
                .setMultiple(false)
                .setCropable(true)
                //裁剪宽度
                .setCropWidth(Strings.AVATAR_WH)
                //裁剪高度
                .setCropHeight(Strings.AVATAR_WH)
                //是否显示照相功能，仅支持单选模式
                .setShowCamera(true)
                //每行显示照片数量
                .setColumnSum(3)
                //照片横向间距
                .setHorizontalSpacing(DisplayUtil.dip2px(getActivity(), 2))
                //照片纵向间距
                .setVerticalSpacing(DisplayUtil.dip2px(getActivity(), 2))
                //占位底图
                .setPlaceholder(R.mipmap.icon)
                //框架title
                .setTitle(Strings.SELECT_AVATAR)
                //选择成功回调接口
                .setSelectedPhotoPath(new PhotoSelectBuilder.ISelectedPhotoPath() {
                    @Override
                    public void selectedResult(List<String> pathList) {
                        String url = pathList.get(0);

                        UploadImages.setUploadListener(new UploadImages.UploadListener() {
                            @Override
                            public void onFailure(String errorInfo) {
                                Log.e(TAG, errorInfo);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), Strings.AVATAR_UPLOAD_FAILURE
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(final String body) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (body.equals("null")) {
                                            Toast.makeText(getActivity(), Strings.AVATAR_UPLOAD_FAILURE
                                                    , Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), Strings.AVATAR_UPLOAD_SUCCESS
                                                    , Toast.LENGTH_SHORT).show();

                                            User temp = new Gson().fromJson(body, User.class);
                                            User.copy(temp);
                                        }

                                    }
                                });
                            }
                        });
                        UploadImages.uploadAvatar(new File(url)
                                .getAbsolutePath().replace("/file:", ""));

                        Glide.with(getActivity())
                                .load(url)
                                .asBitmap()
                                .signature(new StringSignature(
                                        String.valueOf(System.currentTimeMillis())))
                                .placeholder(R.mipmap.icon)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        avatar.setImageBitmap(resource);
                                    }
                                });
                    }
                });

        //设置照片裁剪存放Uri
        File fileCropUri = new File(Environment
                .getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
        builder.setCropUri(Uri.fromFile(fileCropUri));

        //设置拍照存放Uri
        File takePhotoFile = new File(Environment
                .getExternalStorageDirectory().getPath() + "/take_photo.jpg");
        builder.setPhotoUri(Uri.fromFile(takePhotoFile));

        //创建
        builder.create();
    }

    /**
     * 设置用户信息
     */
    @Override
    public void setUserInfo() {
        User user = User.getInstance();
        //设置头像
        Glide.with(this)
                .load(SiteInfo.HOST_URL + user.getAvatar())
                .asBitmap()
                .signature(new StringSignature(
                        String.valueOf(System.currentTimeMillis())))
                .placeholder(R.mipmap.icon)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        avatar.setImageBitmap(resource);
                    }
                });

        if (user.getNickname() != null) {
            nickname.setText(user.getNickname());
        }
    }

    @Override
    @OnClick(R.id.avatar)
    public void avatar() {
        if (User.getInstance().getPhone() == null) {
            LoginActivity.newInstance(getActivity());
            return;
        } else {
            //选择头像
            selectAvatar();
        }
    }

    /**
     * 显示修改昵称弹框
     */
    @Override
    @OnClick(R.id.nickname)
    public void showAlterNickname() {
        if (User.getInstance().getPhone() == null) {
            LoginActivity.newInstance(getActivity());
            return;
        }
        alterNickname.setVisibility(View.VISIBLE);
        mainActivity.hiddenBottomView();
        nicknameInput.setText(nickname.getText().toString());
    }

    /**
     * 隐藏修改昵称弹框
     */
    @Override
    @OnClick(R.id.cancel)
    public void hiddenAlterNickname() {
        alterNickname.setVisibility(View.GONE);
        mainActivity.showBottomView();

        //强制隐藏键盘
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(nicknameInput.getWindowToken(), 0);
        }

    }

    /**
     * 修改昵称
     */
    @Override
    @OnClick(R.id.alterNickname)
    public void alterNickname() {
        aboveAlterNickname = nickname.getText().toString();
        User.getInstance().setNickname(nicknameInput.getText().toString());
        presenter.alterNickname(getActivity());
    }

    /**
     * 退出登录更新
     */
    @Override
    public void exitLoginUpdate() {
        User.reset();
        //清除本地用户信息记录
        SharePreferUtils.storeDataByKey(getActivity(), Strings.USER_PHONE, null);

        avatar.setImageResource(R.mipmap.icon);
        nickname.setText(Strings.CLICK_ALTER_NICKNAME);

    }

}

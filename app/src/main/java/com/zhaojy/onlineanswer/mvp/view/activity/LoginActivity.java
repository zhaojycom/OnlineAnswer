package com.zhaojy.onlineanswer.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.mvp.contract.LoginActivityContract;
import com.zhaojy.onlineanswer.mvp.presenter.LoginActivityPresenter;
import com.zhaojy.onlineanswer.utils.CheckPhoneUtils;
import com.zhaojy.onlineanswer.utils.DimUtils;
import com.zhaojy.onlineanswer.utils.VerCodeUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * @author: zhaojy
 * @data:On 2018/9/19.
 */

public class LoginActivity extends BaseActivity implements LoginActivityContract.View {
    /**
     * 背景模糊度
     */
    private final static int DIM_RADIUS = 8;
    @BindView(R.id.bg)
    public ConstraintLayout bg;
    @BindView(R.id.verCode)
    public ImageView verCode;
    @BindView(R.id.phoneInput)
    public EditText phoneInput;
    @BindView(R.id.verCodeInput)
    public EditText verCodeInput;
    @BindView(R.id.clear)
    public ImageView clear;
    @BindView(R.id.loginBt)
    public TextView loginBt;
    /**
     * 生成图形验证码工具类
     */
    private VerCodeUtils vcu = VerCodeUtils.getInstance();
    private EventHandler eventHandler;
    @BindView(R.id.immediateAccess)
    public TextView immediateAccess;
    @BindView(R.id.msgVerCode)
    public TextView msgVerCode;

    private LoginActivityContract.Presenter presenter;

    private static LoginActivityContract.UpdateUserInfo updateUserInfo;

    public static void setUpdateUserInfo(LoginActivityContract.UpdateUserInfo updateUserInfo) {
        LoginActivity.updateUserInfo = updateUserInfo;
    }

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        presenter = new LoginActivityPresenter(this);
    }

    @Override
    protected void initView() {
        this.setStatusBarTranparent();
    }

    @Override
    protected void initViewListener() {

    }

    @Override
    protected void process() {
        //生成图片验证码
        generateVerCode();
        //设置短信发送
        setMessageSender();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.login;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //使用完EventHandler需注销，否则可能出现内存泄漏
        SMSSDK.unregisterEventHandler(eventHandler);
        presenter.onDestory();

        //清除焦点
        if (phoneInput != null) {
            phoneInput.clearFocus();
        }
        if (verCodeInput != null) {
            verCodeInput.clearFocus();
        }
        if (msgVerCode != null) {
            msgVerCode.clearFocus();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            //背景模糊处理
            dimBg();
        }
    }

    /**
     * 设置短信发送
     */
    @Override
    public void setMessageSender() {
        // 在尝试读取通信录时以弹窗提示用户（可选功能）
        SMSSDK.setAskPermisionOnReadContact(true);

        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                //发送成功回调
                                Toast.makeText(LoginActivity.this,
                                        Strings.MSG_SEND_SUCCESS, Toast.LENGTH_SHORT).show();
                            } else {

                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                //短信验证码提交成功回调
                                //提交登录注册信息
                                submitLoginRegisterInfo();
                            } else {

                                ((Throwable) data).printStackTrace();
                            }
                        }

                        return false;
                    }
                }).sendMessage(msg);
            }
        };
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 模糊背景处理
     */
    @Override
    public void dimBg() {
        Glide.with(this)
                .load(R.mipmap.login_bg)
                .asBitmap()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        WeakReference<Bitmap> overlay = new WeakReference<>
                                (DimUtils.getDimBitmap(resource, DIM_RADIUS));
                        Drawable drawable = new BitmapDrawable(overlay.get());
                        bg.setBackground(drawable);
                    }
                });
    }

    /**
     * 产生验证码
     */
    @Override
    @OnClick(R.id.verCode)
    public void generateVerCode() {
        Bitmap codeBitmap = vcu.createBitmap();
        verCode.setImageBitmap(codeBitmap);
    }

    /**
     * 提交登录注册信息
     */
    @Override
    public void submitLoginRegisterInfo() {
        String verCodeStr = verCodeInput.getText().toString();
        if (verCodeStr.equals(vcu.getCode())) {
            //如果输入正确的验证码
            //提交注册信息
            //获取输入的手机号码
            final String phoneNumber = phoneInput.getText().toString();
            boolean isLegal = CheckPhoneUtils.isPhone(this, phoneNumber);
            if (isLegal) {
                presenter.submitLoginRegisterInfo(this, phoneNumber);
            }

        } else {
            //如果输入错误的验证码
            //产生新的验证码
            generateVerCode();
        }
    }

    @OnClick(R.id.clear)
    @Override
    public void clear() {
        phoneInput.setText("");
    }

    @OnClick(R.id.immediateAccess)
    @Override
    public void immediateAccess() {
        //获取输入的手机号码
        String phoneNumber = phoneInput.getText().toString();
        boolean isLegal = CheckPhoneUtils.isPhone(this, phoneNumber);
        if (isLegal) {
            // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
            SMSSDK.getVerificationCode(Strings.COUNTRY_CODE, phoneNumber);
        }
    }

    @OnClick(R.id.loginBt)
    @Override
    public void login() {
        //获取输入的手机号码
        String phoneNumber2 = phoneInput.getText().toString();
        //图形验证码
        String verCodeStr = verCodeInput.getText().toString();
        //短信验证码
        String msgVerCodeStr = msgVerCode.getText().toString();
        if (!CheckPhoneUtils.isPhone(this, phoneNumber2)) {
            return;
        } else if (verCodeStr.equals("")) {
            Toast.makeText(this, Strings.PLEASE_INPUT_VERCODE,
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (msgVerCodeStr.equals("")) {
            Toast.makeText(this, Strings.PLEASE_INPUT_MSG_CODE,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 提交验证码，其中的code表示验证码，如“1357”
        SMSSDK.submitVerificationCode(Strings.COUNTRY_CODE,
                phoneNumber2, msgVerCodeStr);
    }

    @Override
    public void loginSuccess() {
        updateUserInfo.setUserInfo();
        this.finish();
    }

    @Override
    public void loginFailure() {

    }

}

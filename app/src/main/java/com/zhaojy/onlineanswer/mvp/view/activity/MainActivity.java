package com.zhaojy.onlineanswer.mvp.view.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zhaojy.onlineanswer.R;
import com.zhaojy.onlineanswer.mvp.adapter.ViewPagerAdapter;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.helper.BottomNavigationViewHelper;
import com.zhaojy.onlineanswer.mvp.contract.MainActivityContract;
import com.zhaojy.onlineanswer.mvp.presenter.MainActivityPresenter;
import com.zhaojy.onlineanswer.mvp.view.fragment.MyFragment;
import com.zhaojy.onlineanswer.mvp.view.fragment.QuestionBankFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks
        , MainActivityContract.View {
    private final static int PERMISSION_REQUEST_CODE = 0x100;
    private MainActivityContract.Presenter presenter;
    @BindView(R.id.bottomNavigationView)
    public BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter viewPagerAdapter;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    private MenuItem menuItem;

    /**
     * 第一次按下返回键的时间
     */
    private long exitTime = 0;

    /**
     * 退出时间间隔
     */
    private final static int EXIT_TIME_GAP = 2000;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            // 将返回结果转给EasyPermissions
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions,
                    grantResults, this);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > EXIT_TIME_GAP) {
                Toast.makeText(this, Strings.PRESS_EXIT_PROCEDURE_AGAIN, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initData() {
        presenter = new MainActivityPresenter(this);
        presenter.initData();
    }

    @Override
    protected void initView() {
        //设置底部导航栏
        setBottomNavigation();
    }

    @Override
    protected void initViewListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        menuItem = item;
                        switch (item.getItemId()) {
                            case R.id.questionBank:
                                viewPager.setCurrentItem(0);
                                return true;
                            case R.id.my:
                                viewPager.setCurrentItem(2);
                                return true;
                            default:
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void process() {
        //申请权限
        applyPermission();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_main;
    }

    private void init() {

    }

    /**
     * 设置底部导航栏
     */
    @Override
    public void setBottomNavigation() {
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        List<Fragment> list = new ArrayList<>();
        list.add(new QuestionBankFragment());
        list.add(new MyFragment());
        viewPagerAdapter.setList(list);
    }

    /**
     * 申请权限
     */
    @Override
    public void applyPermission() {
        String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        //判断权限是否申请
        if (EasyPermissions.hasPermissions(this, permissions)) {
            // 已获取权限
            init();
        } else {
            // 没有权限，现在去获取
            EasyPermissions.requestPermissions(this, Strings.APPLY_PERMISSION,
                    PERMISSION_REQUEST_CODE, permissions);
        }

    }

    /**
     * 隐藏底部标签
     */
    @Override
    public void hiddenBottomView() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    /**
     * 显示底部标签
     */
    @Override
    public void showBottomView() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //获取权限
        init();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //拒绝权限，退出程序
        this.finish();
    }

}

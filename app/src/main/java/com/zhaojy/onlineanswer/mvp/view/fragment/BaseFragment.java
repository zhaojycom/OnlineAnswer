package com.zhaojy.onlineanswer.mvp.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * @author: zhaojy
 * @data:On 2018/10/14.
 */

public abstract class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    protected abstract int getPageLayoutID();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected void initViewListener() {
    }

    protected abstract void process(Bundle savedInstanceState);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getPageLayoutID(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        initView(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewListener();
        process(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && getContext() != null) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //启动内存泄漏检测
       /* RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}

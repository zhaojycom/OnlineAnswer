package com.zhaojy.onlineanswer.helper;

import com.zhaojy.onlineanswer.data.objectbox.SearchHistoryObjectBox;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2019/1/6.
 */
public class ExitLoginSubject implements IExitLoginSubject {
    private static IExitLoginSubject exitLoginSubject;
    private List<IExitLoginObserver> observerList;

    public static IExitLoginSubject getInstance() {
        if (exitLoginSubject == null) {
            exitLoginSubject = new ExitLoginSubject();
            exitLoginSubject.init();
        }

        return exitLoginSubject;
    }

    private ExitLoginSubject() {
        observerList = new ArrayList<>();
    }

    /**
     * 初始化部分观察者
     */
    @Override
    public void init() {
        //搜索历史
        SearchHistoryObjectBox historyObjectBox = SearchHistoryObjectBox.getInstance();
        this.registerObserver(historyObjectBox);
    }

    @Override
    public void registerObserver(IExitLoginObserver observer) {
        if (!observerList.contains(observer)) {
            observerList.add(observer);
        }

    }

    @Override
    public void removeObserver(IExitLoginObserver observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (IExitLoginObserver observer : observerList) {
            observer.exitLoginUpdate();
        }
    }
}

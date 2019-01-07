package com.zhaojy.onlineanswer.helper;

/**
 * @author: zhaojy
 * @data:On 2019/1/6.
 */
public interface IExitLoginSubject {

    void init();

    void registerObserver(IExitLoginObserver observer);

    void removeObserver(IExitLoginObserver observer);

    void notifyObserver();
}

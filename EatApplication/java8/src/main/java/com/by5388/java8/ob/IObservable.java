package com.by5388.java8.ob;

/**
 * @author Administrator  on 2019/9/10.
 */
public interface IObservable {
    void add(IObserver observer);

    void remove(IObserver observer);

    void notifyChange();
}

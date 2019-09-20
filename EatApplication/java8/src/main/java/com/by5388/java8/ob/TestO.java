package com.by5388.java8.ob;

/**
 * @author Administrator  on 2019/9/10.
 */
public class TestO {
    public static void main(String[] args) {
        final IObservable observable = new OObservable();
        final IObserver observer1 = new OObserverGuangdong();
        final IObserver observer2 = new OObserverFujian();
        observable.add(observer1);
        observable.add(observer2);

//        observable.notifyChange();
//
//        observable.remove(observer2);
//        observable.notifyChange();
//
//        observable.add(observer1);
//        observable.add(observer2);
//        observable.notifyChange();

        for (int i = 0; i < 3; i++) {
            observable.notifyChange();
        }
    }
}

package com.by5388.java8.ob;

/**
 * @author Administrator  on 2019/9/10.
 */
public class OObserverGuangdong implements IObserver {

    private static final String TAG = "OObserverGuangdong";
    private int mValue = -1;

    @Override
    public void update(int value) {
        if (value == -1 || value == mValue) {
            System.err.println("repeat value");
            return;
        }

        System.out.println(TAG + " newValue = " + value);
        mValue = value;
    }
}

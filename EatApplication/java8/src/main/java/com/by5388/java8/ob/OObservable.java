package com.by5388.java8.ob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Administrator  on 2019/9/10.
 */
public class OObservable implements IObservable {
    private List<IObserver> mIObservers = new ArrayList<>();
    private int mCurrentValue = 10;

    private Random mRandom = new Random();

    @Override
    public void add(IObserver observer) {
        if (mIObservers.contains(observer)) {
            return;
        }
        mIObservers.add(observer);
    }

    @Override
    public void remove(IObserver observer) {
        final int index = mIObservers.indexOf(observer);
        if (index == -1) {
            return;
        }
        mIObservers.remove(index);
    }

    @Override
    public void notifyChange() {
        final int nextInt = mRandom.nextInt(mCurrentValue + 10);
        if (nextInt != mCurrentValue) {
            for (IObserver observer : mIObservers) {
                observer.update(nextInt);
            }
            mCurrentValue = nextInt;
        }
    }
}

package com.eat.temp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Administrator  on 2019/8/22.
 */
class ConsumerProducer {
    private static final int LIMIT = 10;
    private BlockingQueue<Integer> mBlockingQueue = new LinkedBlockingQueue<>(LIMIT);

    void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            mBlockingQueue.put(value++);
        }
    }

    void consume() throws InterruptedException {
        while (true) {
            final Integer take = mBlockingQueue.take();
        }
    }
}

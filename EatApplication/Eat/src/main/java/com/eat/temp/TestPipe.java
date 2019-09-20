package com.eat.temp;

import android.app.PendingIntent;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author Administrator  on 2019/8/21.
 */
public class TestPipe {
    /**
     * 管道
     */
    private void testPipe() {
        //传输 字符,传输字符流
        final PipedWriter writer = new PipedWriter();
        final PipedReader reader = new PipedReader();

        try {
            writer.connect(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //传输 二进制 流？传输字节流
        final PipedOutputStream outputStream = new PipedOutputStream();
        final PipedInputStream inputStream = new PipedInputStream();
        try {
            outputStream.connect(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

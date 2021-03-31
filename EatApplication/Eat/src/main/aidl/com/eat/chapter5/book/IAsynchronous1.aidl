// IAsynchronous1.aidl
package com.eat.chapter5.book;

// Declare any non-default types here with import statements
import com.eat.chapter5.book.IAsynchronousCallback;

interface IAsynchronous1 {
    //引用到其他AIDL文件，即使是同一个包，也要import 进来
    oneway void getThreadNameSlow(IAsynchronousCallback callback);
}

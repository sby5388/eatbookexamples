// ISynchronous.aidl
package com.eat.chapter5.book;

// Declare any non-default types here with import statements

interface ISynchronous {

    String getThreadNameFast();

    String getThreadNameSlow(long sleep);

    String getThreadNameBlocking();

    String getThreadNameUnlock();

}

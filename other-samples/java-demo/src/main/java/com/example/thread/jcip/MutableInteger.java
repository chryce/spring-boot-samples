package com.example.thread.jcip;

import com.example.thread.jcip.annotations.NotThreadSafe;

/**
 * MutableInteger
 * <p/>
 * Non-thread-safe mutable integer holder
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class MutableInteger {

    private int value;

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }
}









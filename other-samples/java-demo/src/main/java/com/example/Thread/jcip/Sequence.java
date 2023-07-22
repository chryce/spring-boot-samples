package com.example.Thread.jcip;

import com.example.Thread.jcip.annotations.*;

/**
 * Sequence
 *
 * @author Brian Goetz and Tim Peierls
 */

@ThreadSafe
public class Sequence {

  @GuardedBy("this")
  private int nextValue;

  public synchronized int getNext() {
    return nextValue++;
  }
}

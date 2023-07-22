package com.example.Spring.demoQ;

import java.util.Random;

public class NumberPrefixGenerator implements PrefixGenerator {

  public String getPrefix() {
    Random randomGenerator = new Random();
    int randomInt = randomGenerator.nextInt(100);
    return String.format("%03d", randomInt);
  }
}

package com.example.Spring.demoA;

import org.junit.Test;

public class SpringDemoA {

  @Test
  public void demo1() {
    CustomerDao customerDao = new CustomerDao();
    CustomerDao proxy = new CglibProxy(customerDao).createProxy();
    proxy.save();
    proxy.update();
    proxy.delete();
    proxy.find();
  }
}

package com.example.Spring.demoN.config;

import com.example.Spring.demoN.DatePrefixGenerator;
import com.example.Spring.demoN.NumberPrefixGenerator;
import com.example.Spring.demoN.SequenceGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SequenceConfiguration {

  @Bean
  public DatePrefixGenerator datePrefixGenerator() {
    DatePrefixGenerator dpg = new DatePrefixGenerator();
    dpg.setPattern("yyyyMMdd");
    return dpg;
  }

  @Bean
  public NumberPrefixGenerator numberPrefixGenerator() {
    return new NumberPrefixGenerator();
  }

  @Bean
  public SequenceGenerator sequenceGenerator() {
    SequenceGenerator sequence = new SequenceGenerator();
    sequence.setInitial(100000);
    sequence.setSuffix("A");
    return sequence;
  }
}

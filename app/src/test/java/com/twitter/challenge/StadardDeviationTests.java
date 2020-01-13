package com.twitter.challenge;

import org.assertj.core.data.Offset;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.within;

public class StadardDeviationTests {
  @Test
  public void testCalculateStandardDeviation() {
    final Offset<Double> precision = within(0.01d);
    List<Double> list = new ArrayList<>();

    list.add(1d);
    list.add(2d);
    list.add(3d);
    list.add(4d);
    list.add(5d);
    assertThat(StandardDeviationCalculator.calculatorStandardDeviation(list)).isEqualTo(1.4142, precision);

    list.clear();
    list.add(1d);
    list.add(1d);
    list.add(2d);
    list.add(2d);
    list.add(3d);
    list.add(3d);
    assertThat(StandardDeviationCalculator.calculatorStandardDeviation(list)).isEqualTo(0.8164, precision);
  }
}

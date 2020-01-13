package com.twitter.challenge;

import java.util.List;

public class StandardDeviationCalculator {

  public static double calculatorStandardDeviation(List<Double> list) {
    double sum = 0.0, standardDeviation = 0.0;
    int length = list.size();
    for(double num : list) {
      sum += num;
    }
    double mean = sum/length;
    for(double num: list) {
      standardDeviation += Math.pow(num - mean, 2);
    }
    return Math.sqrt(standardDeviation/length);
  }
}

package data_generator;

import org.apache.commons.math3.analysis.function.Gaussian;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import java.util.Random;

public class NumericAttribute extends Attribute {

  private final double mean, standardDeviation;

  public NumericAttribute(String name, double mean, double standardDeviation) {
    super(name);
    this.mean = mean;
    this.standardDeviation = standardDeviation;
  }

  public double getMean() {
    return mean;
  }

  public double getStandardDeviation() {
    return standardDeviation;
  }

  @Override
  public double generateValue() {
    Random rnd = new Random();
    double mean = getMean();
    double sd = getStandardDeviation();
    return rnd.nextGaussian()*sd+mean;
  }

}

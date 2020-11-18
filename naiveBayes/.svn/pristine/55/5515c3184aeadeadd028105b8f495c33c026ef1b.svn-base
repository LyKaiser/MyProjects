package naive_bayes.attribute;

public class NumAttribute implements Attribute {
	
  private double min;
  private double max;

  public NumAttribute() {
    this.min = Double.MAX_VALUE;
    this.max = Double.MIN_VALUE;
  }

  public NumAttribute(double min, double max) {
    this.min = min;
    this.max = max;
  }

  public void setMin(double min) {
    if (min < this.min)
      this.min = min;
  }

  public void setMax(double max) {
    if (max > this.max)
      this.max = max;
  }

  public double getMin() {
    return min;
  }

  public double getMax() {
    return max;
  }

  @Override
  public String toString() {
    return "min: " + min + ", max: " + max;
  }

  @Override
  public <T> T accept(AttributeVisitor<T> visitor) {
    return visitor.visit(this);
  }
}

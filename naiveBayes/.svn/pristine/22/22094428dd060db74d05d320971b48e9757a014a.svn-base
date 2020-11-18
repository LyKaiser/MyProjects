package naive_bayes.value;

public class NumValue implements Value {

  private final double value;

  public NumValue(double value) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  @Override
	public <T> T accept(ValueVisitor<T> v) {
    return v.visit(this);
  }

  @Override
  public String toString() {
    return Double.toString(value);
  }
}
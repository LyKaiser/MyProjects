package naive_bayes.value;

public class ChoiceValue implements Value {

  private final String value;

  public ChoiceValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public <T> T accept(ValueVisitor<T> v) {
    return v.visit(this);
  }

  @Override
  public String toString() {
    return value;
  }
}

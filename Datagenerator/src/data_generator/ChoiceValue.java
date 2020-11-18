package data_generator;

public class ChoiceValue {

  private final String name;

  private final double probability;

  public ChoiceValue(String name, double probability) {
    this.name = name;
    this.probability = probability;
  }

  public String getName() {
    return name;
  }

  public double getProbability() {
    return probability;
  }
}

package data_generator;

public abstract class Attribute {

  private final String name;

  public Attribute(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public abstract double generateValue();

}

package data_generator;

public abstract class AttributeTemplate {

  private final String name;

  private final int startColumn;

  public AttributeTemplate(String name, int startColumn) {
    this.name = name;
    this.startColumn = startColumn;
  }

  public String getName() {
    return name;
  }

  public int getStartColumn() {
    return startColumn;
  }

}

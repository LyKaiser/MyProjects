package cbr.attribute;

import java.util.HashSet;
import java.util.Set;

public class ChoiceAttribute implements Attribute {

  private final Set<String> possibleValues;

  public ChoiceAttribute() {
    this.possibleValues = new HashSet<>();
  }

  public void addPossibleValue(String aPossibleValue) {
    possibleValues.add(aPossibleValue);
  }

  public ChoiceAttribute(Set<String> possibleValues) {
    this.possibleValues = possibleValues;
  }

  public Set<String> getPossibleValues() {
    return possibleValues;
  }

  @Override
  public <T> T accept(AttributeVisitor<T> v) {
    return v.visit(this);
  }

  @Override
  public String toString() {
    return possibleValues.toString();
  }

}
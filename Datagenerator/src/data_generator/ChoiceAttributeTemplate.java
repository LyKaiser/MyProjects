package data_generator;

import java.util.ArrayList;
import java.util.List;

public class ChoiceAttributeTemplate extends AttributeTemplate {

  private final List<String> choiceNames;

  public ChoiceAttributeTemplate(String name, int startColumn, String firstChoiceName) {
    super(name, startColumn);
    this.choiceNames = new ArrayList<>();
    choiceNames.add(firstChoiceName);
  }

  public void addChoiceName(String choiceName) {
    choiceNames.add(choiceName);
  }

  public List<String> getChoiceNames() {
    return choiceNames;
  }

}

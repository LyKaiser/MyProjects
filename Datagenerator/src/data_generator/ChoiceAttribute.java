package data_generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChoiceAttribute extends Attribute {

  private final List<ChoiceValue> possibleValues;

  public ChoiceAttribute(String name, List<ChoiceValue> possibleValues) {
    super(name);
    this.possibleValues = possibleValues;
  }

  public List<ChoiceValue> getPossibleValues() {
    return possibleValues;
  }

  @Override
  public double generateValue() {
    List<ChoiceValue> choices = getPossibleValues();
    //int choiceLength = choices.size();
    int counter = 0;
    List<Integer> sumChoices = new ArrayList<>();
    for (ChoiceValue choice : choices){
      double prop = choice.getProbability()*100;
      for (int i = 1; i <= prop; i++){
        sumChoices.add(counter);
      }
      counter++;
    }
    Random r = new Random();
    int low = 0;
    int high = sumChoices.size();
    int rnd = r.nextInt(high-low) + low;
    return sumChoices.get(rnd);
  }
}

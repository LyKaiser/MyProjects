package cbr.attribute.distance;

import cbr.value.ChoiceValue;
import cbr.value.Value;

public class NominalAttributeDistance implements AttributeDistance {

    public NominalAttributeDistance() {
    }

    @Override
    public double distance(Value v1, Value v2) {
        if (v1 instanceof ChoiceValue && v2 instanceof ChoiceValue) {
            if (((ChoiceValue) v1).getValue().equals(((ChoiceValue) v2).getValue())) {
                return 0;
            } else return 1;
        }
        return 1;
    }

}

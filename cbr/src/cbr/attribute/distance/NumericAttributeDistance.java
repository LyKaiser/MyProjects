package cbr.attribute.distance;

import cbr.attribute.Attribute;
import cbr.attribute.AttributeVisitor;
import cbr.attribute.NumAttribute;
import cbr.value.ChoiceValue;
import cbr.value.NumValue;
import cbr.value.Value;

public class NumericAttributeDistance implements AttributeDistance {
    NumAttribute attribute;

    public NumericAttributeDistance(NumAttribute attr) {
        this.attribute = attr;
    }

    @Override
    public double distance(Value v1, Value v2) {
        if (v1 instanceof NumValue && v2 instanceof NumValue) {
            double dist = 0;
            if (((NumValue) v1).getValue() < ((NumValue) v2).getValue()) {
                dist = ((NumValue) v2).getValue() - ((NumValue) v1).getValue();
            } else if (((NumValue) v1).getValue() > ((NumValue) v2).getValue()) {
                dist = ((NumValue) v1).getValue() - ((NumValue) v2).getValue();
            }
            return dist / (attribute.getMax() - attribute.getMin());
        }
        return 1;
    }

}

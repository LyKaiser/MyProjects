package cbr.instance.distance;

import cbr.attribute.Attribute;
import cbr.attribute.NumAttribute;
import cbr.attribute.distance.NominalAttributeDistance;
import cbr.attribute.distance.NumericAttributeDistance;
import cbr.attribute.distance.OrdinalAttributeDistance;
import cbr.instance.DataInstance;
import cbr.value.ChoiceValue;
import cbr.value.NumValue;
import cbr.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleDistanceFunction implements InstanceDistance {
    Map<String, Attribute> attributes;
    HashMap<String, HashMap<Set<String>, Double>> attrNames2distances;

    public SimpleDistanceFunction(Map<String, Attribute> attributes,
                                  HashMap<String, HashMap<Set<String>, Double>> attrNames2distances) {
        this.attributes = attributes;
        this.attrNames2distances = attrNames2distances;
    }

    @Override
    public double computeDistance(DataInstance dataInstances1, DataInstance dataInstances2) {
        Map<String, Value> data1 = dataInstances1.getData();
        Map<String, Value> data2 = dataInstances2.getData();

        double result = 0;
        for (String str1 : data1.keySet()) {
            for (String str2 : data2.keySet()) {
                if (str1.equals(str2)) {
                    if (data1.get(str1) instanceof ChoiceValue) {
                        ChoiceValue val = (ChoiceValue) data1.get(str1);
                        if (attrNames2distances.containsKey(val.getValue())) {
                            result += new OrdinalAttributeDistance(attrNames2distances.get(val.getValue())).distance(data1.get(str1), data2.get(str2));
                        } else {
                            result += new NominalAttributeDistance().distance(data1.get(str1), data2.get(str2));
                        }
                    } else if (data1.get(str1) instanceof NumValue) {
                        result += new NumericAttributeDistance((NumAttribute) attributes.get(str1)).distance(data1.get(str1), data2.get(str2));
                    }
                }
            }
            /*if (attrNames2distances.containsKey(str1)) {
                for (String str2 : data2.keySet()) {
                    for (Set<String> stringSet : attrNames2distances.get(str1).keySet()) {
                        if (stringSet.contains(str2)) {
                            result += attrNames2distances.get(str1).get(stringSet);
                        }
                    }
                }
            }*/
        }
        return result;

    }

    @Override
    public double getWeight(String attrName) {
        return 1;
    }

}

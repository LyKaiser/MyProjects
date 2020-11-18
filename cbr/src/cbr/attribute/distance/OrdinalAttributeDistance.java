package cbr.attribute.distance;

import cbr.Main;
import cbr.value.ChoiceValue;
import cbr.value.Value;

import java.util.HashMap;
import java.util.Set;

public class OrdinalAttributeDistance implements AttributeDistance {
    HashMap<Set<String>, Double> map;

    public OrdinalAttributeDistance(HashMap<Set<String>, Double> values2distance) {
        this.map = values2distance;
    }
  /*public OrdinalAttributeDistance(HashMap<String, HashMap<Set<String>, Double>> values2distance){
  this.map =values2distance;
  }*/

    @Override
    public double distance(Value v1, Value v2) {
        if (v1 instanceof ChoiceValue && v2 instanceof ChoiceValue) {
            Set<Set<String>> sets = map.keySet();
            for (Set<String> set : sets) {
                if (set.contains(((ChoiceValue) v1).getValue()) && set.contains(((ChoiceValue) v2).getValue())&&!((ChoiceValue) v1).getValue().equals(((ChoiceValue) v2).getValue())) {
                    return map.get(set);
                }
            }
            if (((ChoiceValue) v1).getValue().equals(((ChoiceValue) v2).getValue())) {
                return 0;
            } else return 1;
        }
        return 1;
        /*HashMap<String, HashMap<Set<String>, Double>> map = Main.getAttrNames2distance();
        if (v1 instanceof ChoiceValue && v2 instanceof ChoiceValue) {
            if (map.containsKey(((ChoiceValue) v1).getValue())) {
                HashMap<Set<String>, Double> distMap = map.get(((ChoiceValue) v1).getValue());
                if (distMap.containsKey(v2)) {
                    return distMap.get(v2);
                }
            } else if (map.containsKey(((ChoiceValue) v2).getValue())) {
                HashMap<Set<String>, Double> distMap = map.get(((ChoiceValue) v2).getValue());
                if (distMap.containsKey(v1)) {
                    return distMap.get(v1);
                }
            } else {
                if (((ChoiceValue) v1).getValue().equals(((ChoiceValue) v2).getValue())) {
                    return 0;
                } else return 1;
            }
        }*/

    }
}

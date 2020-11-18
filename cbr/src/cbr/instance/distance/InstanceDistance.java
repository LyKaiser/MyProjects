package cbr.instance.distance;

import cbr.instance.DataInstance;

public interface InstanceDistance {

  double computeDistance(DataInstance dataInstances1, DataInstance dataInstances2);

  double getWeight(String attrName);

}

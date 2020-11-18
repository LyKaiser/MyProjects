package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.EstimationFunction;
import de.jpp.model.XYNode;

public class ATest implements EstimationFunction<XYNode>{


    @Override
    public double getEstimatedDistance(XYNode node, XYNode destination) {
        double distx = node.getX()-destination.getX();
        double disty = node.getY()-destination.getY();
        return Math.sqrt(Math.pow(distx,2.0)+Math.pow(disty,2.0));

    }
}

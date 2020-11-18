package de.jpp.model;

import java.util.Objects;

public class XYNode {

    private String label;
    private double x;
    private double y;

    /**
     * Creates a new XYNode with the specified label and coordinate
     *
     * @param label the label
     * @param x     the x value of the coordinate
     * @param y     the y value of the coordinate
     */
    public XYNode(String label, double x, double y) {
        this.label=label;
        this.x=x;
        this.y=y;
    }

    /**
     * Returns the label of this node
     * @return
     */
    public String getLabel() {

        return label;
    }

    /**
     * Returns the x coordinate of this node
     * @return
     */
    public double getX() {

       return x;
    }

    /**
     * Returns the y coordinate of this node
     * @return
     */
    public double getY() {

        return y;
    }

    /**
     * Calculates the euclidian distance to the specified XYNode
     *
     * @param other the node to calculate the distance to
     * @return the euclidian distance to the specified XYNode
     */
    public double euclidianDistTo(XYNode other) {
        double distx = x-other.x;
        double disty = y-other.y;
        return Math.sqrt(Math.pow(distx,2.0)+Math.pow(disty,2.0));
    }

    @Override
    public String toString() {
        return "XYNode{" +
                "label='" + label + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XYNode xyNode = (XYNode) o;
        return Double.compare(xyNode.x, x) == 0 &&
                Double.compare(xyNode.y, y) == 0 &&
                Objects.equals(label, xyNode.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, x, y);
    }
}

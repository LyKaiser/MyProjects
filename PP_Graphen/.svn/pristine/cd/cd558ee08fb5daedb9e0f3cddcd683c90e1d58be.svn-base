package de.jpp.io;

import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;
import org.jdom2.Element;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class TwoDimGraphImageReader implements GraphReader<XYNode, Double, TwoDimGraph, BufferedImage> {

    @Override
    public TwoDimGraph read(BufferedImage input) throws ParseException {
        TwoDimGraph graph = new TwoDimGraph();
        int height = input.getHeight();
        int width = input.getWidth();

        Map<XYNode, Integer> nodes = new HashMap<>();
        int counterNode = 0;
        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                Color mycolor = new Color(input.getRGB(i,j));
                int red=mycolor.getRed();
                int green=mycolor.getGreen();
                int blue=mycolor.getBlue();
                float[] hsb =Color.RGBtoHSB(red, green, blue, null);
                float brightness = hsb[2];
                if (brightness>=0.5) {
                    counterNode++;
                    XYNode node = new XYNode("n" + counterNode, i, j);
                    nodes.put(node, counterNode);
                    graph.addNode(node);
                }
            }
        }
        for (XYNode node : nodes.keySet()) {
            double x = node.getX();
            double y = node.getY();
            for (XYNode no : nodes.keySet()) {
                if (no != node) {
                    double xA = no.getX();
                    double yA = no.getY();
                    if (x == (xA) && (y - 1) == yA && y > 0) {
                        graph.addEdge(no, node, Optional.of(1.0));
                        graph.addEdge(node, no, Optional.of(1.0));
                    }
                    if (x == (xA) && (y + 1) == yA && y < width) {
                        graph.addEdge(no, node, Optional.of(1.0));
                        graph.addEdge(node, no, Optional.of(1.0));
                    }
                    if ((x + 1) == (xA) && y == yA && x < height) {
                        graph.addEdge(no, node, Optional.of(1.0));
                        graph.addEdge(node, no, Optional.of(1.0));
                    }
                    if ((x - 1) == (xA) && y == yA && x > 0) {
                        graph.addEdge(no, node, Optional.of(1.0));
                        graph.addEdge(node, no, Optional.of(1.0));
                    }
                }
            }
        }

        return graph;
    }

}

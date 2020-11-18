package de.jpp.io;

import de.jpp.io.interfaces.GraphIO;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Edge;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TwoDimGraphDotIO implements GraphIO<XYNode, Double, TwoDimGraph, String> {
    private TwoDimGraph two = new TwoDimGraph();
    private Map<Integer, XYNode> nodes = new HashMap<>();
    private List<Edge<XYNode, Double>> edges = new ArrayList<>();

    public TwoDimGraphDotIO() {
    }

    @Override
    public TwoDimGraph read(String input) throws ParseException {

        String[] zeilen = input.split("\n");
        for (String str : zeilen) {
            parseLine(str);
        }
        Collection<XYNode> knoten = nodes.values();
        two.addNodes(knoten);
        if (two.getNodes().isEmpty()){
            throw new ParseException();
        }
        return two;
    }

    public void parseLine(String string) throws ParseException {

        if (string.contains("x") && string.contains("y") && string.contains("label")) {
            parseNode(string);
        } else if (string.contains("->") && string.contains("dist")) {
            parseEdge(string);
        }

    }

    public void parseNode(String string) throws ParseException {

        try {
            String regexID = "\\d+";
            String id = matcher(regexID, string);
            String[] idSplit = id.split(",");
            Integer idWert = Integer.parseInt(idSplit[0]);
            String regexX = "x=[0-9]+";
            String x = matcher(regexX, string);
            Double xWert=null;
            if (x.contains(",")){
                String[] split = x.split(",");
                String[] split1 =split[0].split("=");
                xWert = Double.parseDouble(split1[1]);
            }else{
                String[] xSplit = x.split("=");
                xWert = Double.parseDouble(xSplit[1]);
            }
            String regexY = "y=[0-9]+";
            String y = matcher(regexY, string);
            Double yWert=null;
            if (y.contains(",")){
                String[] split = y.split(",");
                String[] split1 =split[0].split("=");
                yWert = Double.parseDouble(split1[1]);
            }else{
                String[] ySplit = y.split("=");
                yWert = Double.parseDouble(ySplit[1]);
            }
            String regexLabel = "\"([^\"]*)\"|label=[\\w]*";
            String label = matcher(regexLabel, string);
            String labelWert=null;
            if (label.contains("\"")){
                String[] labelSplit=label.split("\"",-1);
                if (labelSplit[1]==null){
                    labelWert="";
                }else labelWert=labelSplit[1];

            }else {
                String[] labelSplit = label.split("=");
                labelWert = labelSplit[1];
            }
            XYNode nodeEnd = new XYNode(labelWert, xWert, yWert);
            nodes.put(idWert, nodeEnd);
        } catch (Exception e) {
            throw new ParseException();
        }


    }

    public void parseEdge(String string) throws ParseException {
        try {

            String regexDist = "dist=[0-9.]*";
            String dist = matcher(regexDist, string);
            String[] distSplit = dist.split("=");
            Double distWert = Double.parseDouble(distSplit[1]);
            String regexBeideID = "\\d+";
            String beideID = matcher(regexBeideID, string);
            String[] beideIDSplit = beideID.split(",");
            Integer knoten1 = Integer.parseInt(beideIDSplit[0]);
            Integer knoten2 = Integer.parseInt(beideIDSplit[1]);

            if (nodes.get(knoten1) != null && nodes.get(knoten2) != null) {
                Edge<XYNode, Double> res = new Edge<>(nodes.get(knoten1), nodes.get(knoten2), Optional.of(distWert));
                edges.add(res);

                two.addEdge(nodes.get(knoten1), nodes.get(knoten2), Optional.of(distWert));
            }
        } catch (Exception e) {
            throw new ParseException();
        }
    }


    public String matcher(String regex, String str) {
        String ergebnis = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            if (regex.equals("\"([^\"]*)\"|label=[\\w]*")){
                sb.append(str.substring(matcher.start(), matcher.end()));
            }else {
                sb.append(",");
                sb.append(str.substring(matcher.start(), matcher.end()));
            }
        }
        if (!regex.equals("\"([^\"]*)\"|label=[\\w]*")){
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    @Override
    public String write(TwoDimGraph graph) {

        StringBuilder sb = new StringBuilder();
        sb.append("digraph{\n");
        Collection<XYNode> knoten = graph.getNodes();
        Map<XYNode, Integer> idKnoten = new HashMap<>();
        int counter = 0;
        for (XYNode kn : knoten) {
            counter++;
            idKnoten.put(kn, counter);
            String s = counter + " [label=" + kn.getLabel() + " x=" + kn.getX() + " y=" + kn.getY() + "]\n";
            sb.append(s);
        }
        Collection<Edge<XYNode, Double>> kanten = graph.getEdges();
        for (Edge<XYNode, Double> kan : kanten) {
            int idStart = idKnoten.get(kan.getStart());
            int idEnd = idKnoten.get(kan.getDestination());

            double dist = 0;
            String s;
            if (kan.getAnnotation().isPresent()) {
                dist = kan.getAnnotation().get();
                s = idStart + " -> " + idEnd + " [dist=" + dist + "]\n";
            } else {
                s = idStart + " -> " + idEnd + " [dist=Optional.null]\n";
            }
            sb.append(s);
        }
        sb.append("}\n");
        return sb.toString();
    }

    /*public static void main(String[] args) throws Exception {
       String str = "1 []";
       TwoDimGraphDotIO two = new TwoDimGraphDotIO();
       two.read(str);
        //"digraph{	1 [x=1 y=0 label=\"\"]";
                //"digraph{\n" + "\t1 [x=0 y=1 label=\"annoying      1 -> 7  1 [x===6][197]}\\//\"]\n";
        "digraph{\n" +
                "\t1    [label=n16 x=50.0 y=450.0]\n" +
                "\t2 [label=n7 x=300.0 y=350.0  ]\n" +
                "\t3 [label=n1 x=100.0 y=100.0]\n" +
                "\t5 [label=n4 x=50.0 y=200.0]\n" +
                "\t4 [label=n11 x=450.0 y=250.0]\n" +
                "\t6 [label=n8 x=200.0 y=350.0]\n" +
                "\t7 [label=n3 x=150.0 y=250.0]\n" +
                "\t8 [label=n10 x=100.0 y=300.0]\n" +
                "\t9 [label=n15 x=150.0 y=450.0]\n" +
                "\t10 [label=n18 x=150.0 y=550.0]\n" +
                "\t11 [label=n6 x=350.0 y=250.0]\n" +
                "\t12  [label=n13      x=450.0 y=450.0]\n" +
                "\t13 [x=350.0 y=450.0     label=n14]\n" +
                "\t14 [label=n12 x=500.0 y=350.0]\n" +
                "\t15 [label=n9 x=200.0 y=100.0]\n" +
                "\t16 [label=n17 y=550.0 x=50.0]\n" +
                "\t17 [label=n2 x=200.0 y=150.0]\n" +
                "\t18 [label=n5 x=300.0 y=150.0]\n" +
                "\n" +
                "\t1 -> 16 [ dist=1.0]\n" +
                "\t2 -> 11 [dist=1.0 ]\n" +
                "\t2 -> 6 [dist=1.0]\n" +
                "\t2 -> 13 [dist=1.0 ]\n" +
                "\t3 -> 17 [dist=1.0]\n" +
                "\t3 -> 5 [dist=1.0]\n" +
                "\t3 -> 15 [dist=1.0]\n" +
                "\t4 -> 11 [dist=1.0]\n" +
                "\t4 -> 14 [dist=1.0]\n" +
                "\t5 -> 7 [dist=1.0]\n" +
                "\t5 -> 3 [dist=1.0]\n" +
                "\t5 -> 8 [dist=1.0]\n" +
                "\t6 -> 2 [dist=1.0]\n" +
                "\t6 -> 7 [dist=1.0]\n" +
                "\t6 -> 8 [dist=1.0]\n" +
                "\t6 -> 9 [dist=1.0]\n" +
                "\t7 -> 17 [dist=1.0]\n" +
                "\t7 -> 5 [dist=1.0]\n" +
                "\t8 -> 6 [dist=1.0]\n" +
                "\t9 -> 1 [dist=1.0]\n" +
                "\t10 -> 9 [dist=1.0]\n" +
                "\t11 -> 18 [dist=1.0]\n" +
                "\t11 -> 2 [dist=1.0]\n" +
                "\t18 -> 15 [dist=1.0]\n" +
                "\t11 -> 4 [dist=1.0]\n" +
                "\t12 -> 14 [dist=1.0]\n" +
                "\t12 -> 13 [dist=1.0]\n" +
                "\t13 -> 12 [dist=1.0]\n" +
                "\t13 -> 2 [dist=1.0]\n" +
                "\t14 -> 4 [dist=1.0]\t\n" +
                "\t14 -> 12 [dist=1.0]\n" +
                "\t15 -> 18 [dist=1.0]\n" +
                "\t16 -> 10 [dist=1.0]\n" +
                "\t17 -> 3 [dist=1.0]\n" +
                "\t17 -> 7 [dist=1.0]\n" +
                "\t18 -> 17 [dist=1.0]\n" +
                "\t18 -> 11 [dist=1.0]\n" +
                "}\n";
        // "\t1 -> 16 [ dist=1.0]\n";
        //"\t100    [label=n16 x=50.0 y=450.0]\\n";

        TwoDimGraphDotIO twodim = new TwoDimGraphDotIO();
        twodim.read(str);
        System.out.println(twodim.nodes);
        System.out.println(twodim.edges);
        TwoDimGraph tw = new TwoDimGraph();
        XYNode node = new XYNode("n1", 50.0, 450.0);
        XYNode node1 = new XYNode("n2", 550.0, 50.0);
        XYNode node2 = new XYNode("n3", 150.0, 450.0);
        XYNode node3 = new XYNode("n4", 150.0, 550.0);
        tw.addNode(node);
        tw.addNode(node1);
        tw.addNode(node2);
        tw.addNode(node3);
        tw.addEdge(node, node1, Optional.of(1.0));
        tw.addEdge(node1, node3, Optional.of(1.0));
        tw.addEdge(node3, node2, Optional.of(1.0));
        tw.addEdge(node2, node, Optional.empty());

        String res = twodim.write(tw);
        System.out.println(res);


                //"1 -> 16 [ dist=1.0]";
                //"13 [x=350.0 y=450.0     label=n14]";
                //"2 [label=n7 x=300.0 y=350.0  ]";
                //"1 [x=0 y=1 label=\"annoying      1 -> 7  1 [x===6][197]}\\//]";

        List<String> ergebnis= new ArrayList<>();
        //String regex = "x=[0-9.]*";
        //String regex = "y=[0-9.]*";
        //String regex = "label=\"[\\w]*|label=[\\w]*";
        String regex = "\\d+";
        //String regex = "^[0-9].->.[0-9]*";


        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            ergebnis.add(str.substring(
                    matcher.start(), matcher.end()));
        }


        System.out.println(ergebnis);

    }*/


}

package de.jpp.io;


import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Edge;
import org.jdom2.Element;

import java.util.HashMap;
import java.util.Map;

public class TwoDimGraphGxlWriter extends GxlWriterTemplate<XYNode,Double, TwoDimGraph,String> {
    private int counterNode =0;
    private int counterEdge=0;
    private Map<XYNode,Integer> ids=new HashMap<>();

    public TwoDimGraphGxlWriter() {
    }

    @Override
    public Element writeNode(XYNode node) {
        counterNode++;
        ids.put(node,counterNode);
        //Element nodeElement = new Element("node").setText("id=id"+ counterNode);
        //nodeElement.addContent(new Element("id","n"+ counterNode));
        Element nodeEl = new Element("node");
        nodeEl.setAttribute("id","id"+ counterNode);
        Element attr = new Element("attr");
        Element string = new Element("string");
        string.setText(java.lang.String.valueOf(node.getLabel()));
        attr.addContent(string);
        attr.setAttribute("name", "description");
        nodeEl.addContent(attr);
        Element attr2 = new Element("attr");
        Element intx = new Element("int");
        intx.setText(java.lang.String.valueOf(node.getX()));
        attr2.addContent(intx);
        attr2.setAttribute("name","x");
        nodeEl.addContent(attr2);
        Element attr3 = new Element("attr");
        Element inty = new Element("int");
        inty.setText(java.lang.String.valueOf(node.getY()));
        attr3.addContent(inty);
        attr3.setAttribute("name","y");
        nodeEl.addContent(attr3);

        return nodeEl;
    }

    @Override
    public Element writeEdge(Edge<XYNode, Double> edge) {
        counterEdge++;
        XYNode start = edge.getStart();
        XYNode dest = edge.getDestination();
        Double annoDou;
        java.lang.String annoStr;
        if (edge.getAnnotation().isPresent()){
            annoDou=edge.getAnnotation().get();
            annoStr= java.lang.String.valueOf(annoDou);
        }else annoStr="";
        Element edgeEl = new Element("edge");
        edgeEl.setAttribute("id","id"+counterEdge);
        edgeEl.setAttribute("from","id"+calculateIdNode(start));
        edgeEl.setAttribute("to","id"+calculateIdNode(dest));
        Element attr = new Element("attr");
        Element string = new Element("string");
        string.setText("");
        attr.addContent(string);
        attr.setAttribute("name","description");
        edgeEl.addContent(attr);
        Element attr2 = new Element("attr");
        Element intx = new Element("double");
        intx.setText(annoStr);
        attr2.addContent(intx);
        attr2.setAttribute("name","cost");
        edgeEl.addContent(attr2);

        return edgeEl;
    }

    @Override
    public String calculateIdEdge(Edge<XYNode, Double> edge) {
        return null;
    }

    @Override
    public String calculateIdNode(XYNode node) {
        return String.valueOf(ids.get(node));
    }
}



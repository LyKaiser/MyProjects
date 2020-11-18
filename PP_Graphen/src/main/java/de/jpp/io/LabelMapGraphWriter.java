package de.jpp.io;

import de.jpp.model.LabelMapGraph;
import de.jpp.model.interfaces.Edge;
import org.jdom2.Element;

import java.util.HashMap;
import java.util.Map;

public class LabelMapGraphWriter extends GxlWriterTemplate<String, Map<String,String>, LabelMapGraph,String>  {
    private int counterNode =0;
    private int counterEdge=0;
    private Map<String,Integer> ids=new HashMap<>();

    public LabelMapGraphWriter() {
    }

    @Override
    public Element writeNode(String node) {
        counterNode++;
        ids.put(node,counterNode);
        Element nodeEl = new Element("node");
        nodeEl.setAttribute("id","id"+ counterNode);
        Element attr = new Element("attr");
        attr.setAttribute("name","description");
        Element string = new Element("string");
        string.setText(String.valueOf(node));
        attr.addContent(string);
        nodeEl.addContent(attr);
        return nodeEl;
    }

    @Override
    public Element writeEdge(Edge<String, Map<String, String>> edge) {
        counterEdge++;
        String start = edge.getStart();
        String dest = edge.getDestination();
        Map<String,String> anno=null;
        if (edge.getAnnotation().isPresent()){
            anno=edge.getAnnotation().get();
        }else anno=new HashMap<>();

        Element edgeEl = new Element("edge");
        edgeEl.setAttribute("id","id"+counterEdge);
        edgeEl.setAttribute("from","id"+calculateIdNode(start));
        edgeEl.setAttribute("to","id"+calculateIdNode(dest));
        if (!anno.isEmpty()){
            for (String key : anno.keySet()){
                Element attr = new Element("attr");
                attr.setAttribute("name", key);
                Element string = new Element("string");
                string.setText(anno.get(key));
                attr.addContent(string);
                edgeEl.addContent(attr);
            }
        }
        return edgeEl;
    }

    @Override
    public String calculateIdEdge(Edge<String, Map<String, String>> edge) {
        return null;
    }

    @Override
    public String calculateIdNode(String node) {
        return String.valueOf(ids.get(node));
    }
}

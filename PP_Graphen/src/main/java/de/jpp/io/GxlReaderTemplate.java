package de.jpp.io;

import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.GraphImpl;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public abstract class GxlReaderTemplate<N, A, G extends GraphImpl<N, A>, F> implements GraphReader<N, A, G, F> {

    public GxlReaderTemplate() {
    }

    @Override
    public G read(Object input) throws ParseException {
        Graph graph = createGraph();
        Map<String, N> nodes = new HashMap<>();
        String s = (String) input;
        if (s.isEmpty()) {
            throw new ParseException();
        }
        SAXBuilder jdomBuilder = new SAXBuilder();
        StringReader sr = new StringReader(s);
        Document jdomDocument = null;
        try {
            jdomDocument = jdomBuilder.build(sr);
        } catch (JDOMException | IOException e) {
            throw new ParseException();
        }
        assert jdomDocument != null;

        Element gxl = jdomDocument.getRootElement();
        Element graphEl = gxl.getChild("graph");
        List<Element> nodesEdges = graphEl.getChildren();

        for (Element el : nodesEdges) {
            if (el.getName().equals("node")) {
                N node = readNode(el);
                graph.addNode(node);
                nodes.put(readNodeId(node, el), node);

            } else if (el.getName().equals("edge")) {
                addEdge(graph, el, nodes);


            } else throw new ParseException();
        }


        return (G) graph;
    }

    public void addEdge(Graph<N, A> graph, Element element, Map<String, N> map) throws ParseException {

        N nodestart = null;
        N nodedest = null;
        List<Attribute> fromTo = element.getAttributes();
        for (Attribute atr : fromTo) {
            if (atr.getName().equals("from")) {
                String from = atr.getValue();
                nodestart = map.get(from);

            } else if (atr.getName().equals("to")) {
                String to = atr.getValue();
                nodedest = map.get(to);
            }
        }
        if (nodestart == null || nodedest == null) {
            throw new ParseException();
        }else graph.addEdge(nodestart, nodedest, readAnnotation(element));
    }


    public abstract Graph createGraph();

    public abstract String readNodeId(N node, Element element) throws ParseException;

    public abstract N readNode(Element element) throws ParseException;

    public abstract Optional<A> readAnnotation(Element element);
}

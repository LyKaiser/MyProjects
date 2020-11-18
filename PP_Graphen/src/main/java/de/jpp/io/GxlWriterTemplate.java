package de.jpp.io;

import de.jpp.io.interfaces.GraphWriter;
import de.jpp.model.GraphImpl;
import de.jpp.model.interfaces.Edge;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.util.*;


public abstract class GxlWriterTemplate<N,A,G extends GraphImpl<N,A>,F> implements GraphWriter<N,A,G,F> {

    public GxlWriterTemplate() {
    }

    @Override
    public String write(G graph) {
        Collection<N> nodes = graph.getNodes();
        Collection<Edge<N,A>> edges = graph.getEdges();
        Document doc = new Document();
        Element root = new Element("glx");
        doc.setRootElement(root);
        Element graphEl = new Element("graph");
        graphEl.setAttribute("id","id0");
        doc.getRootElement().addContent(graphEl);

        for (N node : nodes) {
            Element docNode = writeNode(node);
            graphEl.addContent(docNode);

        }

        for (Edge<N,A> edge : edges) {
            Element docEdge = writeEdge(edge);
            graphEl.addContent(docEdge);
        }

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        String xmlString = outputter.outputString(doc);
        return xmlString;
    }

    public abstract Element writeNode(N node);
    public abstract Element writeEdge(Edge<N,A> edge);
    public abstract String calculateIdEdge(Edge<N,A> edge);
    public abstract String calculateIdNode(N node);

}

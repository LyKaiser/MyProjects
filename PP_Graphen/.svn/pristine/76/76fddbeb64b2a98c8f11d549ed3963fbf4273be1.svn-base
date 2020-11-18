package de.jpp.io;

import de.jpp.io.interfaces.ParseException;
import de.jpp.model.LabelMapGraph;
import de.jpp.model.interfaces.Graph;
import org.jdom2.Attribute;
import org.jdom2.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LabelMapGraphReader extends GxlReaderTemplate<String, Map<String, String>, LabelMapGraph, String> {

    public LabelMapGraphReader() {
    }

    @Override
    public Graph createGraph() {
        return new LabelMapGraph();
    }

    @Override
    public String readNodeId(String node, Element element) throws ParseException {
        String s = null;
        for (Attribute attr : element.getAttributes()) {
            if (attr.getName().equals("id")) {
                s = attr.getValue();
            }
        }
        if (s == null) {
            throw new ParseException();
        } else return s;
    }

    @Override
    public String readNode(Element element) throws ParseException {
        String s = null;
        for (Element el : element.getChildren()) {
            for (Attribute attr : el.getAttributes()) {
                if (attr.getValue().equals("description")) {
                    for (Element ele : el.getChildren()) {
                        s = ele.getText();
                    }
                }
            }
        }
        return s;
    }

    @Override
    public Optional<Map<String, String>> readAnnotation(Element element) {
        Map<String, String> map = new HashMap<>();
        for (Element el : element.getChildren()) {
            for (Attribute attr : el.getAttributes()) {
                for (Element ele : el.getChildren()) {
                    map.put(attr.getValue(), ele.getText());
                }
            }
        }
        if (map.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(map);
        }
    }
}

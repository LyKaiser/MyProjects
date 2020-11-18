package de.jpp.io;


import de.jpp.io.interfaces.ParseException;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Graph;
import org.jdom2.Attribute;
import org.jdom2.Element;
import java.util.List;
import java.util.Optional;

public class TwoDimGraphGxlReader extends GxlReaderTemplate<XYNode, Double, TwoDimGraph, String> {

    public TwoDimGraphGxlReader() {
    }

    @Override
    public Graph createGraph() {
        return new TwoDimGraph();
    }

    @Override
    public String readNodeId(XYNode node, Element element) throws ParseException {
        String s= null;
        for (Attribute attr : element.getAttributes()){
            if (attr.getName().equals("id")){
                s=attr.getValue();
            }
        }
        if (s==null){
            throw new ParseException();
        }else return s;
    }

    @Override
    public XYNode readNode(Element element) throws ParseException {
        String xStr=null;
        String yStr=null;
        String label=null;
        for (Element el : element.getChildren()) {
            for (Attribute attr : el.getAttributes()) {
                if (attr.getValue().equals("x")) {
                    for (Element ele : el.getChildren()) {
                        xStr= ele.getText();
                    }
                }
                if (attr.getValue().equals("y")) {
                    for (Element ele : el.getChildren()) {
                        yStr=ele.getText();
                    }
                }
                if (attr.getValue().equals("description")) {
                    for (Element ele : el.getChildren()) {
                        label=ele.getText();
                    }
                }
            }
        }
        if (xStr==null||yStr==null||label==null){
            throw new ParseException();
        }
        try {
            double x= Double.parseDouble(xStr);
            double y= Double.parseDouble(yStr);
            return new XYNode(label,x,y);
        }catch (Exception e){
            throw new ParseException();
        }
    }

    @Override
    public Optional<Double> readAnnotation(Element element) {
        String anno;
        Double annodouble = null;
        List<Element> elements = element.getChildren();
        for (Element el : elements) {
            List<Attribute> attr = el.getAttributes();
            for (Attribute at : attr) {
                if (at.getValue().equals("cost")) {
                    List<Element> floatEl = el.getChildren();
                    for (Element flo : floatEl) {
                        if (flo.getName().equals("float") || flo.getName().equals("int") || flo.getName().equals("double")) {
                            anno = flo.getText();
                            if (!anno.isEmpty()) {
                                annodouble = Double.parseDouble(anno);

                            }
                        }
                    }
                }
            }
        }
        if (annodouble == null) {
            return Optional.empty();
        } else {
            return Optional.of(annodouble);
        }
    }
}

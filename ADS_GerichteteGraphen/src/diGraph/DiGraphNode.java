package diGraph;

public class DiGraphNode {


    Object key;
    List adj;
    public DiGraphNode(Object key){
        this.key = key;
        this.adj = new List();
    }

    public List getNeighbors() {
        return adj;
    }//– gibt die Adjazenzliste des Knotens zurück

    public Object getKey(){
        return key;
    } //– gibt das Objekt zurück, auf das der Knoten verweist
}

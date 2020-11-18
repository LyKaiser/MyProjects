package diGraph;

public class DiGraph extends List {

    List knotenListe;

    public DiGraph() {
        knotenListe = new List();
    }//Graphen mit einer leeren Knotenliste

    public DiGraphNode find(Object key) {
        ListItem v = this.knotenListe.getHead();
        while (v != null) {
            if (v.key.getKey().equals(key)) {
                return v.key;
            }
            v = v.next;
        }
        return null;
    }
    //– überprüft, ob der Graph einen Knoten v
    //mit key.equals(v.getKey()) enthält und gibt entweder v oder Null zurück.

    public DiGraphNode addNode(Object key) {
        return knotenListe.insert(new DiGraphNode(key)).key;
    }
    //– erzeugt einen neuen Knoten v mit
    //Verweis auf key, fügt diesen dem Graphen hinzu und gibt dann v zurück

    public void addEdge(DiGraphNode v1, DiGraphNode v2) {
        v1.adj.insert(v2);
    }
    //– erzeugt eine gerichtete
    //Kante vom Knoten v1 zum Knoten v2


    public DiGraph(Object[] keys, boolean[][] adjacencyMatrix) {
        knotenListe = new List();
        for (int i = 0; i < keys.length; i++) {
            addNode(keys[i]);
        }
        for (int i = 0; i < keys.length; i++) {
            for (int j = 0; j < keys.length; j++) {
                if (adjacencyMatrix[i][j]) {
                    addEdge(find(keys[i]), find(keys[j]));
                }
            }
        }
    }
}

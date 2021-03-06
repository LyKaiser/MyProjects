package de.jpp.model;

import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;
import org.w3c.dom.Node;

import java.util.*;

public class GraphImpl<N, A> implements Graph<N, A> {
    private List<N> nodes = new ArrayList<>();
    private Map<N, List<Edge<N, A>>> edges = new HashMap<>();


    public GraphImpl() {
    }

    public GraphImpl(List<N> nodes, Map<N, List<Edge<N, A>>> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public GraphImpl(Map<N, List<Edge<N, A>>> edges) {
        this.edges = edges;
    }

    @Override
    public boolean addNode(N node) {
        boolean bol = false;
        if (!nodes.contains(node)) {
            bol = true;
            nodes.add(node);
        }
        if (!edges.keySet().contains(node)) {
            edges.put(node, new ArrayList<>());
        }
        return bol;
    }

    @Override
    public boolean addNodes(Collection<? extends N> nodes) {
        boolean bol = false;
        for (N node : nodes) {
            boolean boolNeu = addNode(node);
            if (boolNeu) {
                bol = true;
            }
        }
        return bol;
    }

    @Override
    public boolean addNodes(N... nodes) {
        boolean bol = false;
        for (N node : nodes) {
            boolean boolNeu = addNode(node);
            if (boolNeu) {
                bol = true;
            }
        }
        return bol;
    }

    @Override
    public Collection<N> getNodes() {
        Set<N> list = new HashSet<>(nodes);
        return list;
    }

    @Override
    public Edge<N, A> addEdge(N start, N destination, Optional<A> annotation) {
        if (!nodes.contains(start)) {
            nodes.add(start);
        }
        if (!nodes.contains(destination)) {
            nodes.add(destination);
        }
        if (!edges.keySet().contains(start)) {
            edges.put(start, new ArrayList<>());
        }
        if (!edges.keySet().contains(destination)) {
            edges.put(destination, new ArrayList<>());
        }
        Edge<N, A> edgeStart = new Edge<>(start, destination, annotation);
        if (!edges.get(start).contains(edgeStart)) {
            edges.get(start).add(edgeStart);
        }
        return edgeStart;
    }

    @Override
    public Edge<N, A> addEdge(N start, N destination) {
        addEdge(start, destination, Optional.empty());
        return new Edge<>(start, destination, Optional.empty());
    }

    @Override
    public boolean removeEdge(Edge<N, A> edge) {

        boolean bol = false;
        if (edge != null) {
            if (edges.keySet().contains(edge.getStart())) {
                if (edges.get(edge.getStart()).contains(edge)) {
                    edges.get(edge.getStart()).remove(edge);
                    bol = true;

                }
            }

        }
        return bol;
    }

    @Override
    public Collection<Edge<N, A>> getNeighbours(N node) {
        ensureEdgeListNonNull(node);
        return edges.get(node);


    }

    @Override
    public Collection<Edge<N, A>> getReachableFrom(N node) {
        List<Edge<N, A>> result = new ArrayList<>();
        for (List<Edge<N, A>> list : edges.values()) {
            for (Edge<N, A> edge : list) {
                if (edge.getDestination().equals(node)) {
                    result.add(edge);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<Edge<N, A>> getEdges() {
        Set<Edge<N, A>> result = new HashSet<>();
        Collection<List<Edge<N, A>>> listCollection = edges.values();
        for (List<Edge<N, A>> list : listCollection) {
            result.addAll(list);
        }
        return result;
    }

    @Override
    public boolean removeNode(N node) {
        boolean bool = false;
        if (nodes.contains(node)) {
            nodes.remove(node);
            //removeHook(node);
            bool = true;
        }

        if (edges.keySet().contains(node)) {
            edges.remove(node);
            bool = true;
        }
        removeHook(node);
        return bool;
    }

    @Override
    public boolean removeNodes(Collection<? extends N> nodes) {
        boolean bo = false;
        for (N node : nodes) {
            boolean bool = removeNode(node);
            if (bool) {
                bo = true;
            }
        }
        return bo;
    }

    @Override
    public boolean removeNodes(N... nodes) {
        boolean bo = false;
        for (N node : nodes) {
            boolean bool = removeNode(node);
            if (bool) {
                bo = true;
            }
        }
        return bo;
    }

    @Override
    public void clear() {
        nodes.clear();
        edges.clear();

    }

    public void removeHook(N node) {
        List<Edge<N, A>> delete = new ArrayList<>();
        for (List<Edge<N, A>> list : edges.values()) {
            for (Edge<N, A> edge : list) {
                if (edge.getDestination().equals(node)) {
                    delete.add(edge);
                }
            }
        }
        for (Edge<N, A> del : delete) {
            edges.get(del.getStart()).remove(del);
            ensureEdgeListNonNull(del.getStart());
        }

    }

    public void ensureEdgeListNonNull(N node) {

        if (edges.get(node) == null) {
            edges.put(node, new ArrayList<>());
        }

        //edges.computeIfAbsent(node, k -> new ArrayList<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphImpl<?, ?> graph = (GraphImpl<?, ?>) o;
        Set<N> no= new HashSet<>(nodes);
        Set<Edge<N,A>> ed = new HashSet<>(getEdges());
        return Objects.equals(no, graph.getNodes()) &&
                Objects.equals(ed, graph.getEdges());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, edges);
    }

    @Override
    public String toString() {
        Set<N> no= new HashSet<>(nodes);
        Set<Edge<N,A>> ed = new HashSet<>(getEdges());
        return "GraphImpl{" +
                "nodes=" + no +
                ", edges=" + ed +
                '}';
    }

    /*public static void main(String[] args) {
        Edge<String, String> edge = new Edge<>("a", "c", Optional.empty());
        Edge<String, String> edge0 = new Edge<>("b", "a", Optional.empty());
        Edge<String, String> edge1 = new Edge<>("c", "d", Optional.empty());
        Edge<String, String> edge2 = new Edge<>("b", "d", Optional.empty());
        Graph<String, String> graph = new GraphImpl<>();

        graph.addEdge("a", "c");
        graph.addEdge("b", "a");
        graph.addEdge("c", "d");
        graph.addEdge("b", "d");
        System.out.println(graph.getNodes());
        System.out.println(graph.getEdges());
        graph.removeNode("a");
        System.out.println(graph.getNodes());
        System.out.println(graph.getEdges());
        graph.removeEdge(edge2);
        System.out.println(graph.getNodes());
        System.out.println(graph.getEdges());

    }*/
}

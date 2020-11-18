package de.jpp.model;

import com.sun.javafx.property.adapter.PropertyDescriptor;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.GraphWithObservableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.function.Consumer;

public class GraphWithObservableModelImpl<N, A> extends GraphImpl<N, A> implements GraphWithObservableModel<N, A> {

    private List<Consumer<N>> nodeAddedListener = new ArrayList<>();
    private List<Consumer<N>> nodeRemovedListener = new ArrayList<>();
    private List<Consumer<Edge<N, A>>> edgeAddedListener = new ArrayList<>();
    private List<Consumer<Edge<N, A>>> edgeRemovedListener = new ArrayList<>();


    @Override
    public boolean addNode(N node) {
        boolean addNode = super.addNode(node);
        if (addNode) {
            for (Consumer<N> con : nodeAddedListener) {
                con.accept(node);
            }
        }
        return addNode;
    }

    @Override
    public boolean removeNode(N node) {
        List<Edge<N,A>> list = new ArrayList<>();
            for (Edge<N, A> ed : super.getEdges()) {
                if (ed.getStart().equals(node) || ed.getDestination().equals(node)) {
                    list.add(ed);
                }
            }


        boolean removeNode = super.removeNode(node);
        if (removeNode) {
            for (Consumer<N> con : nodeRemovedListener) {
                con.accept(node);
            }
            for (Consumer<Edge<N,A>> co : edgeRemovedListener){
                for (Edge<N,A> e : list){
                    co.accept(e);
                }

            }
        }




        /*Collection<Edge<N, A>> edges = getEdges();
        for (Edge<N, A> edge : edges) {
            if (edge.getStart().equals(node) || edge.getDestination().equals(node)) {
                for (Consumer<Edge<N, A>> con : edgeRemovedListener) {
                    con.accept(edge);
                }
            }
        }*/
        return removeNode;
    }


    @Override
    public Edge<N, A> addEdge(N start, N destination, Optional<A> annotation) {
        if (!getNodes().contains(start)) {
            for (Consumer<N> co : nodeAddedListener) {
                co.accept(start);
            }
        }
        if (!getNodes().contains(destination)) {
            for (Consumer<N> co : nodeAddedListener) {
                co.accept(destination);
            }
        }
        for (Consumer<Edge<N, A>> con : edgeAddedListener) {
            con.accept(super.addEdge(start, destination, annotation));
        }
        return super.addEdge(start, destination, annotation);
    }

    @Override
    public boolean removeEdge(Edge<N, A> edge) {
        boolean removeEdge = super.removeEdge(edge);
        if (removeEdge) {
            for (Consumer<Edge<N, A>> con : edgeRemovedListener) {
                con.accept(edge);
            }
        }
        return super.removeEdge(edge);
    }

    @Override
    public void clear() {
        Collection<N> nodes = getNodes();
        for (Consumer<N> con : nodeRemovedListener) {
            for (N node : nodes) {
                con.accept(node);
            }
        }
        Collection<Edge<N, A>> edges = getEdges();
        for (Consumer<Edge<N, A>> con : edgeRemovedListener) {
            for (Edge<N, A> edge : edges) {
                con.accept(edge);
            }
        }
        super.clear();
    }

    @Override
    public void addNodeAddedListener(Consumer<N> listener) {
        nodeAddedListener.add(listener);

    }

    @Override
    public void addNodeRemovedListener(Consumer<N> listener) {

        nodeRemovedListener.add(listener);

    }

    @Override
    public void addEdgeAddedListener(Consumer<Edge<N, A>> listener) {
        edgeAddedListener.add(listener);

    }

    @Override
    public void addEdgeRemovedListener(Consumer<Edge<N, A>> listener) {
        edgeRemovedListener.add(listener);

    }

    @Override
    public void removeNodeAddedListener(Consumer<N> listener) {
        nodeAddedListener.remove(listener);

    }

    @Override
    public void removeNodeRemovedListener(Consumer<N> listener) {
        nodeRemovedListener.remove(listener);

    }

    @Override
    public void removeEdgeAddedListener(Consumer<Edge<N, A>> listener) {
        edgeAddedListener.remove(listener);

    }

    @Override
    public void removeEdgeRemovedListener(Consumer<Edge<N, A>> listener) {
        edgeRemovedListener.remove(listener);

    }

    public List<Consumer<N>> getNodeAddedListener() {
        return nodeAddedListener;
    }

    public List<Consumer<N>> getNodeRemovedListener() {
        return nodeRemovedListener;
    }

    public List<Consumer<Edge<N, A>>> getEdgeAddedListener() {
        return edgeAddedListener;
    }

    public List<Consumer<Edge<N, A>>> getEdgeRemovedListener() {
        return edgeRemovedListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GraphWithObservableModelImpl<?, ?> that = (GraphWithObservableModelImpl<?, ?>) o;
        return Objects.equals(nodeAddedListener, that.nodeAddedListener) &&
                Objects.equals(nodeRemovedListener, that.nodeRemovedListener) &&
                Objects.equals(edgeAddedListener, that.edgeAddedListener) &&
                Objects.equals(edgeRemovedListener, that.edgeRemovedListener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nodeAddedListener, nodeRemovedListener, edgeAddedListener, edgeRemovedListener);
    }

    @Override
    public String toString() {
        return "GraphWithObservableModelImpl{" +
                "nodeAddedListener=" + nodeAddedListener +
                ", nodeRemovedListener=" + nodeRemovedListener +
                ", edgeAddedListener=" + edgeAddedListener +
                ", edgeRemovedListener=" + edgeRemovedListener +
                '}';
    }
}

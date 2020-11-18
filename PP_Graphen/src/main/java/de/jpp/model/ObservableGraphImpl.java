package de.jpp.model;

import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;
import de.jpp.model.interfaces.GraphWithObservableQueries;
import de.jpp.model.interfaces.ObservableGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ObservableGraphImpl<N,A> extends GraphWithObservableModelImpl<N, A> implements ObservableGraph<N,A>, GraphWithObservableQueries<N,A> {

    private List<Consumer<Collection<Edge<N,A>>>> neighboursListedListener=new ArrayList<>();
    private List<Consumer<Collection<Edge<N,A>>>>reachableListedListener=new ArrayList<>();
    private List<Consumer<Collection<N>>> nodesListListener=new ArrayList<>();
    private List<Consumer<Collection<Edge<N,A>>>> edgesListedListener=new ArrayList<>();






    @Override
    public Collection<Edge<N, A>> getNeighbours(N node) {
        for (Consumer<Collection<Edge<N,A>>> con : neighboursListedListener){
            con.accept(super.getNeighbours(node));
        }
        return super.getNeighbours(node);
    }

    @Override
    public Collection<Edge<N, A>> getReachableFrom(N node) {
        for (Consumer<Collection<Edge<N,A>>> con : reachableListedListener){
            con.accept(super.getReachableFrom(node));
        }
        return super.getReachableFrom(node);
    }

    @Override
    public Collection<N> getNodes() {
        for (Consumer<Collection<N>> con : nodesListListener){
            con.accept(super.getNodes());
        }
        return super.getNodes();
    }

    @Override
    public Collection<Edge<N, A>> getEdges() {
        for (Consumer<Collection<Edge<N,A>>> con : edgesListedListener){
            con.accept(super.getEdges());
        }
        return super.getEdges();
    }

    @Override
    public void addNeighboursListedListener(Consumer<Collection<Edge<N, A>>> listener) {
        neighboursListedListener.add(listener);
    }

    @Override
    public void addReachableListedListener(Consumer<Collection<Edge<N, A>>> listener) {
        reachableListedListener.add(listener);

    }

    @Override
    public void addNodesListedListener(Consumer<Collection<N>> listener) {
        nodesListListener.add(listener);

    }

    @Override
    public void addEdgesListedListener(Consumer<Collection<Edge<N, A>>> listener) {
        edgesListedListener.add(listener);

    }

    @Override
    public void removeNeighboursListedListener(Consumer<Collection<Edge<N, A>>> listener) {
        neighboursListedListener.remove(listener);

    }

    @Override
    public void removeReachableListedListener(Consumer<Collection<Edge<N, A>>> listener) {
        reachableListedListener.remove(listener);

    }

    @Override
    public void removeNodesListedListener(Consumer<Collection<N>> listener) {
        nodesListListener.remove(listener);

    }

    @Override
    public void removeEdgesListedListener(Consumer<Collection<Edge<N, A>>> listener) {
        edgesListedListener.remove(listener);

    }

    public List<Consumer<Collection<Edge<N, A>>>> getNeighboursListedListener() {
        return neighboursListedListener;
    }

    public List<Consumer<Collection<Edge<N, A>>>> getReachableListedListener() {
        return reachableListedListener;
    }

    public List<Consumer<Collection<N>>> getNodesListListener() {
        return nodesListListener;
    }

    public List<Consumer<Collection<Edge<N, A>>>> getEdgesListedListener() {
        return edgesListedListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ObservableGraphImpl<?, ?> that = (ObservableGraphImpl<?, ?>) o;
        return Objects.equals(neighboursListedListener, that.neighboursListedListener) &&
                Objects.equals(reachableListedListener, that.reachableListedListener) &&
                Objects.equals(nodesListListener, that.nodesListListener) &&
                Objects.equals(edgesListedListener, that.edgesListedListener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), neighboursListedListener, reachableListedListener, nodesListListener, edgesListedListener);
    }

    @Override
    public String toString() {
        return "ObservableGraphImpl{" +
                "neighboursListedListener=" + neighboursListedListener +
                ", reachableListedListener=" + reachableListedListener +
                ", nodesListListener=" + nodesListListener +
                ", edgesListedListener=" + edgesListedListener +
                '}';
    }
}
